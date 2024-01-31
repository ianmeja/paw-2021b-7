package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Booking;
import ar.edu.itba.paw.model.Menu;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.BookingDao;
import ar.edu.itba.paw.persistence.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.awt.print.Book;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@Rollback
@Sql(scripts = "classpath:bookingTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class BookingDaoJpaTest {

    private static final int DINERS = 5;
    private static final Date DATE = Date.from(Instant.ofEpochSecond(100000000));
    private static final String TIME = "22:00";

    @Autowired
    private DataSource ds;

    @Autowired
    private BookingDao bookingDao;

    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testAddBook(){
        User user = em.find(User.class, 1L);
        Restaurant rest = em.find(Restaurant.class, 1L);
        final Booking booking = bookingDao.addBook(rest, user, DINERS, DATE, TIME);

        assertNotNull(booking);
        assertEquals(rest, booking.getRest());
        assertEquals(user, booking.getBooker());
        assertEquals(DINERS, booking.getDiners());
        assertEquals(DATE, booking.getDate());
        assertEquals(TIME, booking.getTime());

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "bookings", "booking_id = " + booking.getBooking_id()));
    }

    @Test
    public void deleteBooking(){
        Booking book = Mockito.mock(Booking.class);
        when(book.getBooking_id()).thenReturn(2L);

        bookingDao.deleteBooking(book.getBooking_id());
        assertNull(em.find(Booking.class, book.getBooking_id()));
    }

    @Test
    public void testFindById(){
        Booking booking = Mockito.mock(Booking.class);
        when(booking.getBooking_id()).thenReturn(2L);

        final Optional<Booking> optionalBooking = bookingDao.findBookById(booking.getBooking_id());
        assertTrue(optionalBooking.isPresent());
        assertEquals(optionalBooking.get().getBooking_id(), booking.getBooking_id());
    }

    @Test
    public void testUpdateConfirmed(){
        Booking booking = Mockito.mock(Booking.class);
        when(booking.getBooking_id()).thenReturn(2L);

        bookingDao.updateConfirmed(booking.getBooking_id());
        assertTrue(em.find(Booking.class, booking.getBooking_id()).isConfirmed());
    }

}
