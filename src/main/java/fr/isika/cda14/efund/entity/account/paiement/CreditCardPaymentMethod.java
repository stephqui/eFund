package fr.isika.cda14.efund.entity.account.paiement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Table(name = "cc_payment_method")
public class CreditCardPaymentMethod extends PaymentMethod {

	@Column(name = "card_number", length = 16)
	private String cardNumber;

	@Column(name = "owner_name", length = 22)
	private String ownerName;

	@Column(name = "expiration_date", length = 5)
	private String expirationDate;
}
