package movietickets.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface MovieRepository  extends CrudRepository<Movie, Long>{
	
	Optional<Movie> findById(Long movieId);
	
}
