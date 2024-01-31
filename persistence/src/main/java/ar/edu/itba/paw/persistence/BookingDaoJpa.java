package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Booking;
import ar.edu.itba.paw.model.Menu;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class BookingDaoJpa implements BookingDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Booking addBook(Restaurant rest, User user, int diners, Date date, String time) {
        final Booking book = new Booking(rest, user, date, time, diners, false);
        em.persist(book);
        return book;
    }

    @Transactional
    @Override
    public void deleteBooking(long bookingId) {
        Query query = em.createQuery("delete from Booking where bookingId = :bookingId");
        query.setParameter("bookingId", bookingId);
        query.executeUpdate();
    }

    @Override
    public Optional<Booking> findBookById(long id) {
        return Optional.ofNullable(em.find(Booking.class,id));
    }

    @Override
    public List<Booking> findBookByUserId(User user) {
        final TypedQuery<Booking> query = em.createQuery("select b from Booking b where b.booker = :user", Booking.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public List<Booking> findBookByRestId(Restaurant rest) {
        final TypedQuery<Booking> query = em.createQuery("select b from Booking b where b.rest = :rest", Booking.class);
        query.setParameter("rest", rest);
        return query.getResultList();
    }

    @Override
    public boolean isAvailable(Restaurant rest,String time,Date date,int diners) {

        TypedQuery<Long> query = em.createQuery("select sum(diners) from Booking where rest = :rest and time = :time and date = :date", Long.class);
        query.setParameter("rest", rest);
        query.setParameter("time",time);
        query.setParameter("date",date);
        int totalDiners = query.getFirstResult();
        return rest.getCapacity() >= (totalDiners + diners);
    }

    @Transactional
    @Override
    public void updateConfirmed(long id) {
        em.createQuery("update Booking set confirmed = true where bookingId = :id").setParameter("id",id).executeUpdate();
    }

}
