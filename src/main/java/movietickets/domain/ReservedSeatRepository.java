package movietickets.domain;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ReservedSeatRepository extends CrudRepository<ReservedSeat, Long>{
	
	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("FROM ReservedSeat r WHERE r.movieScreening = ?1")
	List<ReservedSeat> findByMovieScreening(MovieScreening movieScreening);
	
	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("FROM ReservedSeat r WHERE r.row = ?1 AND r.column = ?2")
	List<ReservedSeat> findByRowAndColumn(String row, int column);
}
