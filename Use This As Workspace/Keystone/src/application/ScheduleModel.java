package application;

public class ScheduleModel {
	String day, shiftStart, shiftEnd, fName, lName;
	Integer empID;
	
	// Getters and setters for Inventory Items.
	public ScheduleModel(Integer empID, String fName, String lName, String shiftStart, String shiftEnd ,String day) {
		this.empID = empID;
		this.fName = fName;
		this.lName = lName;
		this.shiftStart = shiftStart;
		this.shiftEnd = shiftEnd;
		this.day = day;
	}

	public ScheduleModel() {
		
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
	
	public String getShiftStart() {
		return shiftStart;
	}
	
	public void setShiftStart(String shiftStart) {
		this.shiftStart = shiftStart;
	}
	
	public String getShiftEnd() {
		return shiftEnd;
	}
	
	public void setShiftEnd(String shiftEnd) {
		this.shiftEnd = shiftEnd;
	}
	
	
	public String getDay() {
		return day;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
}