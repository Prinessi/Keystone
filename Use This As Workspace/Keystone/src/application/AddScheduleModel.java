package application;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class AddScheduleModel {
	Integer empID;
	ComboBox shiftStart, shiftEnd;
	String startShift, endShift;
	
	// Getters and setters for Inventory Items.
	public AddScheduleModel(Integer empID, ObservableList data) {
		this.empID = empID;
		this.shiftStart = new ComboBox(data);// Created with no elements
		this.shiftEnd = new ComboBox(data);
	}
	
	public AddScheduleModel() {
		
	}
	
	public Integer getEmpID() {
		return empID;
	}
	
	public void setEmpID(Integer empID) {
		this.empID = empID;
	}
	
	public ComboBox getShiftStart() {
		return shiftStart;
	}
	
	public void setShiftStart(ComboBox shiftStart) {
		this.shiftStart = shiftStart;
	}
	
	public ComboBox getShiftEnd() {
		return shiftEnd;
	}
	
	public void setShiftEnd(ComboBox shiftEnd) {
		this.shiftEnd = shiftEnd;
	}
	
	public void setStartShift(String startShift) {
		this.startShift = startShift;
	}
}
