package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MenuForm {

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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}
