package be.kdg.kandoe.service.exception;

public class ThemeRepositoryException extends RuntimeException {
    public ThemeRepositoryException() {
        super("An error occured while executing ThemeRepository methods");
    }

    public ThemeRepositoryException(String message) {
        super(message);
    }

    public ThemeRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
