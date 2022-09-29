package fr.isika.cda14.efund.managedbeans;

import java.io.IOException;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import fr.isika.cda14.efund.entity.account.Account;
import fr.isika.cda14.efund.entity.enums.Role;
import fr.isika.cda14.efund.services.AccountService;
import fr.isika.cda14.efund.tool.SessionTool;

@ManagedBean
@ViewScoped
public class LoginBean {
	private static final String SERVER_HOME_URL = "http://127.0.0.1:8080/eFund/index.xhtml";

	@Inject
	private AccountService accountService;

	private String email;
	private String password;

	public void login() throws IOException {
		Optional<Account> optional = accountService.findByEmail(email);
		if (optional.isPresent()) {
			Account account = optional.get();
			if (account.getEmail().equals(email) && account.getPassword().equals(password)) {
				SessionTool.writeInSession(account);

			} else {
				UIComponent formulaire = FacesContext.getCurrentInstance().getViewRoot().findComponent("loginForm");
				FacesContext.getCurrentInstance().addMessage(formulaire.getClientId(),
						new FacesMessage("Identifiants incorrects"));
			}
		} else {
			UIComponent formulaire = FacesContext.getCurrentInstance().getViewRoot().findComponent("loginForm");
			FacesContext.getCurrentInstance().addMessage(formulaire.getClientId(),
					new FacesMessage("Utilisateur non reconnu"));
		}
	}

	public Boolean isUser() {
		String role = SessionTool.getRole();
		if (role != null && role.equals(Role.USER.toString())) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean isLogged() {
		if (SessionTool.getUserId() != null) {
			return true;
		} else {
			return false;
		}
	}

	public String getSessionUserName() {
		return SessionTool.getUserName();
	}

	public Long getSessionUserId() {
		return SessionTool.getUserId();
	}

	public String getSessionUserImagePath() {
		return SessionTool.getImagePath();
	}

	public String getSessionDashBoardURL() {
		String redirectURL = SessionTool.getDashBoardURL();
		if (redirectURL == null) {
			redirectURL = "index.xhtml?";
		}
		return redirectURL;
	}

	public void disconnectSession() {
		SessionTool.resetSessionAttributes();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(SERVER_HOME_URL);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
