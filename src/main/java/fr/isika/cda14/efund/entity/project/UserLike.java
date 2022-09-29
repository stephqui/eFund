package fr.isika.cda14.efund.entity.project;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.isika.cda14.efund.entity.space.UserSpace;

@Entity
@Table(name = "user_like")
public class UserLike {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "generic_project_id")
	private GenericProject genericProject;

	@ManyToOne
	@JoinColumn(name = "user_space_id")
	private UserSpace userSpace;

	public GenericProject getGenericProject() {
		return genericProject;
	}

	public void setGenericProject(GenericProject genericProject) {
		this.genericProject = genericProject;
	}

	public UserSpace getUserSpace() {
		return userSpace;
	}

	public void setUserSpace(UserSpace userSpace) {
		this.userSpace = userSpace;
	}

	public Long getId() {
		return id;
	}
}
