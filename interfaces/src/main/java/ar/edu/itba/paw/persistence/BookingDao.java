package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Booking;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookingDao {

    Booking addBook(Restaurant rest, User user, int diners, Date date, String time);
    void deleteBooking(long id);
    Optional<Booking> findBookById(long id);
    List<Booking> findBookByUserId(User user);
    List<Booking> findBookByRestId(Restaurant restaurant);
    boolean isAvailable(Restaurant rest,String time,Date date,int diners);
    void updateConfirmed(long id);
}
