package domain;

import java.io.Serializable;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import java.util.Set;

@SuppressWarnings("serial")
@Entity
public class Traveler extends User implements Serializable {
	
	@OneToMany(targetEntity = Booking.class, mappedBy = "traveler", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private Set<Booking> bookedRides;
	
	private double money;


	public Set<Booking> getBookedRides() {
		return bookedRides;
	}

	public void setBookedRides(Set<Booking> bookedRides) {
		this.bookedRides = bookedRides;
	}

	public Traveler(String email, String passwd) {
		super(email, passwd, "Traveler");
		bookedRides= new HashSet<>();
		this.money=50;
	}
	
	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public Traveler() {
		
	}

	public String toString() {
		return super.toString();
	}
	
	public void addBookedRide(Booking bookedRide) {
		bookedRides.add(bookedRide);
		bookedRide.setTraveler(this);
	}

}
