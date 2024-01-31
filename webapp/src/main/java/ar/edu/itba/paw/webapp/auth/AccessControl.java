package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccessControl {

    @Autowired
    private UserService us;

    @Autowired
    private RestaurantService rs;

    @Autowired
    private MenuService ms;

    @Autowired
    private BookingService bs;

    @Autowired
    private ReviewService revs;

    @Autowired
    private ImageService is;


    public boolean checkIsSameUser(Authentication authentication, long userId) {
        Optional<User> loggedUser = getUser(authentication);
        return loggedUser.isPresent() && loggedUser.get().getUserId() == userId;
    }

    public boolean checkIsSameRestaurant(Authentication authentication, long restId) {
        Optional<Restaurant> loggedRest = getRestaurant(authentication);
        return loggedRest.isPresent() && loggedRest.get().getId() == restId;
    }

    public boolean checkIsBookingOwner(Authentication authentication, long bookingId) {
        Optional<User> loggedUser = getUser(authentication);
        if (!loggedUser.isPresent())
            return false;

        Optional<Booking> bookingOpt = bs.findBookById(bookingId);

        return bookingOpt.filter(booking -> loggedUser.get().getUserId() == booking.getBooker().getUserId()).isPresent();
    }

    public boolean checkIsBookingRestaurant(Authentication authentication, long bookingId) {
        Optional<Restaurant> loggedRest = getRestaurant(authentication);
        if (!loggedRest.isPresent())
            return false;

        Optional<Booking> bookingOpt = bs.findBookById(bookingId);

        return bookingOpt.filter(booking -> loggedRest.get().getId() == booking.getRest().getId()).isPresent();
    }

    public boolean checkIsMenuOwner(Authentication authentication, long menuId) {
        Optional<Restaurant> loggedRest = getRestaurant(authentication);
        if (!loggedRest.isPresent())
            return false;

        Optional<Menu> menuOpt = ms.getMenu(menuId);

        return menuOpt.filter(menu -> loggedRest.get().getId() == menu.getRest().getId()).isPresent();
    }

    public boolean checkCanRespond(Authentication authentication, long reviewId) {
        Optional<Restaurant> loggedRest = getRestaurant(authentication);
        if (!loggedRest.isPresent())
            return false;

        Optional<Review> reviewOpt = revs.getReview(reviewId);

        return reviewOpt.filter(review -> loggedRest.get().getId() == review.getRest().getId()).isPresent();
    }

    public boolean checkIsImageOwner(Authentication authentication, long imageId) {
        Optional<Restaurant> loggedRest = getRestaurant(authentication);
        if (!loggedRest.isPresent())
            return false;

        Optional<Image> imageOpt = is.getImage(imageId);

        return imageOpt.filter(image -> loggedRest.get().getId() == image.getRest().getId()).isPresent();
    }

    private Optional<User> getUser(Authentication authentication) {
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return us.findByEmail(userDetails.getUsername());
        } catch (ClassCastException e) {
            return Optional.empty();
        }
    }

    private Optional<Restaurant> getRestaurant(Authentication authentication) {
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return rs.findByEmail(userDetails.getUsername());
        } catch (ClassCastException e) {
            return Optional.empty();
        }
    }
}
