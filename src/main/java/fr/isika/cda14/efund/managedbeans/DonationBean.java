package fr.isika.cda14.efund.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import fr.isika.cda14.efund.entity.project.Project;
import fr.isika.cda14.efund.services.ProjectService;
import fr.isika.cda14.efund.viewmodel.DonationVM;

@ManagedBean
@ViewScoped
public class DonationBean {

	@Inject
	ProjectService projectService;

	private Project project;

	private DonationVM donationVM = new DonationVM();

	public void onLoad(String id) {
		this.project = projectService.findProject(Long.parseLong(id));
	}

	public String createDonation(String id) {
		projectService.createDonation(donationVM, Long.parseLong(id));
		return "pageProject.xhtml?id=" + id + "faces-redirect=true";
	}

	public DonationVM getDonationVM() {
		return donationVM;
	}

	public Project getProject() {
		return project;
	}
}
