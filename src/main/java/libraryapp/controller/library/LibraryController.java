package libraryapp.controller.library;

import libraryapp.dto.library.DropOffLibraryBookRequestDTO;
import libraryapp.dto.library.DropOffLibraryBookResponseDTO;
import libraryapp.dto.library.PickUpLibraryBookRequestDTO;
import libraryapp.dto.library.PickUpLibraryBookResponseDTO;
import libraryapp.exception.PickedBookNotFoundException;
import libraryapp.exception.BookAlreadyPickedUpException;
import libraryapp.exception.BookNotFoundException;
import libraryapp.service.LibraryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/pick-up")
    public PickUpLibraryBookResponseDTO pickUp(@RequestBody PickUpLibraryBookRequestDTO request) throws BookNotFoundException, BookAlreadyPickedUpException {
        return libraryService.pickUp(request);
    }

    @PostMapping("/drop-off")
    public DropOffLibraryBookResponseDTO dropOff(@RequestBody DropOffLibraryBookRequestDTO request) throws PickedBookNotFoundException {
        return libraryService.dropOff(request);
    }
}
