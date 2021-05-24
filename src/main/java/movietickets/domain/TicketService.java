package movietickets.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	List<Ticket> getTicketsInAMovieScreening(MovieScreening movieScreening){
		List<Ticket> tickets = ticketRepository.findByMovieScreening(movieScreening);
		return tickets;
	}
	
	public Ticket findById(Long id) {
		return ticketRepository.findById(id).get();
	}
	
	@Transactional
	public void insertTicket(Ticket ticket) {
		this.entityManager.persist(ticket);
		System.out.println("Successfully inserted ticket: " + ticket);
	}
	
}
