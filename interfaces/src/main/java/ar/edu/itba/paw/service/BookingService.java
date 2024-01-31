package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Booking;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface BookingService {

    void deleteBooking(long id);
    boolean isAvailable(Restaurant rest,String time,Date date,int diners);
    Optional<Booking> findBookById(long id);
    List<Booking> findBookByUserId(User user);
    List<Booking> findBookByRestId(Restaurant loggedRestaurant);
    void updateConfirmed(long id);
    Booking addBook(Restaurant rest, User user, String diners, Date date, String time);
}
