package fr.isika.cda14.efund.entity.account.paiement;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PaymentMethod {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
}
