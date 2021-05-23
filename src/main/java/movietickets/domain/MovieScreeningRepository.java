package movietickets.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MovieScreeningRepository extends CrudRepository<MovieScreening, Long> {

	
	Optional<MovieScreening> findById(Long id);
	
	@Query("FROM MovieScreening m WHERE m.scheduleDate = CURRENT_DATE AND m.movie = ?1")
	List<MovieScreening> findByMovieAndScheduledToday(Movie movie);
	
	@Query("FROM MovieScreening m WHERE m.scheduleDate > CURRENT_DATE AND m.movie = ?1")
	List<MovieScreening> findByMovieAndScheduledSoon(Movie movie);
	
	List<MovieScreening> findByMovie(Movie movie);	
	
	@Query("SELECT DISTINCT movie FROM MovieScreening m WHERE m.scheduleDate = CURRENT_DATE")
	List<Movie> findMovieByScheduledToday();
	
}
