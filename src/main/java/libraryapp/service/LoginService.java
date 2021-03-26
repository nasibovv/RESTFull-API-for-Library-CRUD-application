package libraryapp.service;

import libraryapp.dto.login.UserLoginRequestDTO;
import libraryapp.dto.login.UserLoginResponseDTO;

public interface LoginService {
    UserLoginResponseDTO login(UserLoginRequestDTO request);
}
