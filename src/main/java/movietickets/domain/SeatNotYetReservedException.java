package movietickets.domain;

public class SeatNotYetReservedException extends RuntimeException {

	SeatNotYetReservedException(String message) {
		super(message);
	}
	
}
