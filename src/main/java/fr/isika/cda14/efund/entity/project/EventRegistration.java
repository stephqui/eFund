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
@Table(name = "event_registration")
public class EventRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Event event;

	@ManyToOne
	@JoinColumn(name = "user_space_id")
	private UserSpace userSpace;

	public Event getEvent() {
		return event;
	}

	public UserSpace getUserSpace() {
		return userSpace;
	}

	public void setUserSpace(UserSpace userSpace) {
		this.userSpace = userSpace;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
}
