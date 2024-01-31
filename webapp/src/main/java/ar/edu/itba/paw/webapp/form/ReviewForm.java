package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Size;

public class ReviewForm {

    @Size(max = 100)
    private String text;

    private int rating;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
