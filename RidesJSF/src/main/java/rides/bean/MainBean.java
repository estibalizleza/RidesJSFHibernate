package rides.bean;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Traveler;
import domain.User;

public class MainBean {

	private BLFacade businessLogic;

	public User getErabiltzailea() {
	    return businessLogic.getCurrentUser();
	}
	
	public MainBean() {
		businessLogic= FacadeBean.getBusinessLogic();
		if (getErabiltzailea()==null) {
			User erabiltzailea=businessLogic.getUser("has@gmail.com");
			businessLogic.setCurrentUser(erabiltzailea);
		}
		
	}
			
	public String goToQueryRides() {
		return "QueryRides";
	}

	public String goToCreateRide() {
		return "CreateRide";
	}
	
	public String goToLogin() {
		return "Login";
	}
	
	public String goToRegister() {
		return "Register";
	}
	
	public String goToBookRide() {
		return "BookRide";
	}
	
	public void logout() {
		User erabiltzailea=businessLogic.getUser("has@gmail.com");
		businessLogic.setCurrentUser(erabiltzailea);
	}
	
	
	public boolean isDriver() {
        return getErabiltzailea() instanceof Driver;
    }

    public boolean isTraveler() {
        return getErabiltzailea() instanceof Traveler;
    }
    
    public boolean isAdmin() {
    	return getErabiltzailea().getMota().equals("Admin");
    }
    public boolean isUser() {
    	return getErabiltzailea() instanceof Traveler || getErabiltzailea() instanceof Driver;
    }
}