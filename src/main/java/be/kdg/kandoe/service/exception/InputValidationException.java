package be.kdg.kandoe.service.exception;

public class InputValidationException extends RuntimeException {

    public InputValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputValidationException(String message) {
        super(message);
    }
}
