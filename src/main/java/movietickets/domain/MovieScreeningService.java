package movietickets.domain;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieScreeningService {
	
	@Autowired
	private MovieScreeningRepository movieScreeningRepository;
	
	@Autowired
	private ReservedSeatService reservedSeatService;
	
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
		List<ReservedSeat> reservedSeats = reservedSeatService.findByMovieScreening(new MovieScreening(3L));
		return reservedSeats;
	}
	
}
