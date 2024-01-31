package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.model.Review;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class ReviewDto {

    private URI reviewer;

    private URI rest;

    private String text;

    private int rating;

    private String dateReview;

    private String name;

    private String answer;

    private long  id;
    private long user_id;
    private long rest_id;


    public static ReviewDto fromReview(Review review, UriInfo uri) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.text = review.getText();
        reviewDto.rating = review.getRating();
        reviewDto.dateReview = review.getDateReview();
        reviewDto.name = review.getName();
        reviewDto.answer = review.getAnswer();

        reviewDto.id = review.getId();
        reviewDto.user_id = review.getUser_id();
        reviewDto.rest_id = review.getRest_id();

        reviewDto.reviewer = uri.getAbsolutePathBuilder().path("users").path(String.valueOf(review.getUser_id())).build();
        reviewDto.rest = uri.getAbsolutePathBuilder().path("restaurant").path(String.valueOf(review.getRest().getId())).build();
        return reviewDto;
    }

    public URI getReviewer() {
        return reviewer;
    }

    public void setReviewer(URI reviewer) {
        this.reviewer = reviewer;
    }

    public URI getRest() {
        return rest;
    }

    public void setRest(URI rest) {
        this.rest = rest;
    }

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

    public String getDateReview() {
        return dateReview;
    }

    public void setDateReview(String dateReview) {
        this.dateReview = dateReview;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getRest_id() {
        return rest_id;
    }

    public void setRest_id(long rest_id) {
        this.rest_id = rest_id;
    }
}
