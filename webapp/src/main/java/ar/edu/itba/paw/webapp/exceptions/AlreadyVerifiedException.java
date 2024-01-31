package ar.edu.itba.paw.webapp.exceptions;

public class AlreadyVerifiedException extends CustomException{

    public AlreadyVerifiedException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.userAlreadyVerified");
    }
}
