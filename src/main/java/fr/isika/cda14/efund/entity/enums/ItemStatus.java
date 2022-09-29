package fr.isika.cda14.efund.entity.enums;

public enum ItemStatus {
	AVAILABLE("Disponible"), OUT_OF_STOCK("Indisponible");

	private String itemStatusLabel;

	private ItemStatus(String itemStatusLabel) {
		this.itemStatusLabel = itemStatusLabel;
	}

	public String getItemStatusLabel() {
		return itemStatusLabel;
	}
}
