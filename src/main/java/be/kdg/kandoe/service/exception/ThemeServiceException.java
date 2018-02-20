package be.kdg.kandoe.service.exception;

public class ThemeServiceException extends RuntimeException {
    public ThemeServiceException(String message) {
        super(message);
    }

    public ThemeServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
