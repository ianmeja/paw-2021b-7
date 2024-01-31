package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Favorite;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    User register(String username, String email, String password);
    Optional<User> findById(long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    void update(final long id, String username);
    void enableUser(String email);
    List<Restaurant> findUserFavRest(User user,int page,int total);
    Favorite addFav(User user, Restaurant rest);
    void deleteFavorite(User user, Restaurant rest);
    int getCountFav();
}
