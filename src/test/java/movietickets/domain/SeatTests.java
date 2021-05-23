package movietickets.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SeatTests {

	@Test
	void create_valid_seat() {
		Cinema cinema = new Cinema(1L, "b", 3);
		assertNotNull(new Seat("a", 3, cinema));
	}
	
	@Test
	void create_invalid_seat() {
		Cinema cinema = new Cinema(1L, "b", 3);
		assertThrows( IllegalArgumentException.class,
						() -> new Seat("bb", 3, cinema) );
	}

}
