package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;


@Component
public class PawUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService us;

    @Autowired
    private RestaurantService rs;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        final ar.edu.itba.paw.model.User user = us.findByEmail(email).orElse(null);
        if (user == null){
            final Restaurant restaurant = rs.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user or restaurant for " + email));
            authorities.add(new SimpleGrantedAuthority("RESTAURANT"));
            return new User(email, restaurant.getPassword(), authorities);
        }
        authorities.add(new SimpleGrantedAuthority("CLIENT"));
        return new User(email, user.getPassword(), user.isEnabled(), true, true,true, authorities);
    }
}
