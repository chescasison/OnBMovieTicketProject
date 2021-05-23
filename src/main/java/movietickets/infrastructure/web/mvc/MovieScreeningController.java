package movietickets.infrastructure.web.mvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import movietickets.domain.Movie;
import movietickets.domain.MovieScreening;
import movietickets.domain.MovieScreeningService;
import movietickets.domain.MovieService;
import movietickets.domain.ReservedSeat;
import movietickets.domain.ReservedSeatService;
import movietickets.domain.SeatAlreadyReservedException;

@Controller
public class MovieScreeningController {

	@Autowired
	private MovieScreeningService movieScreeningService;

	@Autowired
	private MovieService movieService;

	@Autowired
	private ReservedSeatService reservedSeatService;

	/*
	 * @RequestMapping("/screenings") public List<MovieScreening>
	 * getAllMovieScreening() { return movieScreeningService.findAll(); }
	 */

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showMovieScreenings(Model model) {
		List<Movie> movieList = movieScreeningService.findMovieByScheduledToday();
		model.addAttribute("movieList", movieList);
		return "index";
	}

	@RequestMapping(value = "/screenings/{id}", method = RequestMethod.GET)
	public ModelAndView getMovieScreeningsOfMovie(@PathVariable Long id) {
		Movie movie = movieService.findById(id);
		List<MovieScreening> todayScreenings = movieScreeningService.findByMovieAndScheduledToday(id);
		List<MovieScreening> soonScreenings = movieScreeningService.findByMovieAndScheduledSoon(id);
		ModelAndView model = new ModelAndView("/screenings/movieScreening");
		model.addObject("today_screenings", todayScreenings);
		model.addObject("soon_screenings", soonScreenings);
		model.addObject(movie);
		return model;
	}

	@RequestMapping(value = "/reserveSeat/{id}", method = RequestMethod.GET)
	public String showSeatLayout(@PathVariable Long id, Model model) {
		System.out.println("Movie Screening id: " + id);
		Optional<MovieScreening> movieScreening = movieScreeningService.findById(id);
		List<ReservedSeat> reservedSeats = movieScreeningService.getReservedSeatsOfMovieScreening(id);
		model.addAttribute("movieScreening", movieScreening.get());
		model.addAttribute("reservedSeats", reservedSeats);
		model.addAttribute("movieScreeningId", id);

		return "/seat/seat";
	}

	@RequestMapping(value = "/reserveSeat/{id}", method = RequestMethod.POST)
	public String reserveASeat(@RequestParam("seat") String[] seatNumbers, @PathVariable Long id, Model model) {

//for debugging only
//		for(String seatnumber: seatNumbers) {
//			System.out.println("The newly reserved seat number: " + seatnumber + "------");
//		}
		try {
			//isSeatAvailable(seatNumbers);
			Optional<MovieScreening> movieScreening = movieScreeningService.findById(id);
			model.addAttribute("movieScreening", movieScreening.get());
			model.addAttribute("seatNumbers", Arrays.asList(seatNumbers));
			System.out.println("Movie Screening for confirmation: " + movieScreening.get().getId());
			return "/booking/confirmation";
			
		} catch (SeatAlreadyReservedException e) {
			System.out.println(e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
			return "/seat/seat";
		}
		
	}

	void isSeatAvailable(String[] seatNumbers) {
		for (String seatNumber : seatNumbers) {
			Character row = seatNumber.charAt(0);
			String column = seatNumber.substring(1);

			if (reservedSeatService.seatAlreadyReserved(row, Integer.parseInt(column))) {
				throw new SeatAlreadyReservedException("Seat " + row + column + " is already reserved.");
			}
		}

 	}
	
	@RequestMapping(value="/confirmAndReserve/{screeningId}")
	public String confirmAndReserveSeat(@RequestParam ("seatNumbers") String seatNumbers, @PathVariable Long screeningId, Model model) {
		try {
			String[] seatNums = seatNumbers.substring(1, seatNumbers.length() - 1).split(", ");
			isSeatAvailable(seatNums);
			Long id = 22L;
			for(String seat: seatNums) {
				MovieScreening movieScreening = new MovieScreening(screeningId);
				String row = String.valueOf(seat.charAt(0));
				int column = Integer.parseInt(seat.substring(1));
				ReservedSeat reservedSeat = new ReservedSeat(id++, movieScreening, row, column);
				reservedSeatService.insertReserveSeat(reservedSeat);
			}
			System.out.println("Successful reservation of seats.");
			
			return showMovieScreenings(model);
		} catch (SeatAlreadyReservedException e) {
			System.out.println(e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
			return "booking/confirmation";
		}
	}
}
