package libraryapp.service;

import libraryapp.dto.library.DropOffLibraryBookRequestDTO;
import libraryapp.dto.library.DropOffLibraryBookResponseDTO;
import libraryapp.dto.library.PickUpLibraryBookRequestDTO;
import libraryapp.dto.library.PickUpLibraryBookResponseDTO;
import libraryapp.exception.PickedBookNotFoundException;
import libraryapp.exception.BookAlreadyPickedUpException;
import libraryapp.exception.BookNotFoundException;

public interface LibraryService {
    PickUpLibraryBookResponseDTO pickUp(PickUpLibraryBookRequestDTO request) throws BookNotFoundException, BookAlreadyPickedUpException;

    DropOffLibraryBookResponseDTO dropOff(DropOffLibraryBookRequestDTO request) throws PickedBookNotFoundException;
}
