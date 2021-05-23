package movietickets.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="movie_screenings", uniqueConstraints={
	    @UniqueConstraint(columnNames = {"cinema_id", "schedule_date", "schedule_time"})})
public class MovieScreening {
	
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name = "movie_id")
	private Movie movie;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name = "cinema_id")
	private Cinema cinema;
	
	@NotNull
	@Column(name = "schedule_date")
	private LocalDate scheduleDate;
	
	@NotNull
	@Column(name = "schedule_time")
	private LocalTime scheduleTime;
	
	@OneToMany(orphanRemoval = true, targetEntity=ReservedSeat.class, mappedBy = "movieScreening", fetch=FetchType.EAGER)
	private Collection<ReservedSeat> reservedSeats;

	public MovieScreening(Movie movie, Cinema cinema, LocalDate scheduleDate, LocalTime scheduleTime) {
		this.movie = movie;
		this.cinema = cinema;
		this.scheduleDate = scheduleDate;
		this.scheduleTime = scheduleTime;
		this.reservedSeats = new HashSet<>();
	}
	
	public MovieScreening(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Movie getMovie() {
		return movie;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public LocalDate getScheduleDate() {
		return scheduleDate;
	}
	
	public LocalTime getScheduleTime() {
		return scheduleTime;
	}
	
//	public void reserveSeat(Seat seat) {
//		if (!seat.getCinema().equals(cinema)) {
//			throw new IllegalArgumentException("seat " + seat + "is not in cinema "+ cinema);
//		}
//		if (reservedSeats.contains(seat)) {
//			throw new SeatAlreadyReservedException("seat " + seat + "is already reserved");
//		}
//		else {
//			reservedSeats.add(seat);
//		}
//	}
	
	public void unreserveSeat(Seat seat) {
		if (!reservedSeats.contains(seat)) {
			throw new SeatNotYetReservedException("seat " + seat + "is not yet reserved");
		}
		else {
			reservedSeats.remove(seat);
		}
	}
	
	public Collection<ReservedSeat> getReservedSeats() {
		return reservedSeats;
	}

	protected MovieScreening() {
		// required by persistence layer
	}
	
//	@Override
//	public String toString() {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm a");
//		return this.movie + " in " + this.cinema + " at " + this.schedule.format(formatter);
//	}
	
	
	
}
