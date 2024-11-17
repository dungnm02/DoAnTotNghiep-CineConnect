package dungnm243.cineconnect.services.impl;

import dungnm243.cineconnect.models.User;
import dungnm243.cineconnect.repositories.UserRepository;
import dungnm243.cineconnect.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User checkLogin(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public List<String> register(User user) {
        List<String> errorList = validateUserInformation(user);
        if (errorList.isEmpty()) {
            try {
                // Mã hóa password
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
            } catch (Exception e) {
                errorList.add("Failed to create user");
            }
        }
        return errorList;
    }

    /**
     * Kiểm tra thông tin user
     *
     * @param user thông tin user
     * @return List<String> danh sách lỗi
     */
    private List<String> validateUserInformation(User user) {
        List<String> errorList = new ArrayList<>();

        // Kiểm tra username & email đã tồn tại chưa
        if (userRepository.existsByUsername(user.getUsername())) {
            errorList.add("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            errorList.add("Email already exists");
        }
        // Kiểm tra password có 8 ký tự trở lên
        if (user.getPassword().length() < 8) {
            errorList.add("Password must be at least 8 characters");
        }

        return errorList;
    }
}
