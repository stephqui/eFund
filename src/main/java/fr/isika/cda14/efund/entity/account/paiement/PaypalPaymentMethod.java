package fr.isika.cda14.efund.entity.account.paiement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Table(name = "paypal_payment_method")
public class PaypalPaymentMethod extends PaymentMethod {

	@Column(length = 320)
	private String email;
}
