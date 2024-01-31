package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface RestaurantService {

    Restaurant register(String email, String password, String phone_number, String full_name, String address, int price, String cuisine, String neighborhood, boolean reservation, int capacity);
    void update(final long id, String phone_number, String full_name, String address, int price, String cuisine, String neighborhood, boolean reservation, int capacity, String about,String message);
    Optional<Restaurant> findById(long id);
    Optional<Restaurant> findByEmail(String email);
    List<Restaurant> getAll(String page, String total);
    List<Restaurant> getRestaurantsSearch(String text,int total, int page);
    List<Restaurant> search(String search, String cuisine, String sortBy, String min, String max,String page, String total);
    void updateRatingAndReviews(long restId);
    int emailTaken(String email);
    List<String> getAllCuisineType();
    int getCountRest();
    void updateImage(byte[] image, long restId);
    byte[] getImageId(long restId);
    List<Restaurant> getAllRestaurants();
    List<Restaurant> getAllHighRating(String page, String total);
    void deleteBooking(long book_id);
    void updateImage(long id, String fullName, byte[] image);

}
