package ar.edu.itba.paw.webapp.dto.input;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RestaurantEditDto {

    @NotBlank
    @Pattern(regexp = "^\\d+$")
    @Size(min=10, max=10)
    private String phone_number;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9ñ ]+" )
    private String full_name;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9.!#$%@ &'*+/=?^_`{|}~-ñ]+ [0-9]+" )
    private String address;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z ]+")
    private String cuisine;

    @NotNull
    private int price;

    @NotBlank
    @Pattern(regexp = "[a-zA-Zñ ]+")
    private String neighborhood;

    @NotNull
    private boolean reservation;

    @NotNull
    private int capacity;

    @Size(max = 150)
    private String about_me;

    @Size(max = 25)
    private String message;


    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Boolean getReservation() {
        return reservation;
    }

    public void setReservation(Boolean reservation) {
        this.reservation = reservation;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
