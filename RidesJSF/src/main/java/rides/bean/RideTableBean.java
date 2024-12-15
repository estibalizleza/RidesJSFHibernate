package rides.bean;

import java.util.List;
import businessLogic.BLFacade;
import domain.Driver;
import domain.Ride;

public class RideTableBean {
	private List<Ride> rides;
	private BLFacade businessLogic;

	public RideTableBean() {
		businessLogic = FacadeBean.getBusinessLogic();
		rides = businessLogic.getRidesFromDriver((Driver) businessLogic.getCurrentUser());
	}

	public List<Ride> getRides() {
		return rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}

	public String close() {
		return "Main";
	}

}
