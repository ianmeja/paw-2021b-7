package ar.edu.itba.paw.webapp.exceptions;

public class BookingNotFoundException extends CustomRuntimeException{

    public BookingNotFoundException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.bookingNotFound");
    }
}
