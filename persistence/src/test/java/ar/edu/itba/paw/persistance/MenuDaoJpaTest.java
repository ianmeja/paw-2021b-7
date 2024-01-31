package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.Menu;
import ar.edu.itba.paw.persistence.RestaurantDao;
import ar.edu.itba.paw.persistence.MenuDao;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
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
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@Rollback
@Sql(scripts = "classpath:menuTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class MenuDaoJpaTest{

    private static final String DESCRIPTION = "Con fritas";
    private static final String DISH = "Milanesa";
    private static final String CATEGORY = "MAIN";
    private static final int MPRICE = 500;

    @Autowired
    private DataSource ds;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RestaurantDao restaurantDao;

    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;
    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testAddMenu(){
        Menu menu = menuDao.addMenu(em.find(Restaurant.class, 1L),DISH,DESCRIPTION,MPRICE,CATEGORY);
        assertEquals(DISH, menu.getDish());
        assertEquals(DESCRIPTION, menu.getDescription());
        assertEquals(MPRICE, menu.getPrice());
        assertEquals(CATEGORY, menu.getCategory());

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "menu", "dish_id = " + menu.getDish_id()));
    }

    @Test
    public void testDeleteMenu(){
        Menu menu = Mockito.mock(Menu.class);
        when(menu.getDish_id()).thenReturn(2L);

        menuDao.deleteMenu(menu.getDish_id());
        assertNull(em.find(Menu.class, menu.getDish_id()));
    }
}
