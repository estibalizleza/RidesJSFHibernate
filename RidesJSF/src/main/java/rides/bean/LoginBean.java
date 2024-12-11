package rides.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import businessLogic.BLFacade;
import domain.User;

public class LoginBean {
	private String email;
	private String pasahitza;
	private BLFacade businessLogic;
	
	public LoginBean() {
		businessLogic = FacadeBean.getBusinessLogic();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasahitza() {
		return pasahitza;
	}
	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}
	
	public void login() {
		User erabiltzailea= businessLogic.getUser(email);
		if (erabiltzailea==null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erabiltzailea ez da existitzen. Erregistratu zaitez saioa hasi baino lehen.", null));
		}else {
			//comprobar si la contraseña coincide
			if(erabiltzailea.getPasswd().equals(pasahitza)) {
				businessLogic.setCurrentUser(erabiltzailea);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Saioa hasi duzu!!", null));
			}else
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pasahitza ez da egokia.", null));
		}
		
	}
	
	public String close() {
		return "Main";
	}
	

}
