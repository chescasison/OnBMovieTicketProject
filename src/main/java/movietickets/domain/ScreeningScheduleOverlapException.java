package movietickets.domain;

public class ScreeningScheduleOverlapException extends RuntimeException{
	public ScreeningScheduleOverlapException(String message) {
		super(message);
	}
}
