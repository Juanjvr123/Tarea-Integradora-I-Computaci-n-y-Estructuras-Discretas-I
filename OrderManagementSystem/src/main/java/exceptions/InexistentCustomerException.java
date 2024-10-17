package exceptions;

public class InexistentCustomerException extends Exception {
    public InexistentCustomerException(String message) {
        super(message);
    }
}