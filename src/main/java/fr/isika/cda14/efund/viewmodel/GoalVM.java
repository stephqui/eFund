package fr.isika.cda14.efund.viewmodel;

import java.math.BigDecimal;

public class GoalVM {
	private BigDecimal target;
	private String description;

	public BigDecimal getTarget() {
		return target;
	}

	public void setTarget(BigDecimal target) {
		this.target = target;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
