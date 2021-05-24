package movietickets.domain;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CinemaService {
	
	@Autowired
	private CinemaRepository cinemaRepository;
	
	CinemaService(){
		
	}
	
	public Cinema findById(Long id) {
		Optional<Cinema> cinema = cinemaRepository.findById(id);
		return cinema.get();
	}
}
