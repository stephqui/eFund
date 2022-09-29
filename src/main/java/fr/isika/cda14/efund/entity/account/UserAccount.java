package fr.isika.cda14.efund.entity.account;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import fr.isika.cda14.efund.entity.account.paiement.PaymentMethod;
import fr.isika.cda14.efund.entity.enums.Role;
import fr.isika.cda14.efund.entity.space.UserSpace;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Table(name = "user_account")
public class UserAccount extends Account {

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_info_id")
	private UserInfo userInfo;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_space_id")
	private UserSpace userSpace;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_account_id")
	private List<PaymentMethod> paymentMethods;

	public UserAccount() {
		super.role = Role.USER;
		super.imagePath = "/img/user/default.jpg";
		this.userInfo = new UserInfo();
		this.userSpace = new UserSpace();
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public List<PaymentMethod> getPaymentMethods() {
		return paymentMethods;
	}

	public UserSpace getUserSpace() {
		return userSpace;
	}
}
