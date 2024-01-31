package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Menu;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.RestaurantDao;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MenuServiceImplTest extends TestCase {

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

    private static final String DISH = "Fideos";
    private static final String DESCRIPTION = "con tuco";
    private static final int PRICEMENU = 800;
    private static final String CATEGORY = "MAIN";

    private static final Restaurant testRest = new Restaurant(REMAIL, RPASS, PHONE, NAME, ADDRESS, PRICE, RATING, CUISINE, NEIGHBORHOOD, MESSAGE, false, CAPACITY, ABOUT, CANTREVIEWS, null);
    private static final Menu testMenu = new Menu(testRest,DISH,DESCRIPTION,PRICEMENU,CATEGORY);

    @Mock
    private MenuService mockMenuService;
    @Mock
    private RestaurantDao mockRestaurantDao;

    @Test
    public void testAddMenu() {
        when(mockRestaurantDao.register(REMAIL, RPASS, PHONE, NAME, ADDRESS, PRICE, CUISINE, NEIGHBORHOOD, false, CAPACITY)).thenReturn(testRest);
        when(mockMenuService.addMenu(testRest,"Fideos","con tuco",800,"MAIN")).thenReturn(testMenu);
    }

    @Test
    public void testSearch() {
        List<Long> list = new ArrayList<>();
        list.add((long) 1);
        when(mockRestaurantDao.register(REMAIL, RPASS, PHONE, NAME, ADDRESS, PRICE, CUISINE, NEIGHBORHOOD, false, CAPACITY)).thenReturn(testRest);
        when(mockMenuService.addMenu(testRest,"Fideos","con tuco",800,"MAIN")).thenReturn(testMenu);
        when(mockMenuService.search("Fideos")).thenReturn(list);
        when(mockMenuService.search("Ravioles")).thenReturn(new ArrayList<>());
    }

}