package fr.isika.cda14.efund.managedbeans;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import fr.isika.cda14.efund.entity.account.OrganizationAccount;
import fr.isika.cda14.efund.entity.project.Project;
import fr.isika.cda14.efund.services.ProjectService;

@ManagedBean
@ViewScoped
public class ProjectsListBean {

	@Inject
	private ProjectService projectService;

	private Long remainingDays;

	private String searchRequest;

	private List<Project> projectsList;

	public void onLoad(String name) {
		if (name.isEmpty()) {
			this.projectsList = getAllProjects();
		} else {
			this.projectsList = searchResult(name);
		}
	}

	private List<Project> getAllProjects() {
		return this.projectService.findAllPublished();
	}

	public List<Project> getProjectList() {
		return projectsList;
	}

	public int percentage(BigDecimal currentCollect, BigDecimal target) {
		return (currentCollect.intValue() * 100) / target.intValue();
	}

	public Long calculRemainingDays(Date date) {
		Date endDate = new Date(date.getTime());
		ZonedDateTime endDateTime = ZonedDateTime.ofInstant(endDate.toInstant(), ZoneId.of("UTC"));

		return ChronoUnit.DAYS.between(ZonedDateTime.now(), endDateTime);
	}

	public String search() {

		String returnUrl = "projectsList.xhtml?faces-redirect=true&amp;searchId=" + this.searchRequest;
		return returnUrl;
	}

	public List<Project> searchResult(String searchProject) {
		return projectService.searchProjectFromPage(searchProject);
	}

	public Long getRemainingDays() {
		return remainingDays;
	}

	public String getSearchRequest() {
		return searchRequest;
	}

	public void setSearchRequest(String searchRequest) {
		this.searchRequest = searchRequest;
	}
}