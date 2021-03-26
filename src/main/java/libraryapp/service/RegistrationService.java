package libraryapp.service;

import libraryapp.dto.register.UserRegistrationResponseDTO;
import libraryapp.dto.register.UserRegistrationRequestDTO;
import libraryapp.exception.UserAlreadyRegisteredException;

public interface RegistrationService {
    UserRegistrationResponseDTO register(UserRegistrationRequestDTO request) throws UserAlreadyRegisteredException;
}
