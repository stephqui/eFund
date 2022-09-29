package fr.isika.cda14.efund.viewmodel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class PaymentShopVM implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7838217853200090951L;

	@NotNull(message = "Ne doit pas être null")
	private BigDecimal amount;

	@NotEmpty(message = "Ne doit pas être vide")
	@NotNull(message = "Ne doit pas être null")
	@Size(min = 1, max = 25, message = "Doit être entre 1 et 25 car.")
	@Pattern(regexp = "[^0-9]*", message = "Ne doit pas contenir des chiffres")
	private String name;

	@NotEmpty(message = "Ne doit pas être vide")
	@NotNull(message = "Ne doit pas être null")
	@Size(min = 15, max = 15, message = "Doit avoir 15 caractère.")
	private String cardNumber;

	@NotNull(message = "Ne doit pas être null")
	private Date expirationDate;

	@NotEmpty(message = "Ne doit pas être vide")
	@NotNull(message = "Ne doit pas être null")
	@Size(min = 3, max = 3, message = "Doit avoir 3 caractère.")
	private String SecurityCode;

	@NotEmpty(message = "Ne doit pas être vide")
	@NotNull(message = "Ne doit pas être null")
	@Size(min = 5, max = 5, message = "Doit avoir 5 caractère.")
	private String zipCode;

	public PaymentShopVM() {
		super();
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getSecurityCode() {
		return SecurityCode;
	}

	public void setSecurityCode(String securityCode) {
		SecurityCode = securityCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PaymentForm [amount=");
		builder.append(amount);
		builder.append(", name=");
		builder.append(name);
		builder.append(", cardNumber=");
		builder.append(cardNumber);
		builder.append(", expirationDate=");
		builder.append(expirationDate);
		builder.append(", SecurityCode=");
		builder.append(SecurityCode);
		builder.append(", zipCode=");
		builder.append(zipCode);
		builder.append("]");
		return builder.toString();
	}
}
