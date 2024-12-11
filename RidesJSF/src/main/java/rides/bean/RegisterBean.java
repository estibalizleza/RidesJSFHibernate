package rides.bean;

import java.util.ArrayList;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import businessLogic.BLFacade;

public class RegisterBean {
	private String mota;
	private List<String> motak = new ArrayList<String>();
	private String email;
	private String pass;
	private String pass2;
	private BLFacade businessLogic;

	public RegisterBean() {
		motak.add("Driver");
		motak.add("Traveler");
		businessLogic = FacadeBean.getBusinessLogic();
	}

	public String getMota() {
		return mota;
	}

	public void setMota(String mota) {
		this.mota = mota;
	}

	public List<String> getMotak() {
		return motak;
	}

	public void setMotak(List<String> motak) {
		this.motak = motak;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPass2() {
		return pass2;
	}

	public void setPass2(String pass2) {
		this.pass2 = pass2;
	}

	public void register() {
		if (businessLogic.getUser(email)!=null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errorea: Erabiltzaiela existitzen da.", null));
		} else {
			if (!pass.equals(pass2)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Errorea: pasahitza desberdinak sartu dituzu.", null));
			} else {
				try {
					businessLogic.register(email, pass, mota);
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Ongi erregistratu zara", null));
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
				}
			}
		}
	}

	public String close() {
		return "Main";
	}

}
