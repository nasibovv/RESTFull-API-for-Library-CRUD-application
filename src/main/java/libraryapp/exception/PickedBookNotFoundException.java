package libraryapp.exception;

public class PickedBookNotFoundException extends Throwable {
    public PickedBookNotFoundException(String message) {
        super(message);
    }
}
