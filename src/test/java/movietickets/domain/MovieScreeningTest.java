package movietickets.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MovieScreeningTest {

	@Autowired
	private CinemaService cinemaService;
	
	@Autowired
	private MovieScreeningService movieScreeningService;
	
	@Autowired
	private MovieService movieService;
	
	@Test
	void test_movie_and_movie_screening_creation() {
		
		
		Movie movie = new Movie(5L, "Sample Title", "Summary", "Directors", "Actors", 400, "Poster Link", "TrailerLink");
		LocalDate date = LocalDate.parse("2021-05-24");
		LocalTime time = LocalTime.parse("14:30");
		Cinema existingCinema = cinemaService.findById(1L);
		MovieScreening movieScreening = new MovieScreening(8L, movie, existingCinema, date, time);
		movieScreeningService.insertNewMovieScreening(movieScreening);
		
		Movie lastInsertedMovie = movieService.findLastInserted();
		assertEquals(movie, lastInsertedMovie);
		
		MovieScreening lastInsertedMovieScreening = movieScreeningService.findLastInserted();
		assertEquals(movieScreening, lastInsertedMovieScreening);
	}

}
