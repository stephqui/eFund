package fr.isika.cda14.efund.entity.project;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import fr.isika.cda14.efund.entity.space.OrganizationSpace;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Event extends GenericProject {

	@Column(name = "event_start_date")
	@Temporal(TemporalType.DATE)
	private Date eventStartDate;

	private Integer duration;

	@Column(name = "volunteer_target")
	private Integer volunteerTarget;

	@Column(name = "volunteer_current")
	private Integer volunteerCurrent;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "event")
	private List<EventRegistration> eventRegistrations;

	@ManyToOne
	@JoinColumn(name = "organization_space_id")
	private OrganizationSpace organizationSpace;

	public void setEventStartDate(Date eventStartDate) {
		this.eventStartDate = eventStartDate;
	}

	public Date getEventStartDate() {
		return eventStartDate;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
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

	public Integer getVolunteerCurrent() {
		return volunteerCurrent;
	}

	public void setVolunteerCurrent(Integer volunteerCurrent) {
		this.volunteerCurrent = volunteerCurrent;
	}

	public OrganizationSpace getOrganizationSpace() {
		return organizationSpace;
	}

	public void setOrganizationSpace(OrganizationSpace organizationSpace) {
		this.organizationSpace = organizationSpace;
	}
}
