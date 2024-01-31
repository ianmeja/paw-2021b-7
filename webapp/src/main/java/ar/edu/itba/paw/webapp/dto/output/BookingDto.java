package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.model.Booking;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Date;

public class BookingDto {

    private long booking_id;

    private String restaurant;

    private String booker;

    private Date date;

    private String time;

    private int diners;

    private boolean confirmed;

    public static BookingDto fromBooking(Booking booking, UriInfo uri) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.booking_id = booking.getBooking_id();
        bookingDto.date = booking.getDate();
        bookingDto.time = booking.getTime();
        bookingDto.diners = booking.getDiners();
        bookingDto.confirmed = booking.isConfirmed();
        bookingDto.restaurant = booking.getRest().getFullName();
        bookingDto.booker = booking.getBooker().getUsername();
        return bookingDto;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getBooker() {
        return booker;
    }

    public void setBooker(String booker) {
        this.booker = booker;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDiners() {
        return diners;
    }

    public void setDiners(int diners) {
        this.diners = diners;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public long getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(long booking_id) {
        this.booking_id = booking_id;
    }
}
