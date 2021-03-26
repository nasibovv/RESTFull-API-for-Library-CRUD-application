package libraryapp.service.impl;

import libraryapp.dto.register.UserRegistrationRequestDTO;
import libraryapp.dto.register.UserRegistrationResponseDTO;
import libraryapp.exception.UserAlreadyRegisteredException;
import libraryapp.model.User;
import libraryapp.repository.UserRepository;
import libraryapp.service.RegistrationService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public RegisterServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Override
    public UserRegistrationResponseDTO register(UserRegistrationRequestDTO request) throws UserAlreadyRegisteredException {
        logger.info("user registration service started");

        logger.info("check user existence");
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyRegisteredException("user already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setLibraryId(RandomStringUtils.randomAlphanumeric(7));
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        String encodedPassword = encoder.encode(request.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        UserRegistrationResponseDTO response = new UserRegistrationResponseDTO();
        response.setMessage("user successfully registered");

        logger.info("user registration service ended");
        return response;
    }
}
