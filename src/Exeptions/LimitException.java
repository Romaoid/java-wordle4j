package exeptions;

public class LimitException extends RuntimeException {

    public LimitException(final String message) {
        super(message);
    }
}