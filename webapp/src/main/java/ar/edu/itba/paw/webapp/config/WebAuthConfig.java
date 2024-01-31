package ar.edu.itba.paw.webapp.config;


import ar.edu.itba.paw.webapp.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.web.cors.CorsConfiguration.ALL;

@ComponentScan({"ar.edu.itba.paw.webapp.auth"})
@EnableWebSecurity
@Configuration
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PawUserDetailsService pawUserDetails;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private AccessControl accessControl;


    @Value("classpath:rememberme.txt")
    Resource resource;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList(ALL));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.addAllowedHeader(ALL);
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Link", "Location", "ETag", "Total-Elements"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(pawUserDetails)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling()
                .authenticationEntryPoint((request, response, ex) -> response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage()
                ))
                .accessDeniedHandler((request, response, ex) -> response.sendError(
                        HttpServletResponse.SC_FORBIDDEN, ex.getMessage()
                ))
                .and().headers().cacheControl().disable()

                .and().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                /** Register & Verify */
                .antMatchers(HttpMethod.POST, "/api/users").anonymous()
                .antMatchers(HttpMethod.POST, "/api/restaurants").anonymous()
                .antMatchers(HttpMethod.PUT, "/api/users/verify").anonymous()

                /** Users */
                .antMatchers(HttpMethod.PUT, "/api/users/{id}").access("@accessControl.checkIsSameUser(authentication, #id)")

                /** Restaurants */
                .antMatchers(HttpMethod.PUT, "/api/restaurants/{id}").access("@accessControl.checkIsSameRestaurant(authentication, #id)")

                /** Favorites */
                .antMatchers(HttpMethod.PUT, "/api/users/{id}/favorites/{rest_id}").access("@accessControl.checkIsSameUser(authentication, #id)")
                .antMatchers(HttpMethod.DELETE, "/api/users/{id}/favorites/{rest_id}").access("@accessControl.checkIsSameUser(authentication, #id)")

                /** Menu */
                .antMatchers(HttpMethod.POST, "/api/restaurants/{id}/menu").access("@accessControl.checkIsSameRestaurant(authentication, #id)")
                .antMatchers(HttpMethod.DELETE, "/api/restaurants/{id}/menu").access("@accessControl.checkIsSameRestaurant(authentication, #id)")

                 /** Reviews */
                .antMatchers(HttpMethod.POST, "/api/restaurants/{id}/reviews").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.PUT, "/api/restaurants/{id}/reviews/{ans_id}").access("@accessControl.checkCanRespond(authentication, #ans_id)")

                 /** Bookings */
                .antMatchers(HttpMethod.POST, "/api/bookings").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.GET,"/api/users/{id}/bookings").access("@accessControl.checkIsSameUser(authentication, #id)")
                .antMatchers(HttpMethod.GET, "/api/restaurants/{id}/bookings").access("@accessControl.checkIsSameRestaurant(authentication, #id)")
                .antMatchers(HttpMethod.PUT, "/api/bookings/{id}/confirm").access("@accessControl.checkIsBookingRestaurant(authentication, #id)")
                .antMatchers(HttpMethod.DELETE, "/api/bookings/{id}").access("@accessControl.checkIsBookingOwner(authentication, #id)")

                 /** Images */
                .antMatchers(HttpMethod.POST, "/api/restaurants/{id}/images").access("@accessControl.checkIsSameRestaurant(authentication, #id)")
                .antMatchers(HttpMethod.DELETE, "/api/restaurants/{id}/images/{image_id}").access("@accessControl.checkIsSameRestaurant(authentication, #id)")
                .antMatchers("/api/**").permitAll()

                // Enable CORS
                .and().cors()
                // Disable CSRF
                .and().csrf().disable()
                // Add JWT Token Filter
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/css/**", "/resources/js/**","/resources/images/**", "/favicon.ico", "/403");
    }
}
