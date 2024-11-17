package dungnm243.cineconnect.controllers;

import dungnm243.cineconnect.models.Role;
import dungnm243.cineconnect.repositories.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/roles")
public class RoleController {
    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public ResponseEntity<Page<Role>> getAllRoles(
            @RequestParam(name = "page_number", defaultValue = "0") int pageNumber,
            @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(roleRepository.findAll(PageRequest.of(pageNumber, pageSize)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@RequestParam long id) {
        return ResponseEntity.ok(roleRepository.findById(id).orElse(null));
    }

    @PostMapping
    public ResponseEntity<String> createRole(@RequestBody Role role) {
        try {
            roleRepository.save(role);
            return ResponseEntity.ok("Role created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create role");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRole(@PathVariable long id, @RequestBody Role role) {
        if (!roleRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Role not found");
        }
        try {
            role.setId(id);
            roleRepository.save(role);
            return ResponseEntity.ok("Role updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update role");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable long id) {
        if (!roleRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Role not found");
        }
        try {
            roleRepository.deleteById(id);
            return ResponseEntity.ok("Role deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete role");
        }
    }
}
