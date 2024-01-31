package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Booking;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RestaurantService rest;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    private final int TOTAL = 8;

    @Override
    public User register(String username, String email, String password) {
        User user = userDao.register(username, email, passwordEncoder.encode(password));
        String token = confirmationTokenService.generateVerificationToken(user);
        emailService.sendUserVerificationEmail(email, token, LocaleContextHolder.getLocale());
        return user;
    }

    @Override
    public void enableUser(String email, String username){
        userDao.enableUser(email);
        emailService.sendConfirmationEmail(email, username, LocaleContextHolder.getLocale());
    }

    @Override
    public Optional<User> findById(long id){
        return userDao.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<Restaurant> findUserFavRest(User user,int page,int total){
        return userDao.findUserFavRest(user,page,total);
    }

    @Override
    public Favorite addFav(User user, Restaurant rest){
        return userDao.addFav(user, rest);
    }

    @Override
    public void deleteFavorite(User user, Restaurant rest){
        userDao.deleteFavorite(user, rest);
    }

    @Transactional
    @Override
    public int update(final long id, String username){
        Optional<User> maybeUser = findByUsername(username);
        if (maybeUser.isPresent() && maybeUser.get().getUserId() != id){
            return -1;
        }
        userDao.update(id,username);
        return 0;
    }

    @Override
    @Transactional
    public void deleteBooking(long book_id){
        Optional<Booking> booking = bookingService.findBookById(book_id);
        if (booking.isPresent()){
            bookingService.deleteBooking(book_id);
            emailService.sendBookingCancellationToRest(booking.get(), LocaleContextHolder.getLocale());
        }
    }


    @Override
    public int setTaken(int user, int rest) {
        if(user==rest){
            return user;
        }
        if(user==3 && rest==2){
            return 3;
        }
        else {
            return user+rest;
        }
    }

    @Override
    public boolean findRest(User user, long rest){
        List<Restaurant> restaurants = findUserFavRest(user,-1,-1);
        for(Restaurant res:restaurants){
            if(res.getId()==rest){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getCountFav() {
        return userDao.getCountFav();
    }

}
