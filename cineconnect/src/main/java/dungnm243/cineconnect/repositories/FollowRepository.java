package dungnm243.cineconnect.repositories;

import dungnm243.cineconnect.models.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
}
