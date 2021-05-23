package movietickets.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="tickets")
public class Ticket {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ticker_number")
	private Long ticketNumber;
	
	@OneToOne
	@JoinColumn(name = "customer_id")
	@NotNull
	private Customer customer;
	
	@ManyToOne
	@NotNull
	private MovieScreening movieScreening;
	
	@OneToOne
	@NotNull
	private Seat seat;
	
	public Ticket(Customer customer, MovieScreening movieScreening, Seat seat) {
		this.customer = customer;
		this.movieScreening = movieScreening;
		this.seat = seat;
	}

	public Long getTicketNumber() {
		return ticketNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public MovieScreening getMovieScreening() {
		return movieScreening;
	}

	public Seat getSeat() {
		return seat;
	}
	
	protected Ticket() {
		// required by persistence layer
	}
	
	@Override
	public String toString() {
		return ticketNumber.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ticketNumber == null) ? 0 : ticketNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (ticketNumber == null) {
			if (other.ticketNumber != null)
				return false;
		} else if (!ticketNumber.equals(other.ticketNumber))
			return false;
		return true;
	}
	
}
