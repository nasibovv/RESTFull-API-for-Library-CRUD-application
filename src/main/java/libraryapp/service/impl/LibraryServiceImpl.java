package libraryapp.service.impl;

import libraryapp.constant.BookLibraryStatus;
import libraryapp.dto.library.DropOffLibraryBookRequestDTO;
import libraryapp.dto.library.DropOffLibraryBookResponseDTO;
import libraryapp.dto.library.PickUpLibraryBookRequestDTO;
import libraryapp.dto.library.PickUpLibraryBookResponseDTO;
import libraryapp.exception.PickedBookNotFoundException;
import libraryapp.exception.BookAlreadyPickedUpException;
import libraryapp.exception.BookNotFoundException;
import libraryapp.model.Book;
import libraryapp.model.PickDrop;
import libraryapp.model.User;
import libraryapp.repository.BookRepository;
import libraryapp.repository.PickDropRepository;
import libraryapp.repository.UserRepository;
import libraryapp.security.service.UserPrincipal;
import libraryapp.service.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;
    private final PickDropRepository pickDropRepository;
    private final UserRepository userRepository;

    public LibraryServiceImpl(BookRepository bookRepository, PickDropRepository pickDropRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.pickDropRepository = pickDropRepository;
        this.userRepository = userRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(LibraryServiceImpl.class);

    private User getSessionUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User sessionUser = userPrincipal.getUser();
        logger.info("getting user from session. user email: {}", sessionUser.getEmail());
        return sessionUser;
    }


    @Override
    public PickUpLibraryBookResponseDTO pickUp(PickUpLibraryBookRequestDTO request) throws BookNotFoundException, BookAlreadyPickedUpException {

        Optional<Book> optionalBook = bookRepository.findById(request.getBookId());

        if (optionalBook.isPresent()) {
            List<PickDrop> pickDrops = optionalBook.get().getPickDrops();
            for (PickDrop pickDrop : pickDrops) {
                if (pickDrop.getStatus().equals(BookLibraryStatus.PICKEDUP)) {
                    throw new BookAlreadyPickedUpException("book already picked up");
                }
            }

            User sessionUser = getSessionUser();

            PickDrop pickDrop = new PickDrop();
            pickDrop.setBook(optionalBook.get());
            pickDrop.setUser(sessionUser);
            pickDrop.setStatus(BookLibraryStatus.PICKEDUP);
            pickDrop.setPickUpDate(new Date());

            pickDropRepository.save(pickDrop);

            PickUpLibraryBookResponseDTO response = new PickUpLibraryBookResponseDTO();
            response.setMessage("book successfully picked up!");

            return response;
        } else {
            throw new BookNotFoundException("book not found with provided id");
        }
    }

    @Override
    public DropOffLibraryBookResponseDTO dropOff(DropOffLibraryBookRequestDTO request) throws PickedBookNotFoundException {

        User sessionUser = getSessionUser();

        User foundUser = userRepository.findById(sessionUser.getId()).get();
        List<PickDrop> pickDrops = foundUser.getPickDrops();

        Optional<PickDrop> optionalPickDrop = pickDrops
                .stream()
                .filter(pickDrop -> pickDrop.getBook().getId().equals(request.getBookId()))
                .filter(pickDrop -> pickDrop.getStatus().equals(BookLibraryStatus.PICKEDUP))
                .findFirst();

        if (optionalPickDrop.isEmpty()) {
            throw new PickedBookNotFoundException("picked book not found");
        }

        PickDrop pickDrop = optionalPickDrop.get();
        pickDrop.setStatus(BookLibraryStatus.DROPPEDOFF);
        pickDrop.setDropOffDate(new Date());
        pickDropRepository.save(pickDrop);

        DropOffLibraryBookResponseDTO response = new DropOffLibraryBookResponseDTO();
        response.setMessage("book successfully dropped off");

        return response;
    }
}
