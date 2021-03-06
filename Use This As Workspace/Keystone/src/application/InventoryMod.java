package application;

public class InventoryMod {
	private String ITEM_ID, ITEM_DESC;
	public double ITEM_PRICE; 
	double INV_AMOUNT;
	
	public InventoryMod() {
		this.ITEM_ID = "";
		this.ITEM_DESC = "";
		this.ITEM_PRICE = 0;
		this.INV_AMOUNT = 0;
	}
	
	public InventoryMod(String ITEM_ID, String ITEM_DESC, double ITEM_PRICE, double INV_AMOUNT) {
		this.ITEM_ID = ITEM_ID;
		this.ITEM_DESC = ITEM_DESC;
		this.ITEM_PRICE = ITEM_PRICE;
		this.INV_AMOUNT = INV_AMOUNT;
	}
	
	public String getItem() {
		return ITEM_ID;
	}
	
	public void setItem(String ITEM_ID) {
		this.ITEM_ID = ITEM_ID;
	}
	
	public String getDesc() {
		return ITEM_DESC;
	}
	
	public void setDesc(String ITEM_DESC) {
		this.ITEM_DESC = ITEM_DESC;
	}
	
	public double getPrice() {
		return ITEM_PRICE;
	}
	
	public void setPrice(double ITEM_PRICE) {
		this.ITEM_PRICE = ITEM_PRICE;
	}
	
	public double getAmount() {
		return INV_AMOUNT;
	}
	
	public void setAmount(double INV_AMOUNT) {
		this.INV_AMOUNT = INV_AMOUNT;
	}
}
