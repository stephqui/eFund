package fr.isika.cda14.efund.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;

import fr.isika.cda14.efund.entity.account.OrganizationAccount;
import fr.isika.cda14.efund.entity.account.UserAccount;
import fr.isika.cda14.efund.entity.enums.AccountStatus;
import fr.isika.cda14.efund.entity.enums.ProjectStatus;
import fr.isika.cda14.efund.entity.project.Event;
import fr.isika.cda14.efund.entity.project.Project;
import fr.isika.cda14.efund.services.AccountService;
import fr.isika.cda14.efund.services.EventService;
import fr.isika.cda14.efund.services.ProjectService;
import fr.isika.cda14.efund.tool.EmailTool;
import fr.isika.cda14.efund.viewmodel.MailForm;

@ManagedBean
@ViewScoped
public class AdminDashboardBean {

	@Inject
	AccountService accountService;

	@Inject
	ProjectService projectService;

	@Inject
	EventService eventService;

	private List<UserAccount> users = new ArrayList<UserAccount>();
	private List<OrganizationAccount> orgs = new ArrayList<OrganizationAccount>();
	private List<Project> projects = new ArrayList<Project>();
	private List<Event> events = new ArrayList<Event>();

	private UserAccount selectedUser;
	private OrganizationAccount selectedOrg;

	private MailForm mailForm = new MailForm();

	public void onLoad() {
		this.users = accountService.getAllUsers();
		this.orgs = accountService.getAllOrgs();
		this.projects = projectService.findAll();
		this.events = eventService.getAllEvents();
	}

	public void onUserCellEdit(CellEditEvent<AccountStatus> event) {
		AccountStatus oldValue = (AccountStatus) event.getOldValue();
		AccountStatus newValue = (AccountStatus) event.getNewValue();

		UserAccount user = (UserAccount) event.getComponent().getAttributes().get("userAttr");
		user.setAccountStatus(newValue);

		this.accountService.updateUser(user);

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cellule modifiée",
					"Avant : " + oldValue + ", Après : " + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void onOrgCellEdit(CellEditEvent<AccountStatus> event) {
		AccountStatus oldValue = (AccountStatus) event.getOldValue();
		AccountStatus newValue = (AccountStatus) event.getNewValue();

		OrganizationAccount org = (OrganizationAccount) event.getComponent().getAttributes().get("orgAttr");
		org.setAccountStatus(newValue);

		accountService.updateOrg(org);

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cellule modifiée",
					"Avant : " + oldValue + ", Après : " + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void onProjCellEdit(CellEditEvent<ProjectStatus> event) {
		ProjectStatus oldValue = (ProjectStatus) event.getOldValue();
		ProjectStatus newValue = (ProjectStatus) event.getNewValue();

		Project proj = (Project) event.getComponent().getAttributes().get("projAttr");
		proj.setProjectStatus(newValue);

		this.projectService.update(proj);

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cellule modifiée",
					"Avant : " + oldValue + ", Après : " + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void onEventCellEdit(CellEditEvent<ProjectStatus> event) {
		ProjectStatus oldValue = (ProjectStatus) event.getOldValue();
		ProjectStatus newValue = (ProjectStatus) event.getNewValue();

		Event myEvent = (Event) event.getComponent().getAttributes().get("eventAttr");
		myEvent.setProjectStatus(newValue);

		this.eventService.update(myEvent);

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cellule modifiée",
					"Avant : " + oldValue + ", Après : " + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void deleteUser() {
		accountService.deleteUser(this.selectedUser.getId());
		this.users.remove(this.selectedUser);
		this.selectedUser = null;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Compte utilisateur supprimé"));
		PrimeFaces.current().ajax().update("@form:messagesUser", "@form:dt-users");
	}

	public void sendingMail() {
		EmailTool.sendMail(mailForm.getToMail(), mailForm.getSubject(), mailForm.getMessage());
		this.selectedUser = null;
	}

	/* Getting Enum Values to populate Selected List */
	public AccountStatus[] getAccountStatus() {
		return AccountStatus.values();
	}

	public ProjectStatus[] getProjectStatus() {
		return ProjectStatus.values();
	}

	public UserAccount getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserAccount selectedUser) {
		this.selectedUser = selectedUser;
		this.mailForm.setToMail(selectedUser.getEmail());
	}

	public OrganizationAccount getSelectedOrg() {
		return selectedOrg;
	}

	public void setSelectedOrg(OrganizationAccount selectedOrg) {
		this.selectedOrg = selectedOrg;
		this.mailForm.setToMail(selectedOrg.getEmail());
	}

	public MailForm getMailForm() {
		return mailForm;
	}

	public void setMailForm(MailForm mailForm) {
		this.mailForm = mailForm;
	}

	public List<UserAccount> getUsers() {
		return users;
	}

	public List<OrganizationAccount> getOrgs() {
		return orgs;
	}

	public List<Event> getEvents() {
		return events;
	}

	public List<Project> getProjects() {
		return projects;
	}
}
