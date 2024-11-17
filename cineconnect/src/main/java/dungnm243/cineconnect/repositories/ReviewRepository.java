package dungnm243.cineconnect.repositories;

import dungnm243.cineconnect.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Objects;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Objects findByUserId(long userId);
}
