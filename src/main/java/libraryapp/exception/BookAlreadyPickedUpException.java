package libraryapp.exception;

public class BookAlreadyPickedUpException extends Throwable {
    public BookAlreadyPickedUpException(String message) {
        super(message);
    }
}
