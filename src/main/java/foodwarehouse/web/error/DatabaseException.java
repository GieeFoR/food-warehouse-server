package foodwarehouse.web.error;

public final class DatabaseException extends RuntimeException {

    public DatabaseException(String message) {
        super(message);
    }
}
