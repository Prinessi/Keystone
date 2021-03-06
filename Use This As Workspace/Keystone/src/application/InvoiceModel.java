package application;

public class InvoiceModel {
	String InvCompany, InvDate, InvItemId, InvDesc;
	Integer InvNumber;
	Double InvQuantity, InvWeight, InvPrice, InvCost;
	
	public InvoiceModel(String InvCompany, String InvDate, String InvItemId, String InvDesc, Integer InvNumber, 
			Double InvQuantity, Double InvWeight, Double InvPrice, Double InvCost) {
		this.InvCompany = InvCompany;
		this.InvDate = InvDate;
		this.InvItemId = InvItemId;
		this.InvDesc = InvDesc;
		this.InvNumber = InvNumber;
		this.InvQuantity = InvQuantity;
		this.InvWeight = InvWeight;
		this.InvPrice = InvPrice;
		this.InvCost = InvCost;
	}
	
	public InvoiceModel() {
		
	}
	
	public String getInvCompany() {
		return InvCompany;
	}
	
	public void setInvCompany(String InvCompany) {
		this.InvCompany = InvCompany;
	}
	
	public String getInvDate() {
		return InvDate;
	}
	
	public void setInvDate(String InvDate) {
		this.InvDate = InvDate;
	}
	
	public String getInvItemId() {
		return InvItemId;
	}
	
	public void setInvItemId(String InvItemId) {
		this.InvItemId = InvItemId;
	}
	
	public String getInvDesc() {
		return InvDesc;
	}
	
	public void setInvDesc(String InvDesc) {
		this.InvDesc = InvDesc;
	}
	
	public Integer getInvNumber() {
		return InvNumber;
	}
	
	public void setInvNumber(Integer InvNumber) {
		this.InvNumber = InvNumber;
	}
	
	public Double getInvQuantity() {
		return InvQuantity;
	}
	
	public void setInvQuantity(Double InvQuantity) {
		this.InvQuantity = InvQuantity;
	}
	
	public Double getInvWeight() {
		return InvWeight;
	}
	
	public void setInvWeight(Double InvWeight) {
		this.InvWeight = InvWeight;
	}
	
	public Double getInvPrice() {
		return InvPrice;
	}
	
	public void setInvPrice(Double InvPrice) {
		this.InvPrice = InvPrice;
	}
	
	public Double getInvCost() {
		return InvCost;
	}
	
	public void setInvCost(Double InvCost) {
		this.InvCost = InvCost;
	}
}
