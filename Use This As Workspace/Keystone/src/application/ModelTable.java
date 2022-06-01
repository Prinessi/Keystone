package application;

public class ModelTable {
	String fName,lName,address;
	Integer empID, areaCode, phoneNumber, wage;
	Boolean manager;
	
	//Getters and setters for employees
	public ModelTable(Integer empID, String fName, String lName, Integer areaCode, Integer phoneNumber, String address, Boolean manager, Integer wage) {
		this.empID = empID;
		this.fName = fName;
		this.lName = lName;
		this.areaCode = areaCode;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.manager = manager;
		this.wage = wage;
	}
	
	public Integer getEmpID() {
		return empID;
	}
	
	public void setEmpID(Integer empID) {
		this.empID = empID;
	}
	
	public String getFName() {
		return fName;
	}
	
	public void setFName(String fName) {
		this.fName = fName;
	}
	
	public String getLName() {
		return lName;
	}
	
	public void setLName(String lName) {
		this.lName = lName;
	}
	
	public Integer getAreaCode() {
		return areaCode;
	}
	
	public void setAreaCode(Integer areaCode) {
		this.areaCode = areaCode;
	}
	
	public Integer getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getManager() {
		return manager;
	}
	
	public void setManager(Boolean manager) {
		this.manager = manager;
	}
	
	public Integer getWage() {
		return wage;
	}
	
	public void setWage(Integer wage) {
		this.wage = wage;
	}
}
