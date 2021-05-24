package movietickets.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="tickets")
public class Ticket {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ticket_number")
	private Long ticketNumber;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name = "movie_screening_id")
	private MovieScreening movieScreening;
	
	private String row;
	private int column;
	
	public Ticket(MovieScreening movieScreening, String row, int column) {
		this.movieScreening = movieScreening;
		this.row = row;
		this.column = column;
	}

	public Long getTicketNumber() {
		return ticketNumber;
	}

	public MovieScreening getMovieScreening() {
		return movieScreening;
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
		if (row == null) {
			if (other.row != null)
				return false;
		} else if(!row.equals(other.row))
			return false;
		if (column != other.column)
			return false;
		return true;
	}
	
}
