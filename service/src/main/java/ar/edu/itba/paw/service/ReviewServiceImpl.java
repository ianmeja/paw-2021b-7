package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.ReviewDao;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);
    @Autowired
    private ReviewDao reviewDao;
    @Autowired
    private RestaurantService rest;
    @Autowired
    private EmailService emailService;

    @Override
    public List<Review> getReviews(Restaurant rest){
        return reviewDao.getReviews(rest);
    }

    @Override
    public Optional<Review> getReview(long id) {
        return reviewDao.getReview(id);
    }

    @Override
    @Transactional
    public Review addReview(User user, Restaurant restaurant, String text, int rating){
        Review review = reviewDao.addReview(user, restaurant, text, rating);;
        rest.updateRatingAndReviews(restaurant.getId());
        emailService.sendReviewNotification(review, LocaleContextHolder.getLocale());
        return review;
    }

    @Override
    @Transactional
    public void addAnswerReview(Review reviewToAnswer, String answer){
        reviewDao.addReviewAnswer(reviewToAnswer.getId(), answer);
        emailService.sendResponseNotification(reviewToAnswer, answer, LocaleContextHolder.getLocale());
    }

}

