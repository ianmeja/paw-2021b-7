package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Booking;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.persistence.RestaurantDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EmailService emailService;

    @Override
    public List<Restaurant> getAll(String page, String total) {
        return restaurantDao.getAll(page,total);
    }

    @Override
    public List<Restaurant> getRestaurantsSearch(String text,int total,int page) {
        return restaurantDao.getRestaurantsSearch(text,total,page);
    }

    @Override
    public List<Restaurant> search(String search, String cuisine, String sortBy, String min, String max,String page, String total) {
        return restaurantDao.search(search,cuisine,sortBy,min,max,page,total);
    }

    @Override
    public Restaurant register(String email, String password, String phone_number, String full_name, String address, int price, String cuisine, String neighborhood, boolean reservation, int capacity) {
        emailService.sendConfirmationEmail(email, full_name, LocaleContextHolder.getLocale());
        return restaurantDao.register(email, passwordEncoder.encode(password), phone_number, full_name, address, price, cuisine, neighborhood, reservation, capacity);
    }

    @Transactional
    @Override
    public void update(long id, String phone_number, String full_name, String address, int price, String cuisine, String neighborhood, boolean reservation, int capacity, String about,String message) {
        restaurantDao.update(id, phone_number, full_name, address, price, cuisine, neighborhood, reservation, capacity, about, message);
    }

    @Override
    public Optional<Restaurant> findById(long id){
        return restaurantDao.findById(id);
    }

    @Override
    public Optional<Restaurant> findByEmail(String email){
        return restaurantDao.findByEmail(email);
    }

    @Transactional
    @Override
    public void updateRatingAndReviews( long restId) { restaurantDao.updateRatingAndReviews(restId);}

    @Override
    public int emailTaken(String email) {
        return restaurantDao.emailTaken(email);
    }

    @Override
    public List<String> getAllCuisineType() {
        return restaurantDao.getAllCuisineType();
    }

    @Override
    public int getCountRest() {
        return restaurantDao.getCountRest();
    }

    @Override
    public byte[] getImageId(long restId){
        return restaurantDao.getImageId(restId);
    }

    @Transactional
    @Override
    public void updateImage(byte[] image, long restId){
        restaurantDao.updateImage(image, restId);
    }

    @Override
    public List<Restaurant> getAllRestaurants(){
        return restaurantDao.getAllRestaurants();
    }

    @Override
    public List<Restaurant> getAllHighRating(String page, String total) {
        return restaurantDao.getAllHighRating(page,total);
    }

    @Override
    @Transactional
    public void deleteBooking(long book_id){
        Optional<Booking> booking = bookingService.findBookById(book_id);
        if (booking.isPresent() && !booking.get().isConfirmed()){
            bookingService.deleteBooking(book_id);
            emailService.sendBookingCancellationToUser(booking.get(), LocaleContextHolder.getLocale());
        }
    }

    public void updateImage(long id, String fullName, byte[] image) {
       if (image != null) {
           updateImage(image, id);
           LOGGER.info("Images of restaurant " + fullName + " loaded.");
        } else {
            LOGGER.info("No images found.");
        }

    }
}
