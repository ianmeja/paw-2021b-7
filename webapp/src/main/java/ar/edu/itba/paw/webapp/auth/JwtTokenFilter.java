package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService us;

    @Autowired
    private RestaurantService rs;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (header.startsWith("Basic ")) {
            tryBasicAuthentication(header, request, response);
        } else if (header.startsWith("Bearer ")) {
            tryBearerAuthentication(header, request);
        }

        filterChain.doFilter(request, response);
    }

    private void tryBearerAuthentication(String header, HttpServletRequest request) throws IOException {
        final String token = header.split(" ")[1].trim();
        if (!jwtUtil.validate(token))
            return;

        // Get user identity and set it on the spring security context
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.getUsername(token));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ? Collections.emptyList() : userDetails.getAuthorities()
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void tryBasicAuthentication(String header, HttpServletRequest request, HttpServletResponse response) {
        String encodedCredentials = header.split(" ")[1];
        try {
            String[] credentials = new String(Base64.getDecoder().decode(encodedCredentials)).split(":");
            if (credentials.length != 2)
                return;

            String username = credentials[0].trim();
            String password = credentials[1].trim();

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, password);

            Authentication authenticate = authenticationManager.authenticate(authentication);

            String email;
            long id;
            UserDetails user = (UserDetails) authenticate.getPrincipal();
            User fullUser = us.findByEmail(user.getUsername()).orElse(null);

            int type;
            if (fullUser == null){
                Restaurant restaurant = rs.findByEmail(user.getUsername()).orElse(null);
                if (restaurant == null){
                    return;
                }
                email = restaurant.getEmail();
                id = restaurant.getId();
                type = 1;
            }else{
                email = fullUser.getEmail();
                id = fullUser.getUserId();
                type = 0;
            }

            response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtil.generateAccessToken(email, id, type));

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (BadCredentialsException | IllegalArgumentException ignored) {
            // If the request needs to be authorized it will be rejected by the next filter
        }
    }

    private boolean isEmpty(String header) {
        return header == null || header.isEmpty();
    }
}