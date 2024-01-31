package ar.edu.itba.paw.webapp.exceptions;

public class ImageNotFoundException extends CustomRuntimeException{

    private static final long serialVersionUID = -4439804381464928244L;

    public ImageNotFoundException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.imageNotFound");
    }
}

