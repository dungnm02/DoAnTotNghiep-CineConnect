package dungnm243.cineconnect.repositories;

import dungnm243.cineconnect.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String keyword);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
