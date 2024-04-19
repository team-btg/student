package sesc.assement.student.exceptions;

public class StudentNotValidException extends RuntimeException {

    public StudentNotValidException() {
        super("Not a valid account.");
    }
    public StudentNotValidException(String message) {
        super(message);
    }
}
