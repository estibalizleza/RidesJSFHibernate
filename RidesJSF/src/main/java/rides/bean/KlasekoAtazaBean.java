package rides.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import businessLogic.BLFacade;
import domain.Ride;

public class KlasekoAtazaBean {
	private BLFacade businessLogic;
	private List<String> cities;
	private String selectedCity;
	
	private List<Ride> bidaiak;
	
	public KlasekoAtazaBean() {
		businessLogic= FacadeBean.getBusinessLogic();
		this.cities = new ArrayList<>();
		cities = businessLogic.getDepartCities();
		bidaiak = new ArrayList<>();
	}
	
	public List<String> getCities() {
		return cities;
	}

	public void setCities(List<String> cities) {
		this.cities = cities;
	}

	public String getSelectedCity() {
		return selectedCity;
	}

	public void setSelectedCity(String selectedCity) {
		this.selectedCity = selectedCity;
	}

	public String aukeratu() {
		this.bidaiakLortu();
		return "Bidaiak";
	}
	
	
	public boolean bidaiakLortu() {
		if (selectedCity != null) {
			bidaiak = businessLogic.bidaiakLortu(selectedCity);
			if (bidaiak == null || bidaiak.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Sartutako hirirako ez da bidairik aurkitu", ""));
				return false;
			}
			return true;
		} else {
			bidaiak = null;
			return false;
		}
	}

	public List<Ride> getBidaiak() {
		return bidaiak;
	}

	public void setBidaiak(List<Ride> bidaiak) {
		this.bidaiak = bidaiak;
	}
}
