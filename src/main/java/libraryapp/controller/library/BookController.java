package libraryapp.controller.library;

import libraryapp.dto.library.BookDTO;
import libraryapp.dto.library.UserLibraryHistoryResponseDTO;
import libraryapp.dto.library.UserPickedUpBookResponseDTO;
import libraryapp.exception.AuthorNotFoundException;
import libraryapp.exception.BookNotFoundException;
import libraryapp.exception.CategoryNotFoundException;
import libraryapp.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/find-book-list")
    public List<BookDTO> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/find-book-by-name/{book-name}")
    public BookDTO getBookByName(@PathVariable("book-name") String bookName) throws BookNotFoundException {
        return bookService.getBookByName(bookName);
    }

    @GetMapping("/find-book-list-by-category/{category}")
    public List<BookDTO> getBooksByCategory(@PathVariable("category") String category) throws BookNotFoundException, CategoryNotFoundException {
        return bookService.getBooksByCategory(category);
    }

    @GetMapping("/find-book-list-by-author/{author-name}")
    public List<BookDTO> getBooksByAuthor(@PathVariable("author-name") String authorName) throws BookNotFoundException, AuthorNotFoundException {
        return bookService.getBooksByAuthor(authorName);
    }

    @GetMapping("/find-user-picked-up-book-list")
    public List<UserPickedUpBookResponseDTO> getSessionUserPickedUpBooks() {
        return bookService.getSessionUserPickedUpBooks();
    }

    @GetMapping("/user-library-history")
    public List<UserLibraryHistoryResponseDTO> getSessionUserLibraryHistory() {
        return bookService.getSessionUserLibraryHistory();
    }

    @GetMapping("/find-eligible-book-list")
    public List<BookDTO> getEligibleBooks() {
        return bookService.getEligibleBooks();
    }
}
