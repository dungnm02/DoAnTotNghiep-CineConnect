package dungnm243.cineconnect.controllers;

import dungnm243.cineconnect.models.User;
import dungnm243.cineconnect.responses.LoginResponse;
import dungnm243.cineconnect.services.JwtService;
import dungnm243.cineconnect.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtService jwtService;
    private final UserService userService;

    public AuthenticationController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User user) {
        User userLogin = userService.checkLogin(user.getUsername(), user.getPassword());

        if (userLogin != null) {
            String token = jwtService.generateToken(userLogin);
            LoginResponse loginResponse = new LoginResponse(token, userLogin);
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register")
    public ResponseEntity<List<String>> register(@RequestBody User user) {
        List<String> errorList = userService.register(user);
        if (errorList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
    }
}
