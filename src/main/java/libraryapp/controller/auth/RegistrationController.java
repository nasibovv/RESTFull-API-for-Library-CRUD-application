package libraryapp.controller.auth;

import libraryapp.dto.register.UserRegistrationRequestDTO;
import libraryapp.dto.register.UserRegistrationResponseDTO;
import libraryapp.exception.UserAlreadyRegisteredException;
import libraryapp.service.RegistrationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public UserRegistrationResponseDTO register(@RequestBody UserRegistrationRequestDTO request) throws UserAlreadyRegisteredException {
        return registrationService.register(request);
    }
}
