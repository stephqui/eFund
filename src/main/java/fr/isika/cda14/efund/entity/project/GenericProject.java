package fr.isika.cda14.efund.entity.project;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import fr.isika.cda14.efund.entity.common.ContentBlock;
import fr.isika.cda14.efund.entity.enums.ProjectCategory;
import fr.isika.cda14.efund.entity.enums.ProjectRange;
import fr.isika.cda14.efund.entity.enums.ProjectStatus;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class GenericProject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	protected String name;

	protected String summary;

	@Column(length = 1000)
	protected String description;

	@Column(name = "image_path")
	protected String imagePath;

	protected String location;

	@Column(name = "creation_date")
	@Temporal(TemporalType.DATE)
	protected Date creationDate;

	@Column(name = "end_date")
	@Temporal(TemporalType.DATE)
	protected Date endDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "project_category")
	protected ProjectCategory projectCategory;

	@Enumerated(EnumType.STRING)
	@Column(name = "project_range")
	protected ProjectRange projectRange;

	@Enumerated(EnumType.STRING)
	@Column(name = "project_status")
	protected ProjectStatus projectStatus;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "generic_project_id")
	protected List<ContentBlock> contentBlocks;

	@OneToMany
	@JoinColumn(name = "generic_project_id")
	protected List<Comment> comments;

	@OneToMany(mappedBy = "genericProject")
	protected List<UserLike> likes;

	@OneToMany(mappedBy = "genericProject")
	protected List<Favorite> favorites;
	
	@OneToMany(cascade = CascadeType.ALL)
	protected List<FaqElement> faq;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public ProjectStatus getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(ProjectStatus projectStatus) {
		this.projectStatus = projectStatus;
	}

	public List<UserLike> getLikes() {
		return likes;
	}

	public void setLikes(List<UserLike> likes) {
		this.likes = likes;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<ContentBlock> getContentBlocks() {
		return contentBlocks;
	}

	public void setContentBlocks(List<ContentBlock> contentBlocks) {
		this.contentBlocks = contentBlocks;
	}

	public List<Favorite> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<Favorite> favorites) {
		this.favorites = favorites;
	}

	public List<FaqElement> getFaq() {
		return faq;
	}

	public void setFaq(List<FaqElement> faq) {
		this.faq = faq;
	}

	public Long getId() {
		return id;
	}
}
