package libraryapp.service.impl;

import libraryapp.dto.login.UserLoginRequestDTO;
import libraryapp.dto.login.UserLoginResponseDTO;
import libraryapp.security.util.JwtUtil;
import libraryapp.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Override
    public UserLoginResponseDTO login(UserLoginRequestDTO request) {
        logger.info("user login service started");

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtil.generateToken(authentication);
        logger.info("generate access token, token: {}", accessToken);

        UserLoginResponseDTO response = new UserLoginResponseDTO();
        response.setAccessToken(accessToken);

        logger.info("user login service ended");
        return response;
    }
}
