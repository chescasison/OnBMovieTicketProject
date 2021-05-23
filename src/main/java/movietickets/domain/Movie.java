package movietickets.domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="movies")
public class Movie {
	
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String summary;
	
	@NotEmpty
	private String directors;
	
	@NotEmpty
	private String actors;
	
	@Column(name = "running_time")
	private int runningTime;
	
	@Column(name="poster_link")
	private String posterLink;
	
	@Column(name="trailer_link")
	private String trailerLink;
	
	public Movie(Long id) {
		this.id = id;
	}
	
	public Movie(String title, String summary, String director, String actors,
			int runningTime, String posterLink) {
		this.title = title;
		this.summary = summary;
		this.directors = director;
		this.actors = actors;
		this.runningTime = runningTime;
		this.posterLink = posterLink;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getSummary() {
		return summary;
	}

	public String getDirectors() {
		return directors;
	}

	public String getActors() {
		return actors;
	}

	public Integer getRunningTime() {
		return runningTime;
	}
	
	public String getPosterLink() {
		return posterLink;
	}
	
	public String getTrailerLink() {
		return trailerLink;
	}

	protected Movie() {
		// required by persistence layer
	}
	
	@Override
	public String toString() {
		return title;
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
		Movie other = (Movie) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
		
}
