package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantDao {

    Restaurant register(String email, String password, String phone_number, String full_name, String address, int price, String cuisine, String neighborhood, boolean reservation, int capacity);
    void update(final long id, String phone_number, String full_name, String address, int price, String cuisine, String neighborhood, boolean reservation, int capacity, String about,String message);
    Optional<Restaurant> findById(long id);
    Optional<Restaurant> findByEmail(String email);
    List<Restaurant> getAll(String page, String total);
    List<Restaurant> getRestaurantsSearch(String text,int total, int page);
    List<Restaurant> search(String search, String cuisine, String sortBy, String min, String max,String page, String total);
    void updateRatingAndReviews(long restId);
    int emailTaken(String email);
    int getCountRest();
    List<String> getAllCuisineType();
    byte[] getImageId(long restId);
    void updateImage(byte[] image, long restId);
    List<Restaurant> getAllRestaurants();
    List<Restaurant> getAllHighRating(String page, String total);
}
