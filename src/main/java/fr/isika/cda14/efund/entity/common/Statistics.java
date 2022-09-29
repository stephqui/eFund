package fr.isika.cda14.efund.entity.common;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Statistics {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "nb_organizations")
	private Integer nbOrganizations;

	@Column(name = "nb_users")
	private Integer nbUsers;

	@Column(name = "nb_projects")
	private Integer nbProjects;

	@Column(name = "nb_events")
	private Integer nbEvents;

	@Column(name = "nb_volunteers")
	private Integer nbVolunteers;

	@Column(name = "nb_donations")
	private Integer nbDonations;

	@Column(name = "total_donations")
	private Integer totalDonations;

	@Column(name = "sold_products")
	private Integer soldProducts;

	private LocalDate date;

	public Integer getNbOrganizations() {
		return nbOrganizations;
	}

	public void setNbOrganizations(Integer nbOrganizations) {
		this.nbOrganizations = nbOrganizations;
	}

	public Integer getNbUsers() {
		return nbUsers;
	}

	public void setNbUsers(Integer nbUsers) {
		this.nbUsers = nbUsers;
	}

	public Integer getNbProjects() {
		return nbProjects;
	}

	public void setNbProjects(Integer nbProjects) {
		this.nbProjects = nbProjects;
	}

	public Integer getNbEvents() {
		return nbEvents;
	}

	public void setNbEvents(Integer nbEvents) {
		this.nbEvents = nbEvents;
	}

	public Integer getNbVolunteers() {
		return nbVolunteers;
	}

	public void setNbVolunteers(Integer nbVolunteers) {
		this.nbVolunteers = nbVolunteers;
	}

	public Integer getNbDonations() {
		return nbDonations;
	}

	public void setNbDonations(Integer nbDonations) {
		this.nbDonations = nbDonations;
	}

	public Integer getTotalDonations() {
		return totalDonations;
	}

	public void setTotalDonations(Integer totalDonations) {
		this.totalDonations = totalDonations;
	}

	public Integer getSoldProducts() {
		return soldProducts;
	}

	public void setSoldProducts(Integer soldProducts) {
		this.soldProducts = soldProducts;
	}

	public Long getId() {
		return id;
	}
}
