package fr.isika.cda14.efund.entity.account;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import fr.isika.cda14.efund.entity.common.Address;

@Entity
@Table(name = "user_info")
public class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6948872234158978511L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 50, name = "first_name")
	private String firstName;

	@Column(length = 50, name = "last_name")
	private String lastName;

	@Column(length = 20)
	private String phone;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_address_id")
	private Address userAddress;

	public UserInfo() {
		this.userAddress = new Address();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Address getUserAddress() {
		return userAddress;
	}

	public Long getId() {
		return id;
	}
}
