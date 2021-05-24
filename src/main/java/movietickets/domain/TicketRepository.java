package movietickets.domain;

import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
	
	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("FROM Ticket t WHERE t.movieScreening = ?1")
	List<Ticket> findByMovieScreening(MovieScreening movieScreening);
	
	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("FROM Ticket t WHERE t.movieScreening = ?1 AND t.row = ?2 AND t.column = ?3")
	List<Ticket> findByMovieScreeningAndRowAndColumn(MovieScreening movieScreening, String row, int column);
	
	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("FROM Ticket t WHERE t.ticketNumber = ?1")
	Optional<Ticket> findById(Long id);
	
}
