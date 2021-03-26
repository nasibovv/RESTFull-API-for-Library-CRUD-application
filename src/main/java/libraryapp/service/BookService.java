package libraryapp.service;

import libraryapp.dto.library.BookDTO;
import libraryapp.dto.library.UserLibraryHistoryResponseDTO;
import libraryapp.dto.library.UserPickedUpBookResponseDTO;
import libraryapp.exception.AuthorNotFoundException;
import libraryapp.exception.BookNotFoundException;
import libraryapp.exception.CategoryNotFoundException;

import java.util.List;

public interface BookService {

    List<BookDTO> getBooks();

    BookDTO getBookByName(String bookName) throws BookNotFoundException;

    List<BookDTO> getBooksByCategory(String category) throws BookNotFoundException, CategoryNotFoundException;

    List<BookDTO> getBooksByAuthor(String authorName) throws BookNotFoundException, AuthorNotFoundException;

    List<UserPickedUpBookResponseDTO> getSessionUserPickedUpBooks();

    List<UserLibraryHistoryResponseDTO> getSessionUserLibraryHistory();

    List<BookDTO> getEligibleBooks();
}
