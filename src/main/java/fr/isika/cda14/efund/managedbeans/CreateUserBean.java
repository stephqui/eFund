package fr.isika.cda14.efund.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import fr.isika.cda14.efund.entity.account.UserAccount;
import fr.isika.cda14.efund.exception.UserAlreadyExistsException;
import fr.isika.cda14.efund.services.AccountService;
import fr.isika.cda14.efund.tool.FileUpload;
import fr.isika.cda14.efund.tool.SessionTool;
import fr.isika.cda14.efund.viewmodel.CreateUserViewModel;

/* Classe mal nommée qui représente les formulaires de création et de modif Utilisateur */

@ManagedBean
@ViewScoped
public class CreateUserBean {

	@Inject
	private AccountService accountService;

	private CreateUserViewModel createUser = new CreateUserViewModel();

	UserAccount account;

	// Ne sert que pour la page "userModificationForm"
	public void onLoad() {
		this.account = accountService.findUserAccountById(SessionTool.getUserId());
	}

	/* Créé un nouveau User avec les infos minimum (username, password, email */
	public String create() {

		try {

			this.account = accountService.createUser(createUser);
			SessionTool.writeInSession(account);

			return "userCreationForm2?id=" + account.getId() + "faces-redirect=true";

		} catch (UserAlreadyExistsException ex) {
			ex.printStackTrace();
		}

		return "userCreationForm.xhtml";
	}

	/* upload de fichier et insertion du chemin dans le ViewModel */
	public void upload(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		String filePath = "/user/" + file.getFileName();

		FileUpload.doUpload(file, filePath);
		createUser.setImagePath("/img" + filePath);
	}

	/* Modification du user simple créé avec create() */
	/* Rajout les informations de la seconde page de formulaire */
	public String modify(Long id) {
		accountService.updateUser(id, createUser);
		if (createUser.getImagePath() != null) {
			SessionTool.updateSessionImage(createUser.getImagePath());
		}
		return "index.xhtml?faces-redirect=true";
	}

	/* Formulaire de modification de toutes les informations d'un compte User */
	public String updateUserModification() {
		if (createUser.getImagePath() != null) {
			account.setImagePath(createUser.getImagePath());
			SessionTool.updateSessionImage(createUser.getImagePath());
		}
		accountService.updateUser(account);
		return SessionTool.getDashBoardURL() + "&amp;faces-redirect=true";
	}

	/* Getters / Setters */
	public CreateUserViewModel getCreateUser() {
		return createUser;
	}

	public void setCreateUser(CreateUserViewModel createUser) {
		this.createUser = createUser;
	}

	public UserAccount getAccount() {
		return account;
	}
}
