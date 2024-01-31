package ar.edu.itba.paw.webapp.exceptions;

public class UserNotFoundException extends CustomRuntimeException{

    private static final long serialVersionUID = -4439804381464928244L;

    public UserNotFoundException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.userNotFound");
    }
}
