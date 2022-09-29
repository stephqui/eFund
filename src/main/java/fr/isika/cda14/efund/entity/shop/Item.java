package fr.isika.cda14.efund.entity.shop;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import fr.isika.cda14.efund.entity.enums.ItemCategory;
import fr.isika.cda14.efund.entity.enums.ItemStatus;

@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String label;

	@Column(name = "image_path")
	private String imagePath = "/img/item/default.jpg";

	@Column(scale = 2)
	private BigDecimal price;

	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "item_status")
	private ItemStatus itemStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "item_category")
	private ItemCategory itemCategory;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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

	public ItemStatus getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(ItemStatus itemStatus) {
		this.itemStatus = itemStatus;
	}

	public ItemCategory getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(ItemCategory itemCategory) {
		this.itemCategory = itemCategory;
	}

	public Long getId() {
		return id;
	}

}
