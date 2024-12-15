package domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Booking implements Serializable {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int bookNumber;
	@ManyToOne
	private Ride ride;
	@ManyToOne(targetEntity=Traveler.class, fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)  
	private Traveler traveler;
	private int seats;

	public Booking(Ride ride, Traveler traveler, int seats) {
		this.ride = ride;
		this.traveler = traveler;
		this.seats = seats;
	}
	public Booking() {
		
	}

	public int getBookNumber() {
		return bookNumber;
	}

	public void setBookNumber(int bookNumber) {
		this.bookNumber = bookNumber;
	}

	public Ride getRide() {
		return ride;
	}

	public void setRide(Ride ride) {
		this.ride = ride;
	}

	public Traveler getTraveler() {
		return traveler;
	}

	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

}
