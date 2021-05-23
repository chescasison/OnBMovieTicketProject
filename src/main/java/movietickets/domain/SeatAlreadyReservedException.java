package movietickets.domain;

public class SeatAlreadyReservedException extends RuntimeException {

	public SeatAlreadyReservedException(String message) {
		super(message);
	}
	
}
