package exceptions;

public class InexistentOrderException extends Exception {
    public InexistentOrderException(String message) {
        super(message);
    }
}