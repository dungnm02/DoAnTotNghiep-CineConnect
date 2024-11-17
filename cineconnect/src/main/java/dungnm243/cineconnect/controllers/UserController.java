package dungnm243.cineconnect.controllers;

import dungnm243.cineconnect.models.User;
import dungnm243.cineconnect.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<Page<User>> getUserList(@RequestParam(name = "page_number", defaultValue = "0") int pageNumber,
                                                  @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        Page<User> userList = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User user = userRepository.findById(id).orElse(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userRepository.save(user);
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create user", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable long id, @RequestBody User user) {
        try {
            userRepository.save(user);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update user", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete user", HttpStatus.BAD_REQUEST);
        }
    }


}
