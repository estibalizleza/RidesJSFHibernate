package rides.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import domain.User;
import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Ride;

public class CreateRideBean implements Serializable {
	private String departCity;
	private String arrivalCity;
	private Date rideDate;
	private int numSeats;
	private float price;
	private BLFacade facadeBL;
	private List<Ride> rides;
	private static final long serialVersionUID = 1L;

	public CreateRideBean() {
		this.facadeBL = FacadeBean.getBusinessLogic();
		this.numSeats = 1;
		User currentUser = facadeBL.getCurrentUser();

	    // Check if the current user is a Driver
	    if (currentUser instanceof Driver) 
	        // Safe cast to Driver
	        this.rides = facadeBL.getRidesFromDriver((Driver) currentUser);
	}

	public List<Ride> getRides() {
		return rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}

	public void updateRidesTable() {
		rides = facadeBL.getRidesFromDriver((Driver) facadeBL.getCurrentUser());
	}
	public String getDepartCity() {
		return departCity;
	}

	public void setDepartCity(String departCity) {
		this.departCity = departCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public Date getRideDate() {
		return rideDate;
	}

	public void setRideDate(Date rideDate) {
		this.rideDate = rideDate;
	}

	public int getNumSeats() {
		return numSeats;
	}

	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public BLFacade getFacadeBL() {
		return facadeBL;
	}

	public void setFacadeBL(BLFacade facadeBL) {
		this.facadeBL = facadeBL;
	}

	public void createRide() {

		try {
			facadeBL.createRide(departCity, arrivalCity, rideDate, numSeats, price, facadeBL.getCurrentUser().getEmail());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Ride created successfully", null));
			this.updateRidesTable();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}

	}

	public void onDateSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("" + event.getObject()));
	}

	public String close() {
		return "Main";
	}

}
