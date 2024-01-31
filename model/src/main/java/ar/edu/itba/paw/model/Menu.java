/*package ar.edu.itba.paw.model;

public class Menu {

    private long rest_id;
    private String dish;
    private String description;
    private int price;
    private String category;
    private long dish_id;


    public Menu(long rest_id, String dish,String description, int price, String category) {
        this.rest_id = rest_id;
        this.dish = dish;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public Menu(long dish_id, long rest_id, String dish, String description, int price, String category) {
        this.rest_id = rest_id;
        this.dish = dish;
        this.description = description;
        this.price = price;
        this.category = category;
        this.dish_id = dish_id;
    }

    public long getRest_id() {
        return rest_id;
    }

    public void setRest_id(long rest_id) {
        this.rest_id = rest_id;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getDish_id() {
        return dish_id;
    }

    public void setDish_id(long dish_id) {
        this.dish_id = dish_id;
    }
}
*/

package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name="menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_dish_id_seq")
    @SequenceGenerator(sequenceName = "menu_dish_id_seq", name = "menu_dish_id_seq", allocationSize = 1)
    @Column(name = "dish_id")
    private long dishId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="rest_id",nullable = false)
    private Restaurant rest;

    @Column(length = 50, nullable=false)
    private String dish;

    @Column(length = 50)
    private String description;

    @Column(nullable=false)
    private int price;

    @Column(nullable=false,columnDefinition = "VARCHAR(20) CHECK (category IN ('STARTER', 'MAIN', 'DESSERT'))")
    private String category;

    Menu(){

    }

    public Menu(Restaurant rest, String dish,String description, int price, String category) {
        this.rest = rest;
        this.dish = dish;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public Menu(long dishId, Restaurant rest, String dish, String description, int price, String category) {
        this.rest = rest;
        this.dish = dish;
        this.description = description;
        this.price = price;
        this.category = category;
        this.dishId = dishId;
    }

    public long getRest_id() {
        return rest.getId();
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getDish_id() {
        return dishId;
    }

    public void setDish_id(long dish_id) {
        this.dishId = dish_id;
    }

    public Restaurant getRest() {
        return rest;
    }
}

