package libraryapp.service.impl;

import libraryapp.constant.BookLibraryStatus;
import libraryapp.dto.library.*;
import libraryapp.exception.AuthorNotFoundException;
import libraryapp.exception.BookNotFoundException;
import libraryapp.exception.CategoryNotFoundException;
import libraryapp.model.*;
import libraryapp.repository.*;
import libraryapp.security.service.UserPrincipal;
import libraryapp.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final PickDropRepository pickDropRepository;

    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository, AuthorRepository authorRepository, UserRepository userRepository, PickDropRepository pickDropRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
        this.pickDropRepository = pickDropRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    private User getSessionUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User sessionUser = userPrincipal.getUser();
        logger.info("getting user from session. user email: {}", sessionUser.getEmail());
        return sessionUser;
    }

    @Override
    public List<BookDTO> getBooks() {
        logger.info("get book list from db");
        List<Book> bookEntities = bookRepository.findAll();

        logger.info("mapping book entities to book dto");
        List<BookDTO> bookDTOs = new ArrayList<>();
        for (Book bookEntity : bookEntities) {
            bookDTOs.add(mapBookEntityToBookDTO(bookEntity));
        }
        return bookDTOs;
    }

    private BookDTO mapBookEntityToBookDTO(Book bookEntity) {
        BookDTO bookDTO = new BookDTO();

        bookDTO.setId(bookEntity.getId());

        bookDTO.setName(bookEntity.getName());

        List<AuthorDTO> authors = bookEntity.getAuthors()
                .stream().map(authorEntity -> {
                    AuthorDTO authorDTO = new AuthorDTO();
                    authorDTO.setName(authorEntity.getAuthor().getName());
                    authorDTO.setSurname(authorEntity.getAuthor().getSurname());
                    return authorDTO;
                })
                .collect(Collectors.toList());
        bookDTO.setAuthors(authors);

        bookDTO.setPublishDate(bookEntity.getPublishDate());

        List<PickDrop> pickDrops = bookEntity.getPickDrops();
        boolean isAvailable = true;
        for (PickDrop pickDrop : pickDrops) {
            if (pickDrop.getStatus().equals(BookLibraryStatus.PICKEDUP)) {
                isAvailable = false;
                break;
            }
        }
        bookDTO.setAvailable(isAvailable);

        if (!bookDTO.isAvailable()) {
            User user = bookEntity.getPickDrops()
                    .stream()
                    .filter(pickDrop -> pickDrop.getStatus().equals(BookLibraryStatus.PICKEDUP))
                    .findFirst()
                    .get()
                    .getUser();
            BookPickedUserDTO bookPickedUser = new BookPickedUserDTO();
            bookPickedUser.setName(user.getName());
            bookPickedUser.setSurname(user.getSurname());
            bookDTO.setPickedUser(bookPickedUser);
        }

        return bookDTO;
    }

    @Override
    public BookDTO getBookByName(String bookName) throws BookNotFoundException {
        Optional<Book> optionalBook = bookRepository.findByName(bookName);
        if (optionalBook.isPresent()) {
            return mapBookEntityToBookDTO(optionalBook.get());
        } else {
            throw new BookNotFoundException("book not found with provided name");
        }
    }

    @Override
    public List<BookDTO> getBooksByCategory(String category) throws BookNotFoundException, CategoryNotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findByName(category);
        if (optionalCategory.isPresent()) {
            List<Book> bookEntities = optionalCategory.get().getBooks();
            if (bookEntities.isEmpty()) {
                throw new BookNotFoundException("book not found with provided category");
            } else {
                List<BookDTO> bookDTOs = new ArrayList<>();
                for (Book bookEntity : bookEntities) {
                    bookDTOs.add(mapBookEntityToBookDTO(bookEntity));
                }
                return bookDTOs;
            }
        } else {
            throw new CategoryNotFoundException("category not found with provided name");
        }
    }

    @Override
    public List<BookDTO> getBooksByAuthor(String authorName) throws BookNotFoundException, AuthorNotFoundException {
        Optional<Author> optionalAuthor = authorRepository.findByName(authorName);
        if (optionalAuthor.isPresent()) {
            List<AuthorBook> bookEntities = optionalAuthor.get().getBooks();
            if (bookEntities.isEmpty()) {
                throw new BookNotFoundException("book not found with provided author");
            } else {
                List<BookDTO> bookDTOs = new ArrayList<>();
                for (AuthorBook authorBook : bookEntities) {
                    bookDTOs.add(mapBookEntityToBookDTO(authorBook.getBook()));
                }
                return bookDTOs;
            }
        } else {
            throw new AuthorNotFoundException("author not found with provided name");
        }
    }

    @Override
    public List<UserPickedUpBookResponseDTO> getSessionUserPickedUpBooks() {
        User sessionUser = getSessionUser();
        User foundUser = userRepository.findById(sessionUser.getId()).get();
        List<PickDrop> pickDrops = foundUser.getPickDrops();
        List<UserPickedUpBookResponseDTO> pickedUpBooks = new ArrayList<>();
        for (PickDrop pickDrop : pickDrops) {
            if (pickDrop.getStatus().equals(BookLibraryStatus.PICKEDUP)) {
                UserPickedUpBookResponseDTO pickedUpBook = mapBookEntityToPickedUpBookDTO(pickDrop.getBook());
                pickedUpBook.setPickUpDate(pickDrop.getPickUpDate());
                pickedUpBooks.add(pickedUpBook);
            }
        }
        return pickedUpBooks;
    }

    private UserPickedUpBookResponseDTO mapBookEntityToPickedUpBookDTO(Book bookEntity) {
        UserPickedUpBookResponseDTO pickedUpBook = new UserPickedUpBookResponseDTO();

        pickedUpBook.setName(bookEntity.getName());

        List<AuthorDTO> authors = bookEntity.getAuthors()
                .stream().map(authorEntity -> {
                    AuthorDTO authorDTO = new AuthorDTO();
                    authorDTO.setName(authorEntity.getAuthor().getName());
                    authorDTO.setSurname(authorEntity.getAuthor().getSurname());
                    return authorDTO;
                })
                .collect(Collectors.toList());
        pickedUpBook.setAuthors(authors);

        pickedUpBook.setPublishDate(bookEntity.getPublishDate());

        return pickedUpBook;
    }

    @Override
    public List<UserLibraryHistoryResponseDTO> getSessionUserLibraryHistory() {
        User sessionUser = getSessionUser();
        User foundUser = userRepository.findById(sessionUser.getId()).get();
        List<PickDrop> pickDrops = foundUser.getPickDrops();
        List<UserLibraryHistoryResponseDTO> libraryHistoryResponseDTOs = new ArrayList<>();
        for (PickDrop pickDrop : pickDrops) {
            UserLibraryHistoryResponseDTO libraryHistoryResponse = mapBookEntityToLibraryHistoryDTO(pickDrop.getBook());
            libraryHistoryResponse.setPickUpDate(pickDrop.getPickUpDate());
            libraryHistoryResponse.setDropOffDate(pickDrop.getDropOffDate());
            libraryHistoryResponseDTOs.add(libraryHistoryResponse);
        }
        return libraryHistoryResponseDTOs;
    }

    private UserLibraryHistoryResponseDTO mapBookEntityToLibraryHistoryDTO(Book bookEntity) {
        UserLibraryHistoryResponseDTO libraryHistoryResponse = new UserLibraryHistoryResponseDTO();

        libraryHistoryResponse.setName(bookEntity.getName());

        List<AuthorDTO> authors = bookEntity.getAuthors()
                .stream().map(authorEntity -> {
                    AuthorDTO authorDTO = new AuthorDTO();
                    authorDTO.setName(authorEntity.getAuthor().getName());
                    authorDTO.setSurname(authorEntity.getAuthor().getSurname());
                    return authorDTO;
                })
                .collect(Collectors.toList());
        libraryHistoryResponse.setAuthors(authors);

        libraryHistoryResponse.setPublishDate(bookEntity.getPublishDate());

        return libraryHistoryResponse;
    }

    @Override
    public List<BookDTO> getEligibleBooks() {
        List<Book> books = bookRepository.findAll();

        List<PickDrop> userLibraryHistory = pickDropRepository.findAll();
        List<Long> pickedBooks = userLibraryHistory
                .stream()
                .filter(pickDrop -> pickDrop.getStatus().equals(BookLibraryStatus.PICKEDUP))
                .map(pickDrop -> pickDrop.getBook().getId())
                .collect(Collectors.toList());
        return books
                .stream()
                .filter(book -> !(pickedBooks.contains(book.getId())))
                .map(this::mapBookEntityToBookDTO)
                .collect(Collectors.toList());
    }
}
