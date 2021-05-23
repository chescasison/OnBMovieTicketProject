package movietickets.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository  extends CrudRepository<Movie, Long>{
	
	Optional<Movie> findById(Long id);
	List<Movie> findByTitle(String title);
	
}
