package movietickets.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
class ReservedSeatTest {
	
	@Autowired
	private ReservedSeatRepository reservedSeatRepository;
	
	@Autowired
	private ReservedSeatService service;
	
	private class InsertReservedSeatAction implements Runnable{
		
		private final ReservedSeat reservedSeat;
		InsertReservedSeatAction(ReservedSeat reservedSeat){
			this.reservedSeat = reservedSeat;
		}
		
		@Override
		public void run() {
			service.insertReserveSeat(this.reservedSeat);
		}
	}
	
	@Test
	public void insert_same_reserved_seat_twice_then_entity_exists_exception_is_thrown() {
		ReservedSeat r1 = new ReservedSeat(100L, new MovieScreening(1L), "A", 10);
		ReservedSeat r2 = new ReservedSeat(100L, new MovieScreening(1L), "A", 10);
		assertThatExceptionOfType(SeatAlreadyReservedException.class).isThrownBy(() -> {
			service.insertReserveSeat(r1);
			service.insertReserveSeat(r2);
		});
	}
	
	@Test
	@Disabled
	public void check() {
		List<ReservedSeat> seats = service.findByMovieScreening(new MovieScreening(3L));
		seats.stream()
			.forEach(System.out::print);
	}
	
	@Test
	public void simultaneous_inserting_of_reserved_seats() throws Exception {
		
		Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler() {
		    @Override
		    public void uncaughtException(Thread th, Throwable ex) {
		        System.out.println("Uncaught exception: " + ex);
		        
		        assertTrue(ex instanceof SeatAlreadyReservedException);
		    }
		};
		
		ReservedSeat r1 = new ReservedSeat(999L, new MovieScreening(1L), "A", 10);
		ReservedSeat r2 = new ReservedSeat(999L, new MovieScreening(1L), "A", 10);
		Thread t1 = new Thread(new InsertReservedSeatAction(r1));
		Thread t2 = new Thread(new InsertReservedSeatAction(r2));
		t1.setUncaughtExceptionHandler(h);
		t2.setUncaughtExceptionHandler(h);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}
}
