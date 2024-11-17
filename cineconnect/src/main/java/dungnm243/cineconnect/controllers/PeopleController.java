package dungnm243.cineconnect.controllers;

import dungnm243.cineconnect.models.People;
import dungnm243.cineconnect.repositories.PeopleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/people")
public class PeopleController {
    private final PeopleRepository peopleRepository;

    public PeopleController(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @GetMapping
    public ResponseEntity<Page<People>> getAllPeople(
            @RequestParam(name = "page_number", defaultValue = "0") int pageNumber,
            @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(peopleRepository.findAll(PageRequest.of(pageNumber, pageSize)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<People> getPeopleById(@PathVariable long id) {
        return ResponseEntity.ok(peopleRepository.findById(id).orElse(null));
    }

    @PostMapping
    public ResponseEntity<String> createPeople(@RequestBody People people) {
        try {
            peopleRepository.save(people);
            return ResponseEntity.ok("People created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create people");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePeople(@PathVariable long id, @RequestBody People people) {

        if (!peopleRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("People not found");
        }
        try {
            people.setId(id);
            peopleRepository.save(people);
            return ResponseEntity.ok("People updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update people");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePeople(@PathVariable long id) {
        if (!peopleRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("People not found");
        }
        try {
            peopleRepository.deleteById(id);
            return ResponseEntity.ok("People deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete people");
        }
    }
}
