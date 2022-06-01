package application;

public class EmployeeModel {
	String day, shiftStart, shiftEnd, fName, lName;
	Integer empID;
	Long phoneNumber;
	
	// Getters and setters for Inventory Items.
	public EmployeeModel(Integer empID, String fName, String lName, Long phoneNumber) {
		this.empID = empID;
		this.fName = fName;
		this.lName = lName;
		this.phoneNumber = phoneNumber;
	}

	public EmployeeModel() {
		
	}
	
	public Integer getEmpID() {
		return empID;
	}
	
	public void setEmpID(Integer empID) {
		this.empID = empID;
	}
	
	public void setfName(String fName) {
		this.fName = fName;
	}
	
	public String getfName() {
		return fName;
	}
	
	public void setlName(String lName) {
		this.lName = lName;
	}
	
	public String getlName() {
		return lName;
	}
	
	public void setPhone(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public Long getPhone() {
		return phoneNumber;
	}
}
