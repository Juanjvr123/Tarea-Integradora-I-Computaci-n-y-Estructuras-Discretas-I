package exceptions;

public class InexistentProductException extends Exception {
    public InexistentProductException(String message) {
        super(message);
    }
}