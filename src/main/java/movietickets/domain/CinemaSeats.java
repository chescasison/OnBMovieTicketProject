package movietickets.domain;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class CinemaSeats {
	
	@Id
	private Long id;
	
	@JoinColumn(name = "cinema_id")
	@ManyToOne
	private Cinema cinema;
	
	private String row;
	private int column;
	
	protected CinemaSeats() {
		
	}

	public Long getId() {
		return id;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public String getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public String toString() {
		return "CinemaSeats [cinema=" + cinema + ", row=" + row + ", column=" + column + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cinema == null) ? 0 : cinema.hashCode());
		result = prime * result + column;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((row == null) ? 0 : row.hashCode());
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
		CinemaSeats other = (CinemaSeats) obj;
		if (cinema == null) {
			if (other.cinema != null)
				return false;
		} else if (!cinema.equals(other.cinema))
			return false;
		if (column != other.column)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (row == null) {
			if (other.row != null)
				return false;
		} else if (!row.equals(other.row))
			return false;
		return true;
	}
	
}
