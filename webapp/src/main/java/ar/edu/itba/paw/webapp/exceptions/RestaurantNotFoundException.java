package ar.edu.itba.paw.webapp.exceptions;

public class RestaurantNotFoundException extends CustomRuntimeException{

    private static final long serialVersionUID = -4439804381464928244L;

    public RestaurantNotFoundException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.restaurantNotFound");
    }
}
