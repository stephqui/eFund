package fr.isika.cda14.efund.viewmodel;

import java.math.BigDecimal;

import fr.isika.cda14.efund.entity.enums.ItemCategory;

public class ItemCreationForm {

	private String label;

	private Integer quantityStock;

	private BigDecimal price;

	private String description;

	private String imagePath;

	private ItemCategory itemCategory;

	public ItemCategory[] getCategories() {
		return ItemCategory.values();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getQuantityStock() {
		return quantityStock;
	}

	public void setQuantityStock(Integer quantityStock) {
		this.quantityStock = quantityStock;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ItemCategory getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(ItemCategory itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
