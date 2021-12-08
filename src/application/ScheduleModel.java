package application;

public class ScheduleModel {
	String empID, day, shiftStart, shiftEnd, comments, fName, lName;
	Integer shiftID;
	Double wage, shiftCost;
	
	// Getters and setters for Inventory Items.
	public ScheduleModel(String empID, String day, String shiftStart, String shiftEnd, 
			String comments, Integer shiftID, Double wage, Double shiftCost) {
		this.empID = empID;
		this.day = day;
		this.shiftStart = shiftStart;
		this.shiftEnd = shiftEnd;
		this.comments = comments;
		this.shiftID = shiftID;
		this.wage = wage;
		this.shiftCost = shiftCost;
	}
	
	public String getEmpID() {
		return empID;
	}
	
	public void setEmpID(String empID) {
		this.empID = empID;
	}
	
	public String getDay() {
		return day;
	}
	
	public void setDay(String day) {
		this.day = day;
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
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public Integer getShiftID() {
		return shiftID;
	}
	
	public void setShiftID(Integer shiftID) {
		this.shiftID = shiftID;
	}
	
	public Double getWage() {
		return wage;
	}
	
	public void setWage(Double wage) {
		this.wage = wage;
	}
	
	public Double getShiftCost() {
		return shiftCost;
	}
	
	public void setShiftCost(Double shiftCost) {
		this.shiftCost = shiftCost;
	}
}
