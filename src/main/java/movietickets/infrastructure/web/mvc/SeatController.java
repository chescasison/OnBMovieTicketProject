package movietickets.infrastructure.web.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import movietickets.domain.SeatService;

@Controller
public class SeatController {
	@Autowired
	private SeatService seatService;
	
	
	// TODO Controllers here
}
