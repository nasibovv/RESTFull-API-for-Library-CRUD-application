package libraryapp.controller.auth;

import libraryapp.dto.login.UserLoginRequestDTO;
import libraryapp.dto.login.UserLoginResponseDTO;
import libraryapp.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginRequestDTO request) {
        return loginService.login(request);
    }

}
