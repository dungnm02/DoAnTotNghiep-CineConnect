package dungnm243.cineconnect.controllers;

import dungnm243.cineconnect.models.Studio;
import dungnm243.cineconnect.repositories.StudioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/studios")
public class StudioController {
    private final StudioRepository studioRepository;

    public StudioController(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    @GetMapping
    public ResponseEntity<Page<Studio>> getAllStudios(
            @RequestParam(name = "page_number", defaultValue = "0") int pageNumber,
            @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(studioRepository.findAll(PageRequest.of(pageNumber, pageSize)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Studio> getStudioById(@PathVariable long id) {
        return ResponseEntity.ok(studioRepository.findById(id).orElse(null));
    }

    @PostMapping
    public ResponseEntity<String> createStudio(@RequestBody Studio studio) {
        try {
            studioRepository.save(studio);
            return ResponseEntity.ok("Studio created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create studio");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudio(@PathVariable long id, @RequestBody Studio studio) {

        if (!studioRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Studio not found");
        }
        try {
            studio.setId(id);
            studioRepository.save(studio);
            return ResponseEntity.ok("Studio updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update studio");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudio(@PathVariable long id) {
        if (!studioRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Studio not found");
        }
        try {
            studioRepository.deleteById(id);
            return ResponseEntity.ok("Studio deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete studio");
        }
    }

}
