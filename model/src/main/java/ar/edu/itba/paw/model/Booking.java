package ar.edu.itba.paw.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookings_booking_id_seq")
    @SequenceGenerator(sequenceName = "bookings_booking_id_seq", name = "bookings_booking_id_seq", allocationSize = 1)
    @Column(name = "booking_id")
    private long bookingId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="rest_id",nullable = false)
    private Restaurant rest;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id",nullable = false)
    private User booker;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern="yyyy-MM-dd")
    @Column(nullable=false)
    private Date date;

    @Column(nullable=false, length = 10)
    private String time;

    @Column(nullable=false)
    private int diners;

    @Column(nullable = false)
    private boolean confirmed;

    Booking (){

    }

    public Booking(long bookingId, Restaurant rest, User booker, Date date, String time, int diners, boolean confirmed) {
        this.bookingId = bookingId;
        this.rest = rest;
        this.booker = booker;
        this.date = date;
        this.time = time;
        this.diners = diners;
        this.confirmed = confirmed;
    }

    public Booking(Restaurant rest, User booker, Date date, String time, int diners, boolean confirmed) {
        super();
        this.rest = rest;
        this.booker = booker;
        this.date = date;
        this.time = time;
        this.diners = diners;
        this.confirmed = confirmed;
    }

    public Restaurant getRest() {
        return rest;
    }

    public void setRest(Restaurant rest) {
        this.rest = rest;
    }

    public User getBooker() {
        return booker;
    }

    public void setBooker(User booker) {
        this.booker = booker;
    }

    public long getBooking_id() {
        return bookingId;
    }

    public void setBooking_id(long bookingId) {
        this.bookingId = bookingId;
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
}
