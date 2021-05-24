package movietickets.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	private MovieService movieService;
	
	@Autowired
	private ReservedSeatService reservedSeatService;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/*
	public List<MovieScreening> findAll() {
		Iterable<MovieScreening> movieScreeningIterable =  movieScreeningRepository.findAll();
		List<MovieScreening> movieScreeningList = new ArrayList<>();
		movieScreeningIterable.forEach(e -> movieScreeningList.add(e));
		
		return movieScreeningList;
	}
	*/
	
	public Optional<MovieScreening> findById(Long id) {
		Optional<MovieScreening> movieScreening = movieScreeningRepository.findById(id);
		return movieScreening;
	}
	
	public List<MovieScreening> findByMovieAndScheduledToday(Long id) {
		Iterable<MovieScreening> movieScreeningIterable =  movieScreeningRepository.findByMovieAndScheduledToday(new Movie(id));
		List<MovieScreening> movieScreeningList = new ArrayList<>();
		movieScreeningIterable.forEach(e -> movieScreeningList.add(e));
		
		return movieScreeningList;
	}
	
	public List<MovieScreening> findByMovieAndScheduledSoon(Long id) {
		Iterable<MovieScreening> movieScreeningIterable =  movieScreeningRepository.findByMovieAndScheduledSoon(new Movie(id));
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
	
	public List<ReservedSeat> getReservedSeatsOfMovieScreening(Long movieScreeningId){
		List<ReservedSeat> reservedSeats = reservedSeatService.findByMovieScreening(new MovieScreening(movieScreeningId));
		return reservedSeats;
	}
	
	public List<MovieScreening> findByCinemaAndSchedule(Cinema cinema, LocalDate date, LocalTime time){
		List<MovieScreening> movieScreenings = movieScreeningRepository.findByCinemaAndSchedule(cinema, date, time);
		return movieScreenings;
	}
	
	@Transactional
	public void insertNewMovieScreening(MovieScreening movieScreening) {
		Cinema cinema = movieScreening.getCinema();
		LocalDate date = movieScreening.getScheduleDate();
		LocalTime time = movieScreening.getScheduleTime();
		if(hasOverlap(cinema, date, time)) {
			throw new ScreeningScheduleOverlapException("Cinema " + cinema.getId() + " is already reserved on " + date + " at " + time);
		}
		movieService.addMovie(movieScreening.getMovie());
		System.out.println("Successful persist movie: " + movieScreening.getMovie());
		this.entityManager.persist(movieScreening);
		System.out.println("Successful persist movie screening: " + movieScreening);
	}
	
	public boolean hasOverlap(Cinema cinema, LocalDate date, LocalTime time) {
		if(findByCinemaAndSchedule(cinema, date, time).size() > 0) {
			return true;
		} 
		return false;
	}
}
