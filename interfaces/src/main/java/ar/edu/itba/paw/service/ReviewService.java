package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface ReviewService {

    List<Review> getReviews(Restaurant rest);
    Optional<Review> getReview(final long id);
    Review addReview(User user, Restaurant restaurant, String text, int rating);
    void addAnswerReview(Review reviewToAnswer,String answer);
}
