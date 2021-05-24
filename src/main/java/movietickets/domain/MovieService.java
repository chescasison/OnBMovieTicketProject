package movietickets.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	public Movie findById(Long movieId) {
		return movieRepository.findById(movieId).get();
	}
	
}
