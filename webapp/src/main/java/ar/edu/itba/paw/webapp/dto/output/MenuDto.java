package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.model.Menu;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class MenuDto {

    private URI restaurant;

    private long dish_id;

    private String dish;

    private String description;

    private int price;

    private String category;

    public static MenuDto fromMenu(Menu menu, UriInfo uri){
        MenuDto menuDto = new MenuDto();
        menuDto.dish_id = menu.getDish_id();
        menuDto.dish = menu.getDish();
        menuDto.description = menu.getDescription();
        menuDto.price = menu.getPrice();
        menuDto.category = menu.getCategory();

        menuDto.restaurant = uri.getBaseUriBuilder().path("restaurant").path(String.valueOf(menu.getRest().getId())).build();

        return menuDto;
    }

    public URI getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(URI restaurant) {
        this.restaurant = restaurant;
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
