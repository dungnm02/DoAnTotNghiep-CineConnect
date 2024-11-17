package dungnm243.cineconnect.controllers;

import dungnm243.cineconnect.models.Genre;
import dungnm243.cineconnect.repositories.GenreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping
    public ResponseEntity<Page<Genre>> getAllGenres(
            @RequestParam(name = "page_number", defaultValue = "0") int pageNumber,
            @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(genreRepository.findAll(PageRequest.of(pageNumber, pageSize)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable long id) {
        return ResponseEntity.ok(genreRepository.findById(id).orElse(null));
    }

    @PostMapping
    public ResponseEntity<String> createGenre(@RequestBody Genre genre) {
        try {
            genreRepository.save(genre);
            return ResponseEntity.ok("Genre created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create genre");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGenre(@PathVariable long id, @RequestBody Genre genre) {

        if (!genreRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Genre not found");
        }
        try {
            genre.setId(id);
            genreRepository.save(genre);
            return ResponseEntity.ok("Genre updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update genre");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable long id) {
        if (!genreRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Genre not found");
        }
        try {
            genreRepository.deleteById(id);
            return ResponseEntity.ok("Genre deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete genre");
        }
    }
}
