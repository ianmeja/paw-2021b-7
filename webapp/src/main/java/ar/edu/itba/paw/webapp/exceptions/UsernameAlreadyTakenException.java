package ar.edu.itba.paw.webapp.exceptions;

public class UsernameAlreadyTakenException extends CustomException {

    public UsernameAlreadyTakenException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.usernameAlreadyExists");
    }
}
