package movietickets.domain;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieScreeningService {
	
	@Autowired
	private MovieScreeningRepository movieScreeningRepository;

	@Autowired
	private SeatRepository seatRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public MovieScreening findByMovieScreeningId(Long movieScreeningId) {
		return movieScreeningRepository.findById(movieScreeningId).get();
	}
		
	public List<MovieScreening> findByMovieAndScheduledToday(Long movieScreeningId) {
		Iterable<MovieScreening> movieScreeningIterable =  movieScreeningRepository.findByMovieAndScheduledToday(new Movie(movieScreeningId));
		List<MovieScreening> movieScreeningList = new ArrayList<>();
		movieScreeningIterable.forEach(e -> movieScreeningList.add(e));
		
		return movieScreeningList;
	}
	
	public List<MovieScreening> findByMovieAndScheduledSoon(Long movieScreeningId) {
		Iterable<MovieScreening> movieScreeningIterable =  movieScreeningRepository.findByMovieAndScheduledSoon(new Movie(movieScreeningId));
		List<MovieScreening> movieScreeningList = new ArrayList<>();
		movieScreeningIterable.forEach(e -> movieScreeningList.add(e));
		
		return movieScreeningList;
	}
	
	public List<Movie> findMovieByScheduledToday() {
		Iterable<Movie> movieIterable =  movieScreeningRepository.findMovieByScheduledToday();
		List<Movie> movieList = new ArrayList<>();
		movieIterable.forEach(e -> movieList.add(e));
		
		return movieList;
	}
	
	@Lock(LockModeType.PESSIMISTIC_READ)
	public Seat findSeatByRowAndColumnAndMovieScreeningId(String row, int column, Long movieScreeningId) {
		Cinema cinema = findByMovieScreeningId(movieScreeningId).getCinema();
		return seatRepository.findByRowAndColumnAndCinema(row, column, cinema).get();
	}
	
	@Lock(LockModeType.PESSIMISTIC_READ)
	public Collection<Seat> findSeatsByMovieScreeningId(Long movieScreeningId) {
		Cinema cinema = findByMovieScreeningId(movieScreeningId).getCinema();
		return seatRepository.findByCinema(cinema);
	}
	
	@Lock(LockModeType.PESSIMISTIC_READ)
	public Collection<Seat> findReservedSeatsByMovieScreeningId(Long movieScreeningId){	
		MovieScreening movieScreening = new MovieScreening(movieScreeningId);
		return movieScreening.getReservedSeats();
	}
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Transactional
	public void reserveSeatByMovieScreeningId(Seat seat, Long movieScreeningId) {
		MovieScreening movieScreening = findByMovieScreeningId(movieScreeningId);
		movieScreening.reserveSeat(seat);
		// TODO: Add tickets?
		entityManager.persist(movieScreening);
	}
	
}
