package ar.edu.itba.paw.webapp.exceptions;

public class CustomException extends Exception {
    //TODO: fijarse que esten bien los mensajes de excepcion en los dos idiomas
    private final int statusCode;
    private final String messageCode;

    public CustomException(int statusCode, String messageCode) {
        super();
        this.statusCode = statusCode;
        this.messageCode = messageCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessageCode() {
        return messageCode;
    }
}
