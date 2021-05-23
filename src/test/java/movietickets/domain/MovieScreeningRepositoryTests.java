package movietickets.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MovieScreeningRepositoryTests {

	@Autowired
	private MovieScreeningRepository movieScreeningRepository;
	

	@Test
	void find_movie_screening_by_movie() throws Exception {
		List<LocalTime> scheduleTimes = Arrays.asList(LocalTime.parse("10:15:00"), LocalTime.parse("12:15:00"));
		List<MovieScreening> movieScreenings = movieScreeningRepository.findByMovie(new Movie(1L));
		assertTrue(movieScreenings.size() > 0);
		for (MovieScreening movieScreening : movieScreenings) {
			assertNotNull(movieScreening);
			assertEquals("Avengers: Endgame", movieScreening.getMovie().getTitle());
			assertEquals(1, movieScreening.getCinema().getId());
			assertEquals(LocalDate.parse("2021-05-24"), movieScreening.getScheduleDate());
			assertThat(scheduleTimes.contains(movieScreening.getScheduleTime()));
		}
		
	}

	@Test
	void find_movie_screening_by_movie_not_found() throws Exception {
		assertEquals(List.of().size(), movieScreeningRepository.findByMovie(new Movie(4L)).size());
	}

	@Test
	void find_schedule_today() throws Exception {
		List<MovieScreening> movieScreenings = movieScreeningRepository.findByMovieAndScheduledToday(new Movie(3L));
		assertThat(movieScreenings).hasSize(2);
	}
	
}
