package movietickets.domain;

import static org.apache.commons.lang3.StringUtils.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Entity
@Table(name="seats")
public class Seat {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private int row;
	
	private int column;
	
	@ManyToOne
	@JoinColumn(name = "cinema_id")
	private Cinema cinema;
	
	public Seat(int row, int column, Cinema cinema) {
		if (!cinema.isValid(row, column)) {
			throw new IllegalArgumentException("seat is outside limits of cinema" + "(max row: "
											+ cinema.getMaxRow() + ", max column: " + cinema.getMaxColumn()
											+ "), was: " + str(row) + column);
		}
		this.row = row;
		this.column = column;
		this.cinema = cinema;
	}
	
	private static String str(int i) {
	    return i < 0 ? "" : str((i / 26) - 1) + (char)(64 + i % 26);
	}
	
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public Cinema getCinema() {
		return cinema;
	}
	
	protected Seat() {
		// required by persistence layer
	}
	
	@Override
	public String toString() {
		return upperCase(str(this.row)) + this.column;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cinema == null) ? 0 : cinema.hashCode());
		result = prime * result + column;
		result = prime * result + row;
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
		Seat other = (Seat) obj;
		if (cinema == null) {
			if (other.cinema != null)
				return false;
		} else if (!cinema.equals(other.cinema))
			return false;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
}
