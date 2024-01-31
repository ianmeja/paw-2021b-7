package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_restId_seq")
    @SequenceGenerator(sequenceName = "restaurant_restId_seq", name = "restaurant_restId_seq", allocationSize = 1)
    @Column(name= "restId")
    private long restId;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(name = "phone_number",length = 50, nullable = false)
    private String phoneNumber;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(length = 100, nullable = false)
    private String address;

    @Column
    private int price;

    @Column
    private int rating;

    @Column(length = 50)
    private String cuisine;

    @Column(length = 50)
    private String neighborhood;

    @Column(length = 100)
    private String message;

    @Column
    private boolean reservation;

    @Column
    private int capacity;

    @Column
    private String about;

    @Column(name="cant_reviews")
    private int cantReviews;

    @Column
    private byte[] image;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rest",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Review> favorites;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rest",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Review> reviews;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rest",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Booking> bookings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rest",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Menu> menus;

    Restaurant(){

    }

    public static List<String> cuisine_list() {
        List<String> cuisine_list = new ArrayList<>();
        cuisine_list.add("Aleman");
        cuisine_list.add("Americano");
        cuisine_list.add("Argentino");
        cuisine_list.add("Asiatico");
        cuisine_list.add("Autoctono");
        cuisine_list.add("Bar");
        cuisine_list.add("Bodegon");
        cuisine_list.add("Cerveceria");
        cuisine_list.add("Chino");
        cuisine_list.add("De autor");
        cuisine_list.add("De fusion");
        cuisine_list.add("Español");
        cuisine_list.add("Frances");
        cuisine_list.add("Hamburgueseria");
        cuisine_list.add("Indio");
        cuisine_list.add("Internacional");
        cuisine_list.add("Israeli");
        cuisine_list.add("Italiano");
        cuisine_list.add("Japones");
        cuisine_list.add("Latino");
        cuisine_list.add("Meditarraneo");
        cuisine_list.add("Mexicano");
        cuisine_list.add("Parrilla");
        cuisine_list.add("Peruano");
        cuisine_list.add("Pescados y mariscos");
        cuisine_list.add("Picadas");
        cuisine_list.add("Pizzeria");
        cuisine_list.add("Sin gluten");
        cuisine_list.add("Sushi");
        cuisine_list.add("Tapeo");
        cuisine_list.add("Vegano");
        cuisine_list.add("Vegetariano");
        cuisine_list.add("Venezolano");
        return cuisine_list;
    }

    public Restaurant(String email, String password, String phone_number, String full_name, String address, int price, int rating, String cuisine, String neighborhood, String message, Boolean reservation, int capacity, String about, int cantReviews, byte[] image) {
        super();
        this.email = email;
        this.password = password;
        this.phoneNumber = phone_number;
        this.fullName = full_name;
        this.address = address;
        this.price = price;
        this.rating = rating;
        this.cuisine = cuisine;
        this.neighborhood = neighborhood;
        this.message = message;
        this.reservation = reservation;
        this.capacity = capacity;
        this.about = about;
        this.cantReviews = cantReviews;
        this.image = image;
    }

    public Restaurant(long restId, String email, String password, String phone_number, String full_name, String address, int price, int rating, String cuisine, String neighborhood, String message, boolean reservation, int capacity, String about, int cantReviews, byte[] image) {
        this(email, password, phone_number, full_name, address, price, rating, cuisine, neighborhood, message, reservation, capacity, about, cantReviews, image);
        this.restId = restId;
    }

    public long getId() {
        return restId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public int getPrice() {
        return price;
    }

    public int getRating() {
        return rating;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getMessage() {
        return message;
    }

    public boolean getReservation() {
        return reservation;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getAbout() {
        return about;
    }

    public int getCantReviews() {
        return cantReviews;
    }

    public byte[] getImage() {
        return image;
    }


}