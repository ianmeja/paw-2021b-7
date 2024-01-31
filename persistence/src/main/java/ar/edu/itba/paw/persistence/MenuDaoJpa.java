package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Menu;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.Review;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public class MenuDaoJpa implements MenuDao{

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Menu addMenu(Restaurant rest, String dish, String description, int price, String category) {
        final Menu menu = new Menu(rest,dish,description,price,category);
        em.persist(menu);
        return menu;
    }

    @Transactional
    @Override
    public Optional<Menu> getMenu(long dish_id) {
        return Optional.ofNullable(em.find(Menu.class,dish_id));
    }

    @Override
    public List<Menu> findMenuByRestId(long restId) {
        //busco el restaurante
        final TypedQuery<Restaurant> resto = em.createQuery("select r from Restaurant r where r.restId = :rest_id", Restaurant.class);
        resto.setParameter("rest_id", restId);
        Restaurant restaurant = resto.getSingleResult();

        final TypedQuery<Menu> query = em.createQuery("select m from Menu m where m.rest = :rest order by m.category desc", Menu.class);
        query.setParameter("rest", restaurant);
        return query.getResultList();
    }

    @Override
    public List<Long> search(String search) {
        search = search.toLowerCase(Locale.ROOT);
        final TypedQuery<Restaurant> query = em.createQuery("select rest from Menu where LOWER(dish) LIKE CONCAT('%',:search,'%') or LOWER(description) LIKE CONCAT('%',:search,'%')", Restaurant.class);
        query.setParameter("search", search);
        List<Restaurant> restos = query.getResultList();
        List<Long> ids = new ArrayList<>();
        for (Restaurant rest: restos ) {
            ids.add(rest.getId());
        }
        return ids;
    }

    @Transactional
    @Override
    public void editMenu(long dish_id, String dish, String description,int price, String category){
        em.createQuery("update Menu set dish = :dish ,description = :description,  price = :price, category = :category where dishId = :dish_id").setParameter("dish", dish).setParameter("description", description).setParameter("price", price).setParameter("category", category).executeUpdate();
    }

    @Transactional
    @Override
    public void deleteMenu(long dishId){
        Query query = em.createQuery("delete from Menu where dishId = :dishId");
        query.setParameter("dishId", dishId);
        query.executeUpdate();
    }
}
