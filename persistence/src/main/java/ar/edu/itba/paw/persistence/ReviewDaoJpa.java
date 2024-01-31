package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class ReviewDaoJpa implements ReviewDao{

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Review addReview(User reviewer, Restaurant rest, String text, int rating){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String aux  = formatter.format(date);
        final Review review = new Review(reviewer, rest, text, rating, aux,null);
        review.setName(reviewer.getUsername());
        em.persist(review);
        return review;
    }

    @Transactional
    @Override
    public void addReviewAnswer(long reviewId,String answer){
        em.createQuery("update Review set answer = :answer where id = :id").setParameter("answer", answer).setParameter("id",reviewId).executeUpdate();
    }

    @Override
    public List<Review> getReviews(Restaurant rest) {
        final TypedQuery<Review> query = em.createQuery("from Review where rest = :rest", Review.class);
        query.setParameter("rest", rest);
        return query.getResultList();
    }

    @Override
    public Optional<Review> getReview(final long id) {
        return Optional.ofNullable(em.find(Review.class,id));
    }
}

