package rides.bean;

import java.util.List;
import businessLogic.BLFacade;
import domain.Booking;
import domain.Traveler;

public class BookingTableBean {
	private List<Booking> bookings;
	private BLFacade businessLogic;
	
	public BookingTableBean() {
		businessLogic = FacadeBean.getBusinessLogic();
		bookings = businessLogic.getBookingsFromTraveler((Traveler)businessLogic.getCurrentUser());
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
	
	public String close() {
		return "Main";
	}
	
	
}
