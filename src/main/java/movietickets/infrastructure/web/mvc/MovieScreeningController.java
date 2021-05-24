package movietickets.infrastructure.web.mvc;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import movietickets.domain.Cinema;
import movietickets.domain.Movie;
import movietickets.domain.MovieScreening;
import movietickets.domain.MovieScreeningService;
import movietickets.domain.MovieService;
import movietickets.domain.Seat;
import movietickets.domain.SeatAlreadyReservedException;

@Controller
public class MovieScreeningController {

	@Autowired
	private MovieScreeningService movieScreeningService;

	@Autowired
	private MovieService movieService;

	@GetMapping(value = "/")
	public String showMovieScreenings(Model model) {
		List<Movie> movieList = movieScreeningService.findMovieByScheduledToday();
		model.addAttribute("movieList", movieList);
		return "index";
	}

	@GetMapping(value = "/screenings/{movieId}")
	public ModelAndView getMovieScreeningsOfMovie(@PathVariable Long movieId) {
		Movie movie = movieService.findById(movieId);
		List<MovieScreening> todayScreenings = movieScreeningService.findByMovieAndScheduledToday(movieId);
		List<MovieScreening> soonScreenings = movieScreeningService.findByMovieAndScheduledSoon(movieId);
		ModelAndView model = new ModelAndView("/screenings/movieScreening");
		model.addObject("today_screenings", todayScreenings);
		model.addObject("soon_screenings", soonScreenings);
		model.addObject(movie);
		return model;
	}

	@GetMapping(value = "/reserveSeat/{movieScreeningId}")
	public String showSeatLayout(@PathVariable Long movieScreeningId, Model model) {
		System.out.println("Movie Screening id: " + movieScreeningId);
		MovieScreening movieScreening = movieScreeningService.findByMovieScreeningId(movieScreeningId);
		Collection<Seat> reservedSeats = movieScreeningService.findReservedSeatsByMovieScreeningId(movieScreeningId);
		Collection<Seat> allSeats = movieScreeningService.findSeatsByMovieScreeningId(movieScreeningId);
		model.addAttribute("movieScreening", movieScreening);
		model.addAttribute("cinema", movieScreening.getCinema());
		model.addAttribute("reservedSeats", reservedSeats);
		model.addAttribute("allSeats", allSeats);
		return "/seat/seat";
	}

	@PostMapping(value = "/reserveSeat/{movieScreeningId}")
	public String reserveASeat(@RequestParam("seatNumbers") String[] seatNumbers, @PathVariable Long movieScreeningId, Model model) {

		try {
			MovieScreening movieScreening = movieScreeningService.findByMovieScreeningId(movieScreeningId);
			Collection<Seat> seats = parseSeatNumbers(seatNumbers, movieScreeningId);
			for(Seat seat: seats) {
				movieScreeningService.reserveSeatByMovieScreeningId(seat, movieScreeningId);
			}
			model.addAttribute("movieScreening", movieScreening);
			model.addAttribute("reservedSeats", seats);
			System.out.println("Movie Screening for confirmation: " + movieScreening.getId());
			System.out.println("Successful reservation of seats.");
			return "/booking/confirmation";
		} catch (SeatAlreadyReservedException e) {
			System.out.println(e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
			return "/seat/seat";
		}
		
	}
	
	private Collection<Seat> parseSeatNumbers(String[] seatNumbers, Long movieScreeningId) {
		HashSet<Seat> seats = new HashSet<>();
		for (String seatNumber : seatNumbers) {
			String regex = "((?<=[a-zA-Z])(?=[0-9]))|((?<=[0-9])(?=[a-zA-Z]))";
			String[] split = seatNumber.split(regex);
			seats.add(movieScreeningService.findSeatByRowAndColumnAndMovieScreeningId(split[0], Integer.parseInt(split[1]), movieScreeningId));
		}
		return seats;
	}

	/*
	@GetMapping(value="/confirmAndReserve/{movieScreeningId}")
	public String confirmAndReserveSeat(@RequestParam ("seatNumbers") String[] seatNumbers, @PathVariable Long movieScreeningId, Model model) {
		try {
			Collection<Seat> seats = parseSeatNumbers(seatNumbers, movieScreeningId);
			for(Seat seat: seats) {
				movieScreeningService.reserveSeatByMovieScreeningId(seat, movieScreeningId);
			}
			System.out.println("Successful reservation of seats.");
			return "booking/confirmation";
			
		} catch (SeatAlreadyReservedException e) {
			System.out.println(e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
			return showMovieScreenings(model);
		}
	}*/
	
}
