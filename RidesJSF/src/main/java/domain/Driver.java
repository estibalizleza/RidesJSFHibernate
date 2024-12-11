package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
public class Driver extends User implements Serializable {
	@OneToMany(targetEntity = Ride.class, mappedBy = "driver", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<Ride> rides = new ArrayList<Ride>();

	public Driver(String email, String passwd) {
		super(email, passwd, "Driver");
	}
	
	public Driver() {
		
	}

	public String toString() {
		return super.toString();
	}

	/**
	 * This method creates a bet with a question, minimum bet amount and percentual
	 * profit
	 * 
	 * @param question   to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Ride addRide(String from, String to, Date date, int nPlaces, float price) {
		Ride ride = new Ride(from, to, date, nPlaces, price, this);
		rides.add(ride);
		return ride;
	}

	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location
	 * @param to   the destination location
	 * @param date the date of the ride
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesRideExists(String from, String to, Date date) {
		for (Ride r : rides)
			if ((java.util.Objects.equals(r.getFromCity(), from)) && (java.util.Objects.equals(r.getToCity(), to))
					&& (java.util.Objects.equals(r.getDate(), date)))
				return true;

		return false;
	}

	public List<Ride> getRides() {
		return rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}

	public Ride removeRide(String from, String to, Date date) {
		boolean found = false;
		int index = 0;
		Ride r = null;
		while (!found && index <= rides.size()) {
			r = rides.get(++index);
			if ((java.util.Objects.equals(r.getFromCity(), from)) && (java.util.Objects.equals(r.getToCity(), to))
					&& (java.util.Objects.equals(r.getDate(), date)))
				found = true;
		}

		if (found) {
			rides.remove(index);
			return r;
		} else
			return null;
	}

}
