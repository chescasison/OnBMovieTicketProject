package movietickets.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservedSeatService {
	
	@Autowired
	private ReservedSeatRepository reservedSeatRepository;
	
	public ReservedSeatService() {
		
	}
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<ReservedSeat> findByMovieScreening(MovieScreening movieScreening){
		List<ReservedSeat> reservedSeats = reservedSeatRepository.findByMovieScreening(movieScreening);
		return reservedSeats;
	}
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Transactional
	public void insertReserveSeat(ReservedSeat reservedSeat) {
		
		if (seatAlreadyReserved(reservedSeat.getRow().charAt(0), reservedSeat.getColumn(), reservedSeat.getMovieScreening())) {
			throw new SeatAlreadyReservedException("Seat " + reservedSeat.getRow() + reservedSeat.getColumn() + " is already reserved.");
		}
		
		this.entityManager.persist(reservedSeat);
		System.out.println(reservedSeat);
	}
	
	public boolean seatAlreadyReserved(Character row, int column, MovieScreening movieScreening) {
		List<ReservedSeat> seat = reservedSeatRepository.findByRowAndColumn(String.valueOf(row), column, movieScreening);
		
		return seat.size() != 0;
	}
	
	
	
}
