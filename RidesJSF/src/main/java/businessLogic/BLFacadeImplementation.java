package businessLogic;

import java.util.Date;
import java.util.List;

import dataAccess.HibernateDataAccess;
import domain.Ride;
import domain.User;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
public class BLFacadeImplementation implements BLFacade {
	HibernateDataAccess dbManager;
	private User currentUser;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");

		dbManager = new HibernateDataAccess();

		// dbManager.close();

	}

	public BLFacadeImplementation(HibernateDataAccess da) {
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		dbManager = da;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> getDepartCities() {

		List<String> departLocations = dbManager.getDepartCities();

		return departLocations;

	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> getDestinationCities(String from) {

		List<String> targetCities = dbManager.getArrivalCities(from);

		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail)
			throws RideMustBeLaterThanTodayException, RideAlreadyExistException {

		Ride ride = dbManager.createRide(from, to, date, nPlaces, price, driverEmail);
		return ride;
	};

	/**
	 * {@inheritDoc}
	 */
	public List<Ride> getRides(String from, String to, Date date) {
		List<Ride> rides = dbManager.getRides(from, to, date);
		return rides;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		List<Date> dates = dbManager.getThisMonthDatesWithRides(from, to, date);
		return dates;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean register(String email, String passwd, String mota) {
		return dbManager.register(email, passwd, mota);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public User getUser(String email) {
		return dbManager.getUser(email);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public User getCurrentUser() {
        return this.currentUser;
    }
    
    /**
	 * {@inheritDoc}
	 */
    public void bookRide(String email, Ride ride, int seats) {
    	dbManager.bookRide(email, ride, seats);
    }

}
