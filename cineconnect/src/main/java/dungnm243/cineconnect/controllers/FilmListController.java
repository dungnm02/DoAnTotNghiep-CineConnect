package dungnm243.cineconnect.controllers;

import dungnm243.cineconnect.models.FilmList;
import dungnm243.cineconnect.models.NormalUser;
import dungnm243.cineconnect.models.User;
import dungnm243.cineconnect.repositories.FilmListRepository;
import dungnm243.cineconnect.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/film-lists")
public class FilmListController {
    private final UserRepository userRepository;
    private final FilmListRepository filmListRepository;

    public FilmListController(UserRepository userRepository, FilmListRepository filmListRepository) {
        this.userRepository = userRepository;
        this.filmListRepository = filmListRepository;
    }

    @GetMapping("{userId}")
    public ResponseEntity<Page<FilmList>> getFilmListByUserId(@PathVariable long userId,
                                                              @RequestParam(name = "page_number", defaultValue = "0") int pageNumber,
                                                              @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(filmListRepository.findByUserId(userId));
    }

    @GetMapping("/{filmListId}")
    public ResponseEntity<FilmList> getFilmListById(@PathVariable long filmListId) {
        return ResponseEntity.ok(filmListRepository.findById(filmListId).orElse(null));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> createFilmList(@PathVariable long userId, @RequestBody FilmList filmList) {
        NormalUser user = (NormalUser) userRepository.findById(userId).orElse(null);
        // Kiểm tra xem người dùng có tồn tại không
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        // Kiểm tra xem người dùng yêu cầu tạo film list có phải là chính họ không
        User userRequesting = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userRequesting.getId() != userId) {
            return ResponseEntity.badRequest().body("You are not authorized to create film list for this user");
        }
        try {
            FilmList savedFilmList = filmListRepository.save(filmList);
            user.getFilmList().add(savedFilmList);
            userRepository.save(user);
            return ResponseEntity.ok("Film list created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create film list");
        }
    }

    @PutMapping("/{userId}/{filmListId}")
    public ResponseEntity<String> updateFilmList(@PathVariable long userId, @PathVariable long filmListId, @RequestBody FilmList filmList) {
        if (!filmListRepository.existsById(filmListId)) {
            return ResponseEntity.badRequest().body("Film list not found");
        }
        NormalUser user = (NormalUser) userRepository.findById(userId).orElse(null);
        // Kiểm tra xem người dùng có tồn tại không
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        // Kiểm tra xem người dùng yêu cầu tạo film list có phải là chính họ không
        User userRequesting = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userRequesting.getId() != userId) {
            return ResponseEntity.badRequest().body("You are not authorized to create film list for this user");
        }

        try {
            filmList.setId(filmListId);
            filmListRepository.save(filmList);
            return ResponseEntity.ok("Film list updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update film list");
        }
    }

    @DeleteMapping("/{userId}/{filmListId}")
    public ResponseEntity<String> deleteFilmList(@PathVariable long userId, @PathVariable long filmListId) {
        if (!filmListRepository.existsById(filmListId)) {
            return ResponseEntity.badRequest().body("Film list not found");
        }
        NormalUser user = (NormalUser) userRepository.findById(userId).orElse(null);
        // Kiểm tra xem người dùng có tồn tại không
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        // Kiểm tra xem người dùng yêu cầu tạo film list có phải là chính họ không
        User userRequesting = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userRequesting.getId() != userId) {
            return ResponseEntity.badRequest().body("You are not authorized to create film list for this user");
        }

        try {
            filmListRepository.deleteById(filmListId);
            return ResponseEntity.ok("Film list deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete film list");
        }
    }

}
