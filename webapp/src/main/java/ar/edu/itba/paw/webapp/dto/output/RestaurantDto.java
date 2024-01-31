package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.model.Restaurant;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class RestaurantDto {
    private long restId;
    private String email;
    private String password;
    private String phoneNumber;
    private String fullName;
    private String address;
    private int price;
    private int rating;
    private String cuisine;
    private String neighborhood;
    private String message;
    private boolean reservation;
    private int capacity;
    private String about;
    private int cantReviews;
    private byte[] image;

    private URI self;
    private URI reviewsUrl;
    private URI bookingsUrl;
    private URI menusUrl;

    public static RestaurantDto fromRestaurant(Restaurant restaurant, UriInfo uri) {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.restId = restaurant.getId();
        restaurantDto.email = restaurant.getEmail();
        restaurantDto.password = restaurant.getPassword();
        restaurantDto.phoneNumber = restaurant.getPhoneNumber();
        restaurantDto.fullName = restaurant.getFullName();
        restaurantDto.address = restaurant.getAddress();
        restaurantDto.price = restaurant.getPrice();
        restaurantDto.rating = restaurant.getRating();
        restaurantDto.cuisine = restaurant.getCuisine();
        restaurantDto.neighborhood = restaurant.getNeighborhood();
        restaurantDto.message = restaurant.getMessage();
        restaurantDto.reservation = restaurant.getReservation();
        restaurantDto.capacity = restaurant.getCapacity();
        restaurantDto.about = restaurant.getAbout();
        restaurantDto.cantReviews = restaurant.getCantReviews();
        restaurantDto.image = restaurant.getImage();

        restaurantDto.self = uri.getBaseUriBuilder().path("restaurant").path(String.valueOf(restaurant.getId())).build();
        restaurantDto.reviewsUrl = uri.getBaseUriBuilder().path(restaurantDto.self.getPath()).path("reviews").build();
        restaurantDto.bookingsUrl = uri.getBaseUriBuilder().path(restaurantDto.self.getPath()).path("bookings").build();
        restaurantDto.menusUrl = uri.getBaseUriBuilder().path(restaurantDto.self.getPath()).path("menu").build();
        return restaurantDto;
    }

    public long getRestId() {
        return restId;
    }

    public void setRestId(long restId) {
        this.restId = restId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isReservation() {
        return reservation;
    }

    public void setReservation(boolean reservation) {
        this.reservation = reservation;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getCantReviews() {
        return cantReviews;
    }

    public void setCantReviews(int cantReviews) {
        this.cantReviews = cantReviews;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public URI getReviewsUrl() {
        return reviewsUrl;
    }

    public void setReviewsUrl(URI reviewsUrl) {
        this.reviewsUrl = reviewsUrl;
    }

    public URI getBookingsUrl() {
        return bookingsUrl;
    }

    public void setBookingsUrl(URI bookingsUrl) {
        this.bookingsUrl = bookingsUrl;
    }

    public URI getMenusUrl() {
        return menusUrl;
    }

    public void setMenusUrl(URI menusUrl) {
        this.menusUrl = menusUrl;
    }
}
