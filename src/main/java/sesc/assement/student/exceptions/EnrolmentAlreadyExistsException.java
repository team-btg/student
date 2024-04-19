package sesc.assement.student.exceptions;

public class EnrolmentAlreadyExistsException extends RuntimeException {

    public EnrolmentAlreadyExistsException() {
        super("Already enroled in this course.");
    }
    public EnrolmentAlreadyExistsException(String message) {
        super(message);
    }
}