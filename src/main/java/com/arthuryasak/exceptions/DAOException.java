package application.exceptions;

public class DAOException extends RuntimeException {
    public DAOException(String message, Exception e) {
        super(message, e);
    }
}
