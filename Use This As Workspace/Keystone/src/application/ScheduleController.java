package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.NumberStringConverter;

public class ScheduleController implements Initializable{
	// Import FXML Items
	@FXML
	private TableView <AddScheduleModel> tblNewSchedule;
	@FXML
	private TableColumn<AddScheduleModel, Number> colEmpId;
	@FXML
	private TableColumn<AddScheduleModel, ComboBox> colStart;
	@FXML
	private TableColumn<AddScheduleModel, ComboBox> colEnd;

	@FXML
	private TableView<EmployeeModel> tblEmployeeList;
	@FXML 
	private TableColumn<EmployeeModel, Number> colEmpIdSearch;
	@FXML 
	private TableColumn<EmployeeModel, String> colFirstNameSearch;
	@FXML 
	private TableColumn<EmployeeModel, String> colLastNameSearch;
	
	
	@FXML
	private DatePicker pickerShiftDate;
	@FXML
	private Button btnAddLine;
	@FXML
	private Button btnRemoveLine;
	@FXML
	private Button btnSubmit;
	
	ObservableList<EmployeeModel> slist = FXCollections.observableArrayList();
	ObservableList<AddScheduleModel> newlist;
	ObservableList dropDowns = FXCollections.observableArrayList(
			"1:00am", "2:00am", "3:00am", "4:00am", "5:00am", "6:00am", "7:00am", "8:00am", "9:00am", "10:00am", "11:00am", "11:00am", 
			"1:00pm", "2:00pm", "3:00pm", "4:00pm", "5:00pm", "6:00pm", "7:00pm", "8:00pm", "9:00pm", "10:00pm", "11:00pm", "12:00pm");
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Connection con = DBConnector.getConnection();
			
			// Result sets to hold SQL Queries
			ResultSet rsEmployee = con.createStatement().executeQuery("select * from keyemployee");
			
			// Schedule Table
			while (rsEmployee.next()) {
				slist.add(new EmployeeModel(rsEmployee.getInt("Empid"), rsEmployee.getString("FirstName"), rsEmployee.getString("LastName"), rsEmployee.getLong("phoneNumber")));
				
				colEmpIdSearch.setCellValueFactory(schedData -> new SimpleIntegerProperty(schedData.getValue().getEmpID()));
				colFirstNameSearch.setCellValueFactory(schedData -> new SimpleStringProperty(schedData.getValue().getfName()));
				colLastNameSearch.setCellValueFactory(schedData -> new SimpleStringProperty(schedData.getValue().getlName()));
				
				tblEmployeeList.setItems(slist);
			}
			
			newlist = FXCollections.observableArrayList(new AddScheduleModel(null, dropDowns));
			//colEmpId;colStart;colEnd;
			colEmpId.setCellValueFactory(new PropertyValueFactory<>("empID"));
			colStart.setCellValueFactory(new PropertyValueFactory<>("shiftStart"));
			colEnd.setCellValueFactory(new PropertyValueFactory<>("shiftEnd"));
			
			tblNewSchedule.setItems(newlist);;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		// Make table and columns editable and save on edit
		tblNewSchedule.setEditable(true);
		
		colStart.setEditable(true);
		colEnd.setEditable(true);
		
		colEmpId.setEditable(true);
		colEmpId.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
		colEmpId.setOnEditCommit(new EventHandler<CellEditEvent<AddScheduleModel, Number>>(){
			
			@Override
			public void handle(CellEditEvent<AddScheduleModel, Number> event ) {
				AddScheduleModel schedule = event.getRowValue();
				schedule.setEmpID(event.getNewValue().intValue());;
			}
		});
	}
	
	public void btnAddLineClick (ActionEvent event) {
		AddScheduleModel newline = new AddScheduleModel(null, dropDowns);
		tblNewSchedule.getItems().add(newline);
	}
	
	public void btnRemoveLineClick(ActionEvent event) {
		tblNewSchedule.getItems().removeAll(tblNewSchedule.getSelectionModel().getSelectedItem());
	}
	
	public void btnSubmitInvoiceClick(ActionEvent event) {
		AddScheduleModel schedule = new AddScheduleModel();
		String box1 = "", box2 = "";
		
		String day = pickerShiftDate.getValue().toString();
		
		
		List <List<String>> arrList = new ArrayList<>();
		for (int i = 0; i <tblNewSchedule.getItems().size(); i++) {
			schedule = tblNewSchedule.getItems().get(i);
			arrList.add(new ArrayList<>());
			arrList.get(i).add(schedule.getEmpID().toString());
			arrList.get(i).add(schedule.getShiftStart().toString());
			arrList.get(i).add(schedule.getShiftEnd().toString());
			arrList.get(i).add(day);
		}
		
		try {
			Connection con = DBConnector.getConnection();
			
			for (int i = 0; i < arrList.size(); i++) {
				PreparedStatement stmt = con.prepareStatement( "insert into keyschedule(EmpID, START_TIME, END_TIME, DAY) VALUES (?, ?, ?, ?)");
				for (int j = 0; j < arrList.get(i).size(); j++) {
					System.out.println(arrList.get(i).get(j));
				}
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
