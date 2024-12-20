package businessLogic;

import java.util.Date;
import java.util.List;

//import domain.Booking;
import domain.Ride;
import domain.Traveler;
import domain.User;
import domain.Booking;
import domain.Driver;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;
 
/**
 * Interface that specifies the business logic.
 */
public interface BLFacade  {
	  
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	public List<String> getDepartCities();
	
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getDestinationCities(String from);


	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driver to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
   public Ride createRide( String from, String to, Date date, int nPlaces, float price, String driverEmail) throws RideMustBeLaterThanTodayException, RideAlreadyExistException;
	
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date);
	
	/**
	 * This method registers a user
	 * @param email
	 * @param passwd
	 * @param mota
	 * @return true if correctly registered, false otherwise
	 */
	public boolean register(String email, String passwd, String mota);
	
	/**
	 * This method checks if a user already exists
	 * @param email
	 * @param pass
	 * @return
	 */
	public User getUser(String email);
	
	/**
     * This method sets the current user
     * @param user
     */
    void setCurrentUser(User user);

    /**
     * This method returns the current user
     * @return the current user
     */
    User getCurrentUser();
    
    /**
     * This method books a ride
     * @param email
     * @param ride
     * @param seats
     */
    void bookRide(String email, Ride ride, int seats);
    /**
     * This method returns all of the rides of a driver
     * @param driver
     * @return all of the rides
     */
    public List<Ride> getRidesFromDriver(Driver driver);
    
    /**
     * This method return all of the bookings of a traveler
     * @param traveler
     * @return all of the bookings
     */
    public List<Booking> getBookingsFromTraveler(Traveler traveler);
    
    List<Ride> bidaiakLortu(String departCity);
    
    
	
	

	
}
