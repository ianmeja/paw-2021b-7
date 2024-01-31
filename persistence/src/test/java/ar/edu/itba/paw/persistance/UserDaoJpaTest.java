package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.jdbc.JdbcTestUtils;
import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@Rollback
@Sql(scripts = "classpath:userTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserDaoJpaTest {
    private static final String EMAIL = "juan@a.com";
    private static final String PASSWORD = "password";
    private static final String USERNAME = "Juan";

    @Autowired
    private DataSource ds;

    @Autowired
    private UserDao userDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testRegister() {
        final User user = userDao.register(USERNAME, EMAIL, PASSWORD);
        assertNotNull(user);
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(USERNAME, user.getUsername());
        assertEquals(EMAIL, user.getEmail());
        assertFalse(user.isEnabled());

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", "userId = " + user.getUserId()));
    }

    @Test
    public void testFindById(){
        User user = Mockito.mock(User.class);
        when(user.getUserId()).thenReturn(2L);

        final Optional<User> optionalUser = userDao.findById(user.getUserId());
        assertTrue(optionalUser.isPresent());
        assertEquals(optionalUser.get().getUserId(), user.getUserId());
    }

    @Test
    public void testFindByEmail(){
        User user = Mockito.mock(User.class);
        when(user.getUserId()).thenReturn(2L);
        when(user.getEmail()).thenReturn("fulano@gmail.com");

        final Optional<User> maybeUser = userDao.findByEmail(user.getEmail());
        assertTrue(maybeUser.isPresent());
        assertEquals(maybeUser.get().getUserId(), user.getUserId());
    }

    @Test
    public void testUpdate(){
        User user = Mockito.mock(User.class);
        when(user.getUserId()).thenReturn(2L);

        userDao.update(user.getUserId(), "username");
        final Optional<User> maybeUser = userDao.findById(user.getUserId());
        assertTrue(maybeUser.isPresent());
        assertEquals("username", maybeUser.get().getUsername());
    }

}


