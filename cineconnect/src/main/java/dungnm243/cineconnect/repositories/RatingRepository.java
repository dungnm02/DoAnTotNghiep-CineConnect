package dungnm243.cineconnect.repositories;

import dungnm243.cineconnect.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByUserId(long userId);

    Rating findByFilmIdAndUserId(long filmId, long id);
}
