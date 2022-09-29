package fr.isika.cda14.efund.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import fr.isika.cda14.efund.entity.account.OrganizationAccount;
import fr.isika.cda14.efund.services.AccountService;
import fr.isika.cda14.efund.tool.FileUpload;
import fr.isika.cda14.efund.tool.SessionTool;

@ViewScoped
@ManagedBean
public class OrgModificationProfileBean {

	@Inject
	private AccountService accService;

	private OrganizationAccount organizationAccount;

	private String imagePath;

	public void onLoad() {
		this.organizationAccount = accService.findOrganizationAccount(SessionTool.getUserId());
	}

	public void upload(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		String filePath = "/organization/" + file.getFileName();

		FileUpload.doUpload(file, filePath);
		this.imagePath = "/img" + filePath;
	}

	public String updateOrgModification() {
		if (this.imagePath != null) {
			organizationAccount.setImagePath(imagePath);
			SessionTool.updateSessionImage(imagePath);
		}
		accService.updateOrg(organizationAccount);
		return SessionTool.getDashBoardURL() + "&amp;faces-redirect=true";
	}

	public OrganizationAccount getOrganizationAccount() {
		return organizationAccount;
	}
}
