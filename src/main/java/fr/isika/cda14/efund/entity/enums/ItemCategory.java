package fr.isika.cda14.efund.entity.enums;

public enum ItemCategory {
	ACCESSORIES("Accessoires"), CLOTHING("Vêtements"), FOOD_AND_DRINKS("Nourriture"), FOURNITURE("Equipement"),
	OTHER("Autre");

	private String label;

	private ItemCategory(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
