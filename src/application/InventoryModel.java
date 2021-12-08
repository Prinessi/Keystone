package application;

public class InventoryModel {
	String product, manufacturer, supplier, description;
	Integer id, inventory;
	Double price;
	
	// Getters and setters for Inventory Items.
	public InventoryModel(String product, Integer id, Double price, Integer inventory, String manufacturer, String supplier, String description) {
		this.product = product;
		this.id = id;
		this.price = price;
		this.inventory = inventory;
		this.manufacturer = manufacturer;
		this.supplier = supplier;
		this.description = description;
	}
	public Integer getId() {
		return id;
	}
	
	public void setID(Integer id) {
		this.id = id;
	}
	
	public String getProduct() {
		return product;
	}
	
	public void setProdcut(String product) {
		this.product = product;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Integer getInventory() {
		return inventory;
	}
	
	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
	
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public String getSupplier() {
		return supplier;
	}
	
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
