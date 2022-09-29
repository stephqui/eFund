package fr.isika.cda14.efund.viewmodel;

import java.util.Date;

import fr.isika.cda14.efund.entity.enums.ProjectCategory;
import fr.isika.cda14.efund.entity.enums.ProjectRange;

public class EventCreationFormVM {

	private String name;
	private Date endDate;
	private String summary;
	private String imagePath;
	private String location;
	private Integer volunteerTarget;
	private Date eventStartDate;
	private Integer duration;

	private ProjectCategory projectCategory;
	private ProjectRange projectRange;

	public ProjectCategory[] getProjectCategories() {
		return ProjectCategory.values();
	}

	public ProjectRange[] getProjectRanges() {
		return ProjectRange.values();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getVolunteerTarget() {
		return volunteerTarget;
	}

	public void setVolunteerTarget(Integer volunteerTarget) {
		this.volunteerTarget = volunteerTarget;
	}

	public Date getEventStartDate() {
		return eventStartDate;
	}

	public void setEventStartDate(Date eventStartDate) {
		this.eventStartDate = eventStartDate;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public ProjectCategory getProjectCategory() {
		return projectCategory;
	}

	public void setProjectCategory(ProjectCategory projectCategory) {
		this.projectCategory = projectCategory;
	}

	public ProjectRange getProjectRange() {
		return projectRange;
	}

	public void setProjectRange(ProjectRange projectRange) {
		this.projectRange = projectRange;
	}
}
