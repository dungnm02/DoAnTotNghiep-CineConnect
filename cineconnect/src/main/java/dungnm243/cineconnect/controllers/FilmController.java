package dungnm243.cineconnect.controllers;

import dungnm243.cineconnect.models.Film;
import dungnm243.cineconnect.models.NormalUser;
import dungnm243.cineconnect.models.Rating;
import dungnm243.cineconnect.models.Review;
import dungnm243.cineconnect.repositories.FilmRepository;
import dungnm243.cineconnect.repositories.RatingRepository;
import dungnm243.cineconnect.repositories.ReportRepository;
import dungnm243.cineconnect.repositories.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/films")
public class FilmController {

    private final FilmRepository filmRepository;
    private final RatingRepository ratingRepository;
    private final ReviewRepository reviewRepository;
    private final ReportRepository reportRepository;

    public FilmController(FilmRepository filmRepository, RatingRepository ratingRepository, ReviewRepository reviewRepository, ReportRepository reportRepository) {
        this.filmRepository = filmRepository;
        this.ratingRepository = ratingRepository;
        this.reviewRepository = reviewRepository;
        this.reportRepository = reportRepository;
    }

    /**
     * Lấy danh sách phim có phân trang
     *
     * @param pageNumber số trang
     * @param pageSize   số phim trên mỗi trang
     * @return Page<Film>
     */
    @GetMapping
    public ResponseEntity<Page<Film>> getFilmList(@RequestParam(name = "page_number", defaultValue = "0") int pageNumber,
                                                  @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        Page<Film> filmList = filmRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return new ResponseEntity<>(filmList, HttpStatus.OK);
    }

    /**
     * Lấy thông tin phim theo id
     *
     * @param id id của phim
     * @return Film
     */
    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilm(@PathVariable long id) {
        Film film = filmRepository.findById(id).orElse(null);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    /**
     * Tạo phim mới
     *
     * @param film thông tin phim
     * @return String
     */
    @PostMapping
    public ResponseEntity<String> createFilm(@RequestBody Film film) {
        try {
            filmRepository.save(film);
            return new ResponseEntity<>("Film created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create film", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Cập nhật thông tin phim
     *
     * @param id   id của phim
     * @param film thông tin phim
     * @return String
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateFilm(@PathVariable long id, @RequestBody Film film) {
        if (!filmRepository.existsById(id)) {
            return new ResponseEntity<>("Film not found", HttpStatus.NOT_FOUND);
        }

        try {
            film.setId(id);
            filmRepository.save(film);
            return new ResponseEntity<>("Film updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update film", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Xóa phim
     *
     * @param id id của phim
     * @return String
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFilm(@PathVariable long id) {
        if (!filmRepository.existsById(id)) {
            return new ResponseEntity<>("Film not found", HttpStatus.NOT_FOUND);
        }

        try {
            filmRepository.deleteById(id);
            return new ResponseEntity<>("Film deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete film", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Tìm kiếm phim theo tên
     *
     * @param pageNumber số trang
     * @param pageSize   số phim trên mỗi trang
     * @param keyword    từ khóa tìm kiếm
     * @return Page<Film>
     */
    @GetMapping("/search")
    public ResponseEntity<Page<Film>> searchFilm(@RequestParam(name = "page_number", defaultValue = "0") int pageNumber,
                                                 @RequestParam(name = "page_size", defaultValue = "10") int pageSize,
                                                 @RequestParam(name = "keyword") String keyword) {

        return new ResponseEntity<>(filmRepository.findByFilmNameContaining(keyword, PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    /**
     * Đánh giá phim
     *
     * @param filmId id của phim
     * @param rating thông tin đánh giá
     * @return String
     */
    @PostMapping("/{filmId}/rating")
    public ResponseEntity<String> rateFilm(@PathVariable long filmId, @RequestBody Rating rating) {
        Film film = filmRepository.findById(filmId).orElse(null);
        if (film == null) {
            return new ResponseEntity<>("Film not found", HttpStatus.NOT_FOUND);
        }
        NormalUser user = (NormalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Kiểm tra xem có đugns là người dùng đăng nhập không
        if (user == null) {
            return new ResponseEntity<>("You are not authorized to rate this film", HttpStatus.UNAUTHORIZED);
        }
        try {
            ratingRepository.save(rating);
            return new ResponseEntity<>("Rating successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to rate film", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Đánh giá phim
     *
     * @param filmId id của phim
     * @param review thông tin đánh giá
     * @return String
     */
    @PostMapping("/{filmId}/review")
    public ResponseEntity<String> reviewFilm(@PathVariable long filmId, @RequestBody Review review) {
        Film film = filmRepository.findById(filmId).orElse(null);
        if (film == null) {
            return new ResponseEntity<>("Film not found", HttpStatus.NOT_FOUND);
        }
        NormalUser user = (NormalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Kiểm tra xem có đugns là người dùng đăng nhập không
        if (user == null) {
            return new ResponseEntity<>("You are not authorized to review this film", HttpStatus.UNAUTHORIZED);
        }
        try {
            reviewRepository.save(review);
            return new ResponseEntity<>("Review successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to review film", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{filmId}/report")
    public ResponseEntity<String> reportFilm(@PathVariable long filmId, @RequestBody String report) {
        Film film = filmRepository.findById(filmId).orElse(null);
        if (film == null) {
            return new ResponseEntity<>("Film not found", HttpStatus.NOT_FOUND);
        }
        NormalUser user = (NormalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Kiểm tra xem có đugns là người dùng đăng nhập không
        if (user == null) {
            return new ResponseEntity<>("You are not authorized to report this film", HttpStatus.UNAUTHORIZED);
        }
        try {
            return new ResponseEntity<>("Report successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to report film", HttpStatus.BAD_REQUEST);
        }
    }
}
