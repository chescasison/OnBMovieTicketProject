package movietickets.domain;

import javax.persistence.*;
import static org.apache.commons.lang3.StringUtils.*;

@Entity
@Table(name="cinemas")
public class Cinema {
	
	@Id 
	private Long id;
	
	@Column(name = "max_row")
	private String maxRow;
	@Column(name = "max_column")
	private int maxColumn;
	
	public Cinema(Long id, String maxRow, int maxColumn) {
		this.id = id;
		isAlpha(maxRow); isNotBlank(maxRow);
		this.maxRow = upperCase(maxRow);
		if (maxColumn <= 0) {
			throw new IllegalArgumentException("max column must be positive, was: " + maxColumn);
		}
		this.maxColumn = maxColumn;
	}
	
	public Cinema(Long id) {
		this.id = id;
		this.maxRow = "S";
		this.maxColumn = 20;
	}
	
	public Long getId() {
		return id;
	}

	public String getMaxRow() {
		return maxRow;
	}
	
	public int getMaxColumn() {
		return maxColumn;
	}
	
	public boolean isValid (String row, int column) {
		return (row.length() < maxRow.length() || row.compareTo(maxRow) <= 0) 
				&& (column > 0 && column <= maxColumn);
	}
	
	protected Cinema() {
		// required by persistence layer
	}
	
	@Override 
	public String toString() {
		return id.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Cinema other = (Cinema) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
		
}
