package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Restaurant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class RestaurantDaoJpa implements RestaurantDao{

    @PersistenceContext
    private EntityManager em;

    //paginacion
    private int countRest = 0;

    @Transactional
    @Override
    public Restaurant register(String email, String password, String phone_number, String full_name, String address, int price, String cuisine, String neighborhood, boolean reservation, int capacity) {
        final Restaurant restaurant = new Restaurant(email,password, phone_number, full_name, address, price,0, cuisine, neighborhood, "Nuevo", reservation, capacity, null, 0, null);
        em.persist(restaurant);
        return restaurant;
    }

    @Transactional
    @Override
    public void update(final long id, String phoneNumber, String fullName, String address, int price, String cuisine, String neighborhood, boolean reservation, int capacity, String about,String message){
        em.createQuery("update Restaurant set phoneNumber = :phone_number, fullName = :full_name, address = :address, price = :price, cuisine = :cuisine, neighborhood = :neighborhood, reservation = :reservation, capacity = :capacity, about = :about, message = :message where restId = :id").setParameter("phone_number", phoneNumber).setParameter("full_name",fullName).setParameter("address",address).setParameter("price",price).setParameter("cuisine",cuisine).setParameter("neighborhood",neighborhood).setParameter("reservation",reservation).setParameter("capacity",capacity).setParameter("about",about).setParameter("message",message).setParameter("id",id).executeUpdate();
    }

    @Transactional
    @Override
    public void updateImage(byte[] image, long restId){
        em.createQuery("update Restaurant set image = :image where restId = :restId").setParameter("image",image).setParameter("restId",restId).executeUpdate();
    }

    @Transactional
    @Override
    public byte[] getImageId(long restId){
        final TypedQuery<Restaurant> query = em.createQuery("from Restaurant where restId = :restId", Restaurant.class);
        query.setParameter("restId", restId);
        if(query.getResultList().stream().findFirst().isPresent()) {
            return query.getResultList().stream().findFirst().get().getImage();
        }
        return null;
    }

    @Override
    public Optional<Restaurant> findById(final long id) {
        return Optional.ofNullable(em.find(Restaurant.class,id));
    }

    @Override
    public Optional<Restaurant> findByEmail(String email) {
        final TypedQuery<Restaurant> query = em.createQuery("select r from Restaurant r where r.email = :email", Restaurant.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<Restaurant> getAll(String page, String total) {
        getAllRestaurants();
        TypedQuery<Restaurant> query = em.createQuery("from Restaurant", Restaurant.class);
        query.setFirstResult((Integer.parseInt(page)-1)*Integer.parseInt(total));
        query.setMaxResults(Integer.parseInt(total));
        return query.getResultList();
    }

    @Override
    public List<Restaurant> getAllRestaurants(){
        final TypedQuery<Restaurant> query = em.createQuery("from Restaurant",Restaurant.class);
        List<Restaurant> lista = query.getResultList();
        setCountRest(lista.size());
        return lista;
    }

    public void setCountRestHighRatingRestaurants() {
        final TypedQuery<Restaurant> query = em.createQuery("from Restaurant r where r.rating >= 4", Restaurant.class);
        List<Restaurant> lista = query.getResultList();
        setCountRest(lista.size());
    }

    @Override
    public List<Restaurant> getAllHighRating(String page, String total) {
        setCountRestHighRatingRestaurants();
        TypedQuery<Restaurant> query = em.createQuery("from Restaurant r where r.rating >= 4", Restaurant.class);
        query.setFirstResult((Integer.parseInt(page)-1)*Integer.parseInt(total));
        query.setMaxResults(Integer.parseInt(total));
        return query.getResultList();
    }

    @Override
    public List<String> getAllCuisineType() {
        final TypedQuery<String> query = em.createQuery("select cuisine from Restaurant group by cuisine",String.class);
        return query.getResultList();
    }

    @Override
    public List<Restaurant> getRestaurantsSearch(String search,int total, int page) {

        search = search.toLowerCase(Locale.ROOT);

        String word = "%";
        word = word.concat(search);
        word = word.concat("%");

        //busco en el menu
        final TypedQuery<Restaurant> menuQuery = em.createQuery("select m.rest from Menu as m where LOWER(m.dish) LIKE :search", Restaurant.class);
        menuQuery.setParameter("search", word);
        List<Restaurant> menuRest = menuQuery.getResultList();
        List<Long> listMenu = new ArrayList<>();
        for (Restaurant rest:menuRest) {
            listMenu.add(rest.getId());
        }

        //busco en el resto
        final TypedQuery<Long> query2 = em.createQuery("select r.restId from Restaurant as r where LOWER(r.fullName) LIKE :search OR LOWER(r.cuisine) LIKE :search OR LOWER(r.address) LIKE :search  OR LOWER(r.neighborhood) LIKE :search OR LOWER(r.message) LIKE :search ", Long.class);
        query2.setParameter("search", word);
        List<Long> list2 = query2.getResultList();

        //junto las dos busquedas
        for (Long id:listMenu) {
            if(!list2.contains(id)){
                list2.add(id);
            }
        }

        if (!list2.isEmpty()) {
            final TypedQuery<Restaurant> query = em.createQuery("from Restaurant as r where r.restId IN (?1)", Restaurant.class);
            query.setParameter(1, list2);
            List<Restaurant> list = query.getResultList();
            //setCountRest(list.size());

            //si viene con paginacion
            if(page!=-1 && total!=-1) {
                final TypedQuery<Restaurant> query3 = em.createQuery("from Restaurant as r where r.restId IN (?1)", Restaurant.class);
                query3.setParameter(1, list2);
                query3.setFirstResult(total * (page - 1));
                query3.setMaxResults(total);
                return query3.getResultList();
            }
            else {
                return list;
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<Restaurant> search(String search, String cuisine, String sortBy, String min, String max, String page, String total){

        if(Objects.equals(search, "%") || Objects.equals(search, "_")){
            return new ArrayList<>();
        }

        boolean flag_cuisine = false;
        boolean flag_min = false;

        String sql = "FROM Restaurant";

        //CUISINE
        if(!Objects.equals(cuisine, "") && cuisine!=null) {

            sql = sql.concat(" WHERE LOWER(cuisine) LIKE ");
            sql = sql.concat("'%");
            sql = sql.concat(cuisine.toLowerCase(Locale.ROOT));
            sql = sql.concat("%' ");
            flag_cuisine = true;
        }
        //MIN
        if(!Objects.equals(min, "") && min!=null){
            flag_min = true;
            if(flag_cuisine){
                sql = sql.concat("AND ");
            }
            else {
                sql = sql.concat(" WHERE ");
            }
            sql = sql.concat(min).concat("<=price ");
        }
        //MAX
        if(!Objects.equals(max, "") && max!=null){
            if(flag_min){
                sql = sql.concat("AND ");
            }
            else {
                if(flag_cuisine)
                    sql = sql.concat("AND ");
                else
                    sql = sql.concat(" WHERE ");
            }
            sql = sql.concat("price<= ").concat(max);

        }

        //search general, si no hay nada del filtro
        if (cuisine == null && sortBy == null && min == null && max == null) {
            if(Objects.equals(search, "")){
                String query = "FROM Restaurant as r ORDER BY r.rating desc";

                List<Restaurant> listafinal = em.createQuery(query,Restaurant.class).getResultList();
                setCountRest(listafinal.size());

                int aux1 = Integer.parseInt(total)*(Integer. parseInt(page) -1);

                return em.createQuery(query,Restaurant.class).setFirstResult(aux1).setMaxResults(Integer.parseInt(total)).getResultList();
            }
            else if(search!=null){

                List<Restaurant> list = new ArrayList<>();

                int count=0;
                String[] arrOfStrings = search.split(" ");
                for (String word: arrOfStrings) {
                    count++;
                    list.addAll(getRestaurantsSearch(word,-1,-1));
                }
                List<Long> searchRes = new ArrayList<>();
                if(count > 1) {
                    int sum;
                    for (Restaurant rest : list) {
                        sum = 0;
                        for (Restaurant rest2 : list) {
                            if (rest.getId() == rest2.getId()) {
                                sum++;
                            }
                        }
                        if (sum >= 2) {
                            if(!searchRes.contains(rest.getId())) {
                                searchRes.add(rest.getId());
                            }
                        }
                    }
                }
                else {
                    for (Restaurant res: list) {
                        if (!searchRes.contains(res.getId())){
                            searchRes.add(res.getId());
                        }
                    }
                }
                setCountRest(searchRes.size());
                if (!searchRes.isEmpty()) {
                    int aux = Integer.parseInt(total)*(Integer. parseInt(page) -1);
                    return em.createQuery("from Restaurant as r where r.restId IN (?1)", Restaurant.class).setParameter("1", searchRes).setFirstResult(aux).setMaxResults(Integer.parseInt(total)).getResultList();
                }
                return new ArrayList<>();

            }
        }
        // query de los filtros
        List<Restaurant> listatotal = em.createQuery(sql,Restaurant.class).getResultList();

        //query del search
        List<Restaurant> listaSearch = new ArrayList<>();

        int count=0;
        String[] arrOfStrings = search.split(" ");
        for (String word: arrOfStrings) {
            count++;
            listaSearch.addAll(getRestaurantsSearch(word,-1,-1));
        }
        List<Restaurant> searchRest = new ArrayList<>();
        if(count > 1) {
            int sum;

            for (Restaurant rest : listaSearch) {
                sum = 0;
                for (Restaurant rest2 : listaSearch) {
                    if (rest.getId() == rest2.getId()) {
                        sum++;
                    }
                }
                if (sum >= 2) {
                    searchRest.add(rest);
                }
            }
        }
        else {
            searchRest.addAll(listaSearch);
        }

        List<Long> finalList = new ArrayList<>();

        for (Restaurant restTotal : listatotal) {
            for (Restaurant restSearch : searchRest) {
                if(restTotal.getId() == restSearch.getId()){
                    finalList.add(restSearch.getId());
                    break;
                }
            }
        }
        setCountRest(finalList.size());

        //para la paginacion
        int aux = Integer.parseInt(total)*(Integer. parseInt(page) -1);
        if (!finalList.isEmpty()) {
            if (!Objects.equals(sortBy, "") && sortBy != null) {
                String query = "from Restaurant as r where r.restId IN (?1) ORDER BY r.";
                switch (sortBy){
                    case("rating"):
                        query = query.concat(sortBy+" DESC");
                        break;
                    case("maxPrice"):
                        query = query.concat("price DESC");
                        break;
                    case("minPrice"):
                        query = query.concat("price");
                        break;
                }
                return em.createQuery(query, Restaurant.class).setParameter("1", finalList).setFirstResult(aux).setMaxResults(Integer.parseInt(total)).getResultList();

            } else {
                return em.createQuery("from Restaurant as r where r.restId IN (?1)", Restaurant.class).setParameter("1", finalList).setFirstResult(aux).setMaxResults(Integer.parseInt(total)).getResultList();
            }
        }
        return new ArrayList<>();
    }

    @Transactional
    @Override
    public void updateRatingAndReviews(long restId) {

        if(findById(restId).isPresent()) {
            Restaurant rest = findById(restId).get();
            final TypedQuery<Integer> query = em.createQuery("select rating from Review where rest = :rest", Integer.class);
            query.setParameter("rest", rest);
            List<Integer> listOfRating = query.getResultList();

            int count = 0;
            int cant = 0;
            for (Integer num : listOfRating) {
                count += num;
                cant++;
            }
            count = count / cant;

            em.createQuery("update Restaurant set rating = :rating, cantReviews = :cantReviews where restId = :rest_id").setParameter("rating", count).setParameter("cantReviews", cant).setParameter("rest_id", (long) restId).executeUpdate();
        }
    }

    @Override
    public int emailTaken(String email){

        int count = 0;

        final TypedQuery<Restaurant> query = em.createQuery("select r from Restaurant as r where r.email = :email",Restaurant.class);
        query.setParameter("email",email);
        Optional<Restaurant> resto = query.getResultList().stream().findFirst();

        if(resto.isPresent()){
            count+=2;
        }
        return count;
    }

    public int getCountRest() {
        return countRest;
    }

    public void setCountRest(int countRest) {
        this.countRest = countRest;
    }
}
