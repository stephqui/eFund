package fr.isika.cda14.efund.entity.account.paiement;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import fr.isika.cda14.efund.entity.shop.BasketOrder;
import fr.isika.cda14.efund.entity.space.UserSpace;

@Entity
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "credit_card_number")
	private String creditCardNumber;

	@Column(scale = 2)
	private BigDecimal amount;

	@Column(name = "expiration_date")
	private Date expirationDate;

	@Column(name = "payment_date")
	private Date paymentDate;

	@Column(name = "cryptogram")
	private String cryptogram;

	@OneToOne
	private BasketOrder basketOrder;

	@ManyToOne
	@JoinColumn(name = "user_space_id")
	private UserSpace userSpace;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setUserSpace(UserSpace userSpace) {
		this.userSpace = userSpace;
	}

	public Long getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getCryptogram() {
		return cryptogram;
	}

	public void setCryptogram(String cryptogram) {
		this.cryptogram = cryptogram;
	}

	public BasketOrder getBasketOrder() {
		return basketOrder;
	}

	public void setBasketOrder(BasketOrder basketOrder) {
		this.basketOrder = basketOrder;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
}
