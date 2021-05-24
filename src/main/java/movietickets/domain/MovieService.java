package movietickets.domain;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Movie findById(Long id) {
		Optional<Movie> movie = movieRepository.findById(id);
		return movie.get();
	}
	
	public Movie findLastInserted() {
		return movieRepository.findLastInserted().get();
	}
	
	@Transactional
	public void addMovie(Movie movie) {
		this.entityManager.persist(movie);
	}
}
