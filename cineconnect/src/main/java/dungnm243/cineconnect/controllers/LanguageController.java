package dungnm243.cineconnect.controllers;

import dungnm243.cineconnect.models.Language;
import dungnm243.cineconnect.repositories.LanguageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/languages")
public class LanguageController {
    private final LanguageRepository languageRepository;


    public LanguageController(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @GetMapping
    public ResponseEntity<Page<Language>> getAllLanguages(@RequestParam(name = "page_number", defaultValue = "0") int pageNumber,
                                                          @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(languageRepository.findAll(PageRequest.of(pageNumber, pageSize)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Language> getLanguageById(@PathVariable long id) {
        return ResponseEntity.ok(languageRepository.findById(id).orElse(null));
    }

    @PostMapping
    public ResponseEntity<String> createLanguage(@RequestBody Language language) {
        try {
            languageRepository.save(language);
            return ResponseEntity.ok("Language created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create language");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateLanguage(@PathVariable long id, @RequestBody Language language) {

        if (!languageRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Language not found");
        }
        try {
            language.setId(id);
            languageRepository.save(language);
            return ResponseEntity.ok("Language updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update language");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLanguage(@PathVariable long id) {
        if (!languageRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Language not found");
        }
        try {
            languageRepository.deleteById(id);
            return ResponseEntity.ok("Language deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete language");
        }
    }
}
