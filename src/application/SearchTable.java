package application;

public class SearchTable {
	String fName,lName,address;
	Integer empID, areaCode, phoneNumber;
	Boolean manager;
	
	//Getters and setters for employees
	public SearchTable(Integer empID, String fName, String lName) {
		this.empID = empID;
		this.fName = fName;
		this.lName = lName;
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
}
