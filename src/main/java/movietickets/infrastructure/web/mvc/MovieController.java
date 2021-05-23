package movietickets.infrastructure.web.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import movietickets.domain.Movie;
import movietickets.domain.MovieService;

@Controller
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	/*
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String showMovieScreenings(Model model) {
		List<Movie> movieList = movieService.getAllMovies();
		model.addAttribute("movieList", movieList);
		return "index";
	}
	*/
	
}
