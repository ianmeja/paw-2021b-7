package ar.edu.itba.paw.webapp.exceptions;

public class EmailAlreadyTakenException extends CustomException {

    public EmailAlreadyTakenException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.emailAlreadyExists");
    }
}
