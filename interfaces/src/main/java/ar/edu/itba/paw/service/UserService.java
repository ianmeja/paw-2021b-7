package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Favorite;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface UserService {

    User register(String username, String email, String password);

    void enableUser(String email, String username);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    int update(final long id, String username);

    void deleteBooking(long book_id);

    int setTaken(int user, int rest);

    List<Restaurant> findUserFavRest(User user,int page , int total);

    Favorite addFav(User user, Restaurant rest);

    void deleteFavorite(User user, Restaurant rest);

    boolean findRest(User user, long rest);

    int getCountFav();
}