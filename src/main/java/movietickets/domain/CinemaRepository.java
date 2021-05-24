package movietickets.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CinemaRepository extends CrudRepository<Cinema, Long> {
	Optional<Cinema> findById(Long id);
}
