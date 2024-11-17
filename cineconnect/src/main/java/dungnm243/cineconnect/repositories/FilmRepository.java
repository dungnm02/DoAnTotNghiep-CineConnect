package dungnm243.cineconnect.repositories;

import dungnm243.cineconnect.models.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    Page<Film> findByFilmNameContaining(String keyword, PageRequest of);
}
