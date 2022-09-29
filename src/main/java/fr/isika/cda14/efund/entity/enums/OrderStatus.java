package fr.isika.cda14.efund.entity.enums;

public enum OrderStatus {
	PROCESSING("Processing"), READY("Ready"), DELIVERED("Delivred");

	private String orderStatusLabel;

	private OrderStatus(String orderStatusLabel) {
		this.orderStatusLabel = orderStatusLabel;
	}

	public String getOrderStatusLabel() {
		return orderStatusLabel;
	}

	public void setOrderStatusLabel(String orderStatusLabel) {
		this.orderStatusLabel = orderStatusLabel;
	}
}
