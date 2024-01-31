package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.RestaurantDao;
import ar.edu.itba.paw.persistence.UserDao;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest extends TestCase {

    private static final String USERNAME = "jose";
    private static final String EMAIL = "jose@mail.com";
    private static final String PASS = "jose123";

    private static final String NAME = "chichilo";
    private static final String REMAIL = "chichilo@mail.com";
    private static final String RPASS = "chichilo123";
    private static final String PHONE = "1168686868";
    private static final int PRICE = 400;
    private static final String ADDRESS = "Camarones 1913";
    private static final int RATING = 5;
    private static final String CUISINE = "Americano";
    private static final String NEIGHBORHOOD = "Palermo";
    private static final String MESSAGE = "Hola, soy nuevo";
    private static final int CAPACITY = 5;
    private static final String ABOUT = "Hola, soy nuevo";
    private static final int CANTREVIEWS = 0;

    private static final User testUser = new User(USERNAME, EMAIL, PASS, true);
    private static final Restaurant testRest = new Restaurant(REMAIL, RPASS, PHONE, NAME, ADDRESS, PRICE, RATING, CUISINE, NEIGHBORHOOD, MESSAGE, false, CAPACITY, ABOUT, CANTREVIEWS, null);

    @Mock
    private UserDao mockUserDao;
    @Mock
    private UserService mockUserService;
    @Mock
    private RestaurantDao mockRestaurantDao;


/*    @Test
    public void testAddFavorite() {
        when(mockRestaurantDao.register(REMAIL, RPASS, PHONE, NAME, ADDRESS, PRICE, CUISINE, NEIGHBORHOOD, false, CAPACITY)).thenReturn(testRest);
        when(mockUserDao.register(USERNAME, EMAIL, PASS)).thenReturn(testUser);
        when(mockUserService.addFavorite(testUser, testRest.getId())).thenReturn(new ModelAndView("redirect:/restaurant-card/{restId}"));
        when(mockUserService.addFavorite(testUser, 4)).thenReturn(new ModelAndView("redirect:/404"));
    }

    @Test
    public void testDeleteFavorite() {
        when(mockRestaurantDao.register(REMAIL, RPASS, PHONE, NAME, ADDRESS, PRICE, CUISINE, NEIGHBORHOOD, false, CAPACITY)).thenReturn(testRest);
        when(mockUserDao.register(USERNAME, EMAIL, PASS)).thenReturn(testUser);
        when(mockUserService.deleteFavorite(testUser, testRest.getId())).thenReturn(new ModelAndView("redirect:/restaurant-card/{restId}"));
        when(mockUserService.deleteFavorite(testUser, 4)).thenReturn(new ModelAndView("redirect:/404"));
    }*/
}
