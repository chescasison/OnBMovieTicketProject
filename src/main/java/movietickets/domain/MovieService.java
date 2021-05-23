package movietickets.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	public Movie findById(Long id) {
		Optional<Movie> movie = movieRepository.findById(id);
		return movie.get();
	}
}
