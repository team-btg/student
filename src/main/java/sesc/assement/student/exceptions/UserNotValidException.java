package sesc.assement.student.exceptions;

public class UserNotValidException extends RuntimeException {
    public UserNotValidException() {
        super("Not a valid account.");
    }
    public UserNotValidException(String message) {
        super(message);
    }
}
