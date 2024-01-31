package ar.edu.itba.paw.webapp.exceptions;

public class ReviewNotFoundException extends CustomRuntimeException{

    private static final long serialVersionUID = -4439804381424128244L;

    public ReviewNotFoundException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.reviewNotFound");
    }
}
