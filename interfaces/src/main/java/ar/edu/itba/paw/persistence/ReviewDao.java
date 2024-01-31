package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    List<Review> getReviews(Restaurant rest);
    Review addReview(User reviewer, Restaurant rest, String text, int rating);
    void addReviewAnswer(long reviewId,String answer);
    Optional<Review> getReview(final long id);
}
