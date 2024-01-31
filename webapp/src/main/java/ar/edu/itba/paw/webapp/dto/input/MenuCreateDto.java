package ar.edu.itba.paw.webapp.dto.input;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MenuCreateDto {

    @NotBlank
    @Size(max = 50)
    private String dish;

    @Size(max = 50)
    private String description;

    @NotNull
    private int price;

    @NotBlank
    private String category;

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
}
