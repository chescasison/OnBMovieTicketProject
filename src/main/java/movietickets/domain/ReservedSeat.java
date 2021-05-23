package movietickets.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="reserved_seats")
public class ReservedSeat {
	
	@Id
	private Long id;
	
	@JoinColumn(name = "movie_screening_id")
	@ManyToOne
	private MovieScreening movieScreening;

	private String row;
	
	private int column;
	
	protected ReservedSeat() {
		
	}
	
	public ReservedSeat(MovieScreening movieScreening, String row, int column) {
		this.movieScreening = movieScreening;
		this.row = row;
		this.column = column;
	}
	
	public ReservedSeat(Long id, MovieScreening movieScreening, String row, int column) {
		this.id = id;
		this.movieScreening = movieScreening;
		this.row = row;
		this.column = column;
	}
	
	public Long getId() {
		return id;
	}
	
	public MovieScreening getMovieScreening() {
		return movieScreening;
	}

	public String getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((movieScreening == null) ? 0 : movieScreening.hashCode());
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
		ReservedSeat other = (ReservedSeat) obj;
		if (column != other.column)
			return false;
		if (row == null) {
			if (other.row != null)
				return false;
		} else if (!row.equals(other.row))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReservedSeat [movieScreening=" + movieScreening + ", row=" + row + ", column=" + column + "]";
	}
	
	
}
