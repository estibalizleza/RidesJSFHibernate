package rides.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class QueryRidesBean {
	private List<String> departCities;
	private List<String> arrivalCities;
	private Date data;
	private List<Ride> filteredRides;
	private String selectedDepartCity;
	private String selectedArrivalCity;
	private BLFacade businessLogic;

	public QueryRidesBean() {
		businessLogic = FacadeBean.getBusinessLogic();
		this.departCities = new ArrayList<>();
		this.arrivalCities = new ArrayList<>();
		departCities = businessLogic.getDepartCities();
		System.out.println(departCities);
		filteredRides = new ArrayList<>();

	}

	public List<Ride> getFilteredRides() {
		return filteredRides;
	}

	public void setFilteredRides(List<Ride> filteredRides) {
		this.filteredRides = filteredRides;
	}

	public String getSelectedDepartCity() {
		return selectedDepartCity;
	}

	public void setSelectedDepartCity(String selectedDepartCity) {
		this.selectedDepartCity = selectedDepartCity;
		updateArrivalCities();
	}

	public void updateArrivalCities() {
		if (selectedDepartCity != null && !selectedDepartCity.isEmpty()) {
			arrivalCities = businessLogic.getDestinationCities(selectedDepartCity);
		} else {
			arrivalCities = null;
		}
	}

	public String getSelectedArrivalCity() {
		return selectedArrivalCity;
	}

	public void setSelectedArrivalCity(String selectedArrivalCity) {
		System.out.println(selectedArrivalCity + "set select metodoan");
		this.selectedArrivalCity = selectedArrivalCity;
	}

	public List<String> getDepartCities() {
		return departCities;
	}

	public void setDepartCities(List<String> departCities) {
		this.departCities = departCities;
	}

	public List<String> getArrivalCities() {
		return arrivalCities;
	}

	public void setArrivalCities(List<String> arrivalCities) {
		this.arrivalCities = arrivalCities;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void onDateSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("" + event.getObject()));
	}

	public boolean updateRides() {
		if (selectedDepartCity != null && selectedArrivalCity != null && data != null) {
			filteredRides = businessLogic.getRides(selectedDepartCity, selectedArrivalCity, data);
			if (filteredRides == null || filteredRides.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Sartutako parametroetarako ez da bidairik aurkitu", ""));
				return false;
			}
			return true;
		} else {
			filteredRides = null;
			return false;
		}
	}
	
	public void sartuArrivalCities() {
		System.out.println(this.selectedArrivalCity + "listener");
	}
	
	public String close() {
		return "Main";
	}
	
}
