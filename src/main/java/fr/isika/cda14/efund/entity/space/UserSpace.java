package fr.isika.cda14.efund.entity.space;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import fr.isika.cda14.efund.entity.project.Comment;
import fr.isika.cda14.efund.entity.project.Donation;
import fr.isika.cda14.efund.entity.project.EventRegistration;
import fr.isika.cda14.efund.entity.project.Favorite;
import fr.isika.cda14.efund.entity.project.UserLike;
import fr.isika.cda14.efund.entity.shop.BasketOrder;

@Entity
@Table(name = "user_space")
public class UserSpace implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2442454499805984702L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "userSpace")
	private List<BasketOrder> basketOrders;

	@OneToMany
	@JoinColumn(name = "user_space_id")
	private List<Comment> comments;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "userSpace")
	private List<Favorite> favorites;

	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "user_space_id")
	private List<UserLike> userLikes;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "userSpace")
	private List<EventRegistration> eventRegistrations;

	@OneToMany(mappedBy = "userSpace")
	private List<Donation> donations;

	public List<BasketOrder> getBasketOrders() {
		return basketOrders;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public List<Favorite> getFavorites() {
		return favorites;
	}

	public List<UserLike> getUserLikes() {
		return userLikes;
	}

	public List<EventRegistration> getEventRegistrations() {
		return eventRegistrations;
	}

	public List<Donation> getDonations() {
		return donations;
	}

	public Long getId() {
		return id;
	}
}
