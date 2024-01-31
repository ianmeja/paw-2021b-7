package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RestaurantForm {

    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-ñ]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*")
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

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
    private Integer price;

    @NotBlank
    @Pattern(regexp = "[a-zA-Zñ ]+")
    private String neighborhood;

    @NotNull
    private Boolean reservation;

    @NotNull
    private Integer capacity;

    @Size(max = 150)
    private String about_me;

    @Size(max = 25)
    private String message;

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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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
}
