package movietickets.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TicketTest {

	@Autowired
	private TicketService ticketService;
	
	@Test
	public void test_insert_ticket() {
		MovieScreening movieScreening = new MovieScreening(3L);
		Ticket ticket = new Ticket(movieScreening, "B", 3);
		
		ticketService.insertTicket(ticket);
		
		Ticket insertedTicket = ticketService.findById(13L);
		assertEquals(ticket, insertedTicket);
		
	}

}
