package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Booking;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.BookingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl  implements  BookingService{

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public Booking addBook(Restaurant rest, User user, String diners, Date date, String time){
        if (isAvailable(rest, time, date, Integer.parseInt(diners))){
            Booking booking = bookingDao.addBook(rest , user, Integer.parseInt(diners), date, time);
            emailService.sendBookingInfo(booking, LocaleContextHolder.getLocale());
            emailService.sendBookingNotification(booking, LocaleContextHolder.getLocale());
            return booking;
        }
        return null;
    }

    @Override
    public void deleteBooking(long id){
        bookingDao.deleteBooking(id);
        Optional<Booking> booking = findBookById(id);
        if (booking.isPresent()){
            emailService.sendBookingCancellationToRest(booking.get(), LocaleContextHolder.getLocale());
            emailService.sendBookingCancellationToUser(booking.get(), LocaleContextHolder.getLocale());
        }
    }

    @Override
    public List<Booking> findBookByUserId(User user){
        return bookingDao.findBookByUserId(user);
    }

    @Override
    public List<Booking> findBookByRestId(Restaurant restaurant){
        return bookingDao.findBookByRestId(restaurant);
    }

    @Override
    @Transactional
    public void updateConfirmed(long id) {
        bookingDao.updateConfirmed(id);
        Optional<Booking> booking = findBookById(id);
        if (booking.isPresent()){
            emailService.sendBookingConfirmation(booking.get(), LocaleContextHolder.getLocale());
        }
    }

    @Override
    public boolean isAvailable(Restaurant rest,String time,Date date,int diners) {
        return bookingDao.isAvailable(rest,time,date,diners);
    }

    @Override
    public Optional<Booking> findBookById(long id) {
        return bookingDao.findBookById(id);
    }

}
