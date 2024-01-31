package ar.edu.itba.paw.webapp.utils;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.service.UserService;

import javax.ws.rs.core.*;

public class ApiUtils {

    private ApiUtils() {
        throw new AssertionError();
    }

    public static final int MAX_AGE = 31536000;

    public static User getLoggedUser(SecurityContext context, UserService us){
        return us.findByEmail(context.getUserPrincipal().getName()).orElse(null);
    }

    public static Restaurant getLoggedRestaurant(SecurityContext context, RestaurantService rs){
        return rs.findByEmail(context.getUserPrincipal().getName()).orElse(null);
    }

    public static Response.ResponseBuilder getConditionalCacheResponse(Request request, EntityTag eTag) {
        Response.ResponseBuilder response = request.evaluatePreconditions(eTag);
        if (response != null) {
            final CacheControl cacheControl = new CacheControl();
            cacheControl.setNoCache(true);
            cacheControl.setNoTransform(true);
            cacheControl.setMustRevalidate(true);
        }
        return response;
    }

    public static void setUnconditionalCache(Response.ResponseBuilder responseBuilder) {
        final CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(MAX_AGE);
        responseBuilder.cacheControl(cacheControl);
    }
}
