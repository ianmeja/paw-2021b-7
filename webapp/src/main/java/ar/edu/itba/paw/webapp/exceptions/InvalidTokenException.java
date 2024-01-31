package ar.edu.itba.paw.webapp.exceptions;

public class InvalidTokenException extends CustomException{

    public InvalidTokenException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.invalidToken");
    }
}
