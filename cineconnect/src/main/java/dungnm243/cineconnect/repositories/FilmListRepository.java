package dungnm243.cineconnect.repositories;

import dungnm243.cineconnect.models.FilmList;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmListRepository extends JpaRepository<FilmList, Long> {
    Page<FilmList> findByUserId(long userId);
}
