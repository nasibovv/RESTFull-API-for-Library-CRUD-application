package libraryapp.exception;

public class UserAlreadyRegisteredException extends Throwable {
    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
