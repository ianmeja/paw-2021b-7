package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Booking;
import ar.edu.itba.paw.model.Favorite;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.UncheckedIOException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserDaoJpa implements UserDao{

    @PersistenceContext
    private EntityManager em;

    //paginacion
    private int countFav = 0;

    @Transactional
    @Override
    public User register(String username, String email, String password) {
        final User user = new User(username, email, password, false);
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(em.find(User.class,id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        final TypedQuery<User> query = em.createQuery("select u from User u where u.email = :email", User.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        final TypedQuery<User> query = em.createQuery("from User where username = :username", User.class);
        query.setParameter("username", username);
        return query.getResultList().stream().findFirst();
    }

    @Transactional
    @Override
    public void update(long userid, String username){
        em.createQuery("update User set username = :username where userId = :userid").setParameter("userid",userid).setParameter("username",username).executeUpdate();
    }

    @Transactional
    @Override
    public void enableUser(String email) {
        em.createQuery("UPDATE User SET enabled = true WHERE email = :email").setParameter("email", email).executeUpdate();
    }

    @Transactional
    @Override
    public Favorite addFav(User user, Restaurant rest){
        final Favorite fav = new Favorite(user, rest);
        em.persist(fav);
        return fav;
    }

    @Transactional
    @Override
    public void deleteFavorite(User user, Restaurant rest) {
        Query query = em.createQuery("delete from Favorite where user = :user and rest = :rest");
        query.setParameter("user", user);
        query.setParameter("rest", rest);
        query.executeUpdate();
    }

    @Override
    public List<Restaurant> findUserFavRest(User user,int page, int total){

        TypedQuery<Favorite> count = em.createQuery("from Favorite where user = :user", Favorite.class);
        count.setParameter("user", user);
        setCountFav(count.getResultList().size());

        TypedQuery<Favorite> query = em.createQuery("from Favorite where user = :user", Favorite.class);
        query.setParameter("user", user);
        if(page!=-1 && total!=-1) {
            query.setFirstResult((page-1)*total);
            query.setMaxResults(total);
        }
        List<Favorite> favorites = query.getResultList();
        List<Restaurant> restaurants = new ArrayList<>();
        for(Favorite fav : favorites){
            restaurants.add(fav.getRest());
        }
        return restaurants;
    }

    public int getCountFav() {
        return countFav;
    }

    public void setCountFav(int countFav) {
        this.countFav = countFav;
    }
}
