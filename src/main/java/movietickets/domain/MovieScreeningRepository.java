package movietickets.domain;

import java.time.LocalDate;
import java.time.LocalTime;
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
	
	@Query("FROM MovieScreening m WHERE m.cinema = ?1 AND m.scheduleDate = ?2 AND m.scheduleTime = ?3")
	List<MovieScreening> findByCinemaAndSchedule(Cinema cinema, LocalDate date, LocalTime time);
	
	@Query("FROM MovieScreening m where m.id = (Select max(id) from MovieScreening)")
	Optional<MovieScreening> findLastInserted();
	
}
