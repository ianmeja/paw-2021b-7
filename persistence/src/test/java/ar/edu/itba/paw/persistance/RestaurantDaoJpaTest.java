package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.RestaurantDao;
import ar.edu.itba.paw.persistence.ReviewDao;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@Rollback
@Sql(scripts = "classpath:restaurantTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class RestaurantDaoJpaTest {
    private static final String EMAIL = "biblos@gmail.com";
    private static final String PASSWORD = "password";
    private static final String PHONE_NUMBER = "1122334455";
    private static final String NAME = "Biblos";
    private static final String ADDRESS = "Ramsay 1234";
    private static final String NEIGHBOURHOOD = "Belgrano";
    private static final String CUISINE = "Parrilla";
    private static final int PRICE = 1000;
    private static final boolean RESERVATIONS = true;
    private static final int CAPACITY = 100;
    private static final String ABOUT = "Hola soy un restaurant.";
    private static final String MESSAGE = "Hola test.";

    private static final List<String> CUISINES = Arrays.asList("Autoctono", "Parrilla", "Argentino", "Japones");

    private static final int RATING = 3;

    @Autowired
    private DataSource ds;

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private ReviewDao reviewDao;

    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testRegister() {
        final Restaurant restaurant = restaurantDao.register(EMAIL, PASSWORD, PHONE_NUMBER, NAME, ADDRESS, PRICE, CUISINE, NEIGHBOURHOOD,RESERVATIONS,CAPACITY);
        assertNotNull(restaurant);
        assertEquals(EMAIL, restaurant.getEmail());
        assertEquals(PASSWORD, restaurant.getPassword());
        assertEquals(PHONE_NUMBER, restaurant.getPhoneNumber());
        assertEquals(NAME, restaurant.getFullName());
        assertEquals(ADDRESS, restaurant.getAddress());
        assertEquals(PRICE, restaurant.getPrice());
        assertEquals(CUISINE, restaurant.getCuisine());
        assertEquals(NEIGHBOURHOOD, restaurant.getNeighborhood());
        assertEquals(RESERVATIONS, restaurant.getReservation());
        assertEquals(CAPACITY, restaurant.getCapacity());
        assertEquals(0, restaurant.getRating());
        assertEquals("Nuevo", restaurant.getMessage());
        assertNull(restaurant.getAbout());
        assertNull(restaurant.getImage());
        assertEquals(0, restaurant.getCantReviews());

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "restaurant", "restId = " + restaurant.getId()));
    }

    @Test
    public void testUpdate() {
        Restaurant rest = Mockito.mock(Restaurant.class);
        when(rest.getId()).thenReturn(2L);

        restaurantDao.update(rest.getId(), PHONE_NUMBER, NAME, ADDRESS, PRICE, CUISINE, NEIGHBOURHOOD, RESERVATIONS, CAPACITY, ABOUT, MESSAGE);
        final Optional<Restaurant> maybeRestaurant = restaurantDao.findById(rest.getId());
        assertTrue(maybeRestaurant.isPresent());
        assertEquals(PHONE_NUMBER, maybeRestaurant.get().getPhoneNumber());
        assertEquals(NAME, maybeRestaurant.get().getFullName());
        assertEquals(ADDRESS, maybeRestaurant.get().getAddress());
        assertEquals(PRICE, maybeRestaurant.get().getPrice());
        assertEquals(CUISINE, maybeRestaurant.get().getCuisine());
        assertEquals(NEIGHBOURHOOD, maybeRestaurant.get().getNeighborhood());
        assertEquals(RESERVATIONS, maybeRestaurant.get().getReservation());
        assertEquals(CAPACITY, maybeRestaurant.get().getCapacity());
        assertEquals(MESSAGE, maybeRestaurant.get().getMessage());
        assertEquals(ABOUT, maybeRestaurant.get().getAbout());

    }

    @Test
    public void testFindById() {
        Restaurant rest = Mockito.mock(Restaurant.class);
        when(rest.getId()).thenReturn(3L);

        final Optional<Restaurant> optionalRestaurant = restaurantDao.findById(rest.getId());
        assertTrue(optionalRestaurant.isPresent());
        assertEquals(optionalRestaurant.get().getId(), rest.getId());
    }

    @Test
    public void testFindByEmail() {
        Restaurant rest = Mockito.mock(Restaurant.class);
        when(rest.getEmail()).thenReturn("ejemplo2@gmail.com");

        final Optional<Restaurant> optionalRestaurant = restaurantDao.findByEmail(rest.getEmail());
        assertTrue(optionalRestaurant.isPresent());
        assertEquals(optionalRestaurant.get().getEmail(), rest.getEmail());
    }

    @Test
    public void testGetAllHighRating() {
        final List<Restaurant> highRating = restaurantDao.getAllHighRating("1", "8");
        for (Restaurant rest: highRating) {
            assertTrue(rest.getRating()>=4);
        }
    }

    @Test
    public void testGetAllCuisineType() {
        final List<String> cuisines = restaurantDao.getAllCuisineType();
        assertTrue(cuisines.containsAll(CUISINES));
        assertEquals(4, cuisines.size());
    }

    @Test
    public void testUpdateRatingAndReviews() {
        User user = em.find(User.class, 1L);
        Restaurant rest = em.find(Restaurant.class, 2L);

        reviewDao.addReview(user, rest, "test", RATING);
        restaurantDao.updateRatingAndReviews(rest.getId());

        Restaurant rest_final = em.find(Restaurant.class, 2L);
        assertEquals(1, rest_final.getCantReviews());
        assertEquals(RATING, rest_final.getRating());
    }

    @Test
    public void testEmailTaken(){
        assertEquals(0, restaurantDao.emailTaken("noexisteestemail@gmail.com"));
        assertEquals(2, restaurantDao.emailTaken("ejemplo3@gmail.com"));
    }
}