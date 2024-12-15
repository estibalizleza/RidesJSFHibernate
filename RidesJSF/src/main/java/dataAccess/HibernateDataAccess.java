package dataAccess;

import eredua.HibernateUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import configuration.UtilDate;
import domain.Booking;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import org.hibernate.Session;
import org.hibernate.Query;

/**
 * It implements the data access to the objectDb database
 */
public class HibernateDataAccess {

	private static boolean isInitialized = false;

	public HibernateDataAccess() {
		if (!isInitialized) {
			init();
			isInitialized = true;
		}
	}

	public boolean existsDriver(String email, String pass) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Driver driver = (Driver) session.get(Driver.class, email);
			return driver != null;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}

	public User getUser(String email) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			return (User) session.get(User.class, email);
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from        the origin location of a ride
	 * @param to          the destination location of a ride
	 * @param date        the date of the ride
	 * @param nPlaces     available seats
	 * @param driverEmail to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today
	 * @throws RideAlreadyExistException         if the same ride already exists for
	 *                                           the driver
	 */
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail)
			throws RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(">> DataAccess: createRide=> from= " + from + " to= " + to + " driver=" + driverEmail
				+ " date " + date);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Driver driver = (Driver) session.get(Driver.class, driverEmail);
			if (driver.doesRideExists(from, to, date)) {
				session.getTransaction().commit();
				throw new RideAlreadyExistException(
						ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			Ride ride = driver.addRide(from, to, date, nPlaces, price);
			session.persist(driver);
			session.getTransaction().commit();

			return ride;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * This method returns all the arrival destinations, from all rides that depart
	 * from a given city
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String from) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			String hql = "SELECT DISTINCT r.toCity FROM Ride r WHERE r.fromCity = :from ORDER BY r.toCity";
			Query query = session.createQuery(hql);
			query.setParameter("from", from);
			List<String> arrivingCities = (List<String>) query.list();
			session.getTransaction().commit();
			return arrivingCities;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method returns all the cities where rides depart
	 * 
	 * @return collection of cities
	 */
	public List<String> getDepartCities() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Query query = session.createQuery("SELECT DISTINCT r.fromCity FROM Ride r ORDER BY r.fromCity");
			List<String> cities = query.list();
			session.getTransaction().commit();
			return cities;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return new ArrayList<>();
		}

	}

	/**
	 * This method retrieves the rides from two locations on a given date
	 * 
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date the date of the ride
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getRides=> from= " + from + " to= " + to + " date " + date);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try{
			List<Ride> res = new ArrayList<>();
			Query query = session
					.createQuery("SELECT r FROM Ride r WHERE r.fromCity = :from AND r.toCity = :to AND r.date = :date");
			query.setParameter("from", from);
			query.setParameter("to", to);
			query.setParameter("date", date);
			List<Ride> rides = query.list();
			for (Ride ride : rides) {
				res.add(ride);
			}
			session.getTransaction().commit();
			return res;
		} catch (Exception e) {
            session.getTransaction().rollback(); 
            e.printStackTrace();
            return null;
        }
		
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date of the month for which days with rides want to be retrieved
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Date> res = new ArrayList<>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		Query query = session.createQuery(
				"SELECT DISTINCT r.date FROM Ride r WHERE r.fromCity = :from AND r.toCity = :to AND r.date BETWEEN :startDate AND :endDate");

		query.setParameter("from", from);
		query.setParameter("to", to);
		query.setParameter("startDate", firstDayMonthDate);
		query.setParameter("endDate", lastDayMonthDate);
		List<Date> dates = query.list();
		for (Date d : dates) {
			res.add(d);
		}
		session.getTransaction().commit();
		return res;
	}

	public void init() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 1;
				year += 1;
			}

			// Create drivers
			Driver driver1 = (Driver) session.get(Driver.class, "driver1@gmail.com");
			if (driver1 == null) {
				driver1 = new Driver("driver1@gmail.com", "Aitor Fernandez");

				driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 4, 7);
				driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year, month, 6), 4, 8);
				driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 25), 4, 4);

				session.persist(driver1);
			}

			Driver driver2 = (Driver) session.get(Driver.class, "driver2@gmail.com");
			if (driver2 == null) {
				driver2 = new Driver("driver2@gmail.com", "Ane Gaztañaga");

				driver2.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 3, 3);
				driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 25), 2, 5);
				driver2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year, month, 6), 2, 5);

				session.persist(driver2);
			}

			Driver driver3 = (Driver) session.get(Driver.class, "driver3@gmail.com");
			if (driver3 == null) {
				driver3 = new Driver("driver3@gmail.com", "Test driver");
				driver3.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 14), 1, 3);
				session.persist(driver3);
			}
			// No hace falta persistir los rides porque tenemos cascadeType.PERSIST
			// Hibernate automáticamente persistirá también los objetos Ride que están
			// asociados a ese Driver

			Traveler trav1 = (Traveler) session.get(Traveler.class, "traveler1@gmail.com");
			if (trav1 == null) {
				trav1 = new Traveler("traveler1@gmail.com", "Test traveler");
				session.persist(trav1);
			}
			User admin = (User) session.get(User.class, "has@gmail.com");
			if (admin == null) {
				admin = new User("has@gmail.com", "12345", "Admin");
				session.persist(admin);
			}

			session.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}

	}

	public boolean register(String email, String password, String mota) {
		System.out.println(">> DataAccess: register");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			if (mota.equals("Driver")) {
				Driver existingDriver = (Driver) session.get(Driver.class, email);
				if (existingDriver == null) {
					Driver d = new Driver(email, password);
					session.persist(d);
				} else
					return false;
			} else if (mota.equals("Traveler")) {
				Traveler existingTraveler = (Traveler) session.get(Traveler.class, email);
				if (existingTraveler == null) {
					Traveler t = new Traveler(email, password);
					session.persist(t);
				} else
					return false;
			}
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
	}

	/**
	 * This method books a ride for a traveler
	 * 
	 * @param email email of the traveler
	 * @param ride  ride to book
	 * @param seats number of seats to book
	 * @param desk  discount to apply
	 * @return true if the booking is successful, false otherwise
	 */
	public void bookRide(String email, Ride ride, int seats) {
		System.out.println(">> DataAccess: book Ride");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Traveler traveler = (Traveler) getUser(email);
			double ridePrice = ride.getPrice() * seats;
			Booking booking = new Booking(ride, traveler, seats);
			booking.setTraveler(traveler);
			// session.persist(booking); Ez da beharrezkoa booking lista CascadeType.PERSIST
			// delako
			ride.setnPlaces(ride.getnPlaces() - seats);
			traveler.addBookedRide(booking);
			traveler.setMoney(traveler.getMoney() - ridePrice);
			session.persist(traveler);
			session.merge(ride); // only to update entity
			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}

	}
	
	/**
	 * This method return all of the rides of a driver
	 * @param driver
	 * @return 
	 */
	public List<Ride> getRidesFromDriver(Driver driver) {
		System.out.println(">> DataAccess: bidaiak lortu");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try{
			Query query = session
					.createQuery("SELECT r FROM Ride r WHERE r.driver = :driver");
			query.setParameter("driver", driver);
			List<Ride> rides = query.list();
			session.getTransaction().commit();
			return rides;
		} catch (Exception e) {
            session.getTransaction().rollback(); 
            e.printStackTrace();
            return null;
        }
		
	}
	
	/**
	 * This method return all of the rides of a driver
	 * @param driver
	 * @return 
	 */
	public List<Booking> getBookingsFromTraveler(Traveler traveler) {
		System.out.println(">> DataAccess: erreserbak lortu");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try{
			Query query = session
					.createQuery("SELECT b FROM Booking b WHERE b.traveler = :traveler");
			query.setParameter("traveler", traveler);
			List<Booking> bookings = query.list();
			session.getTransaction().commit();
			return bookings;
		} catch (Exception e) {
            session.getTransaction().rollback(); 
            e.printStackTrace();
            return null;
        }
		
	}
	
	
	public List<Ride> bidaiakLortu(String from) {
		System.out.println(">> DataAccess: bidaiak lortu");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try{
			List<Ride> res = new ArrayList<>();
			Query query = session
					.createQuery("SELECT r FROM Ride r WHERE r.fromCity = :from");
			query.setParameter("from", from);
			List<Ride> rides = query.list();
			for (Ride ride : rides) {
				res.add(ride);
			}
			session.getTransaction().commit();
			return res;
		} catch (Exception e) {
            session.getTransaction().rollback(); 
            e.printStackTrace();
            return null;
        }
		
	}

}
