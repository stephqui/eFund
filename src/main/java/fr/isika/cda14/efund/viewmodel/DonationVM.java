package fr.isika.cda14.efund.viewmodel;

import java.math.BigDecimal;
import java.util.Date;

import fr.isika.cda14.efund.entity.project.Project;

public class DonationVM {

	private BigDecimal amount;
	private String fullName;
	private String creditCardNumber;
	private Date expirationDate;
	private String cryptogram;

	Project project;

	public Project getProject() {
		return project;
	}

	public String getFullName() {
		return fullName;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public String getCryptogram() {
		return cryptogram;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setCryptogram(String cryptogram) {
		this.cryptogram = cryptogram;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
