package movietickets.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface SeatRepository extends CrudRepository<Seat, Long>{
	
	Optional<Seat> findById(Long id);
	
}
