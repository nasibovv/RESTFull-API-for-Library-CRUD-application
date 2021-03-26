package libraryapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public final ResponseEntity<ErrorResponseDTO> handleBookNotFoundException(BookNotFoundException e) {
        return new ResponseEntity<>(getErrorResponse(HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public final ResponseEntity<ErrorResponseDTO> handleCategoryNotFoundException(CategoryNotFoundException e) {
        return new ResponseEntity<>(getErrorResponse(HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public final ResponseEntity<ErrorResponseDTO> handleAuthorNotFoundException(AuthorNotFoundException e) {
        return new ResponseEntity<>(getErrorResponse(HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookAlreadyPickedUpException.class)
    public final ResponseEntity<ErrorResponseDTO> handleBookAlreadyPickedException(BookAlreadyPickedUpException e) {
        return new ResponseEntity<>(getErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PickedBookNotFoundException.class)
    public final ResponseEntity<ErrorResponseDTO> handlePickedBookNotFoundException(PickedBookNotFoundException e) {
        return new ResponseEntity<>(getErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public final ResponseEntity<ErrorResponseDTO> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException e) {
        return new ResponseEntity<>(getErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponseDTO getErrorResponse(HttpStatus status, String message) {
        return new ErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                message,
                LocalDateTime.now());
    }
}

