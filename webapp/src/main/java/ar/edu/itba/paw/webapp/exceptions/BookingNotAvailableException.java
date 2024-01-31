package ar.edu.itba.paw.webapp.exceptions;

public class BookingNotAvailableException extends CustomException{

    public BookingNotAvailableException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.bookingNotAvailable");
    }

}
