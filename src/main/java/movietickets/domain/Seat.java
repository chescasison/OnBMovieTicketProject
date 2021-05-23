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
	private Long id;
	
	@NotEmpty
	private String row;
	
	private int column;
	
	public Seat(String row, int column, Cinema cinema) {
		row = upperCase(row);
		if (!cinema.isValid(row, column)) {
			throw new IllegalArgumentException("seat is outside limits of cinema" + "(max row: "
											+ cinema.getMaxRow() + ", max column: " + cinema.getMaxColumn()
											+ "), was: " + row + column);
		}
		this.row = row;
		this.column = column;
	}
	
	public String getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	protected Seat() {
		// required by persistence layer
	}
	
	@Override
	public String toString() {
		return this.row + this.column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
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
		Seat other = (Seat) obj;
		if (column != other.column)
			return false;
		if (row == null) {
			if (other.row != null)
				return false;
		} else if (!row.equals(other.row))
			return false;
		return true;
	}
	
}
