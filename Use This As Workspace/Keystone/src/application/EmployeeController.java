package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class EmployeeController implements Initializable{
		// Buttons
		@FXML
		private Button btnAddLine;
		@FXML
		private Button btnSubmit;
		@FXML
		private Button btnRemoveLine;
		
		@FXML
		private TableView<EmployeeModel> tblEmployee;
		@FXML
		private TableColumn<EmployeeModel, String> colEmployeeFirst;
		@FXML
		private TableColumn<EmployeeModel, String> colEmployeeLast;
		@FXML
		private TableColumn<EmployeeModel, Number> colPhoneNumber;
		
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			tblEmployee.setEditable(true);
			
			colEmployeeFirst.setEditable(true);
			colEmployeeFirst.setCellFactory(TextFieldTableCell.forTableColumn());
			colEmployeeFirst.setOnEditCommit(new EventHandler<CellEditEvent<EmployeeModel, String>>(){
				
				@Override
				public void handle(CellEditEvent<EmployeeModel, String> event ) {
					EmployeeModel employee = event.getRowValue();
					employee.setfName(event.getNewValue());
				}
			});
			
			colEmployeeLast.setEditable(true);
			colEmployeeLast.setCellFactory(TextFieldTableCell.forTableColumn());
			colEmployeeLast.setOnEditCommit(new EventHandler<CellEditEvent<EmployeeModel, String>>(){
				
				@Override
				public void handle(CellEditEvent<EmployeeModel, String> event ) {
					EmployeeModel employee = event.getRowValue();
					employee.setlName(event.getNewValue());
				}
			});
			
			colPhoneNumber.setEditable(true);
			colPhoneNumber.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
			colPhoneNumber.setOnEditCommit(new EventHandler<CellEditEvent<EmployeeModel, Number>>(){
				
				@Override
				public void handle(CellEditEvent<EmployeeModel, Number> event ) {
					EmployeeModel employee = event.getRowValue();
					employee.setPhone(event.getNewValue().longValue());
				}
			});
		}
		
		public void btnAddLineClick(ActionEvent event) {
			EmployeeModel newline = new EmployeeModel(null, null, null, null);
			tblEmployee.getItems().add(newline);
		}
		
		public void btnRemoveLineClick(ActionEvent event) {
			tblEmployee.getItems().removeAll(tblEmployee.getSelectionModel().getSelectedItem());
		}
		
		public void btnSubmitInvoiceClick(ActionEvent event) {
			
			EmployeeModel employee = new EmployeeModel();
			
			List <List<String>> arrList = new ArrayList<>();
		
			for (int i = 0; i <tblEmployee.getItems().size(); i++) {
				employee = tblEmployee.getItems().get(i);
				arrList.add(new ArrayList<>());
				arrList.get(i).add(employee.getfName());
				arrList.get(i).add(employee.getlName());
				arrList.get(i).add(employee.getPhone().toString());
			}
			try {
				Connection con = DBConnector.getConnection();
				
				for (int i = 0; i < arrList.size(); i++) {
					PreparedStatement stmt = con.prepareStatement( "insert into keyemployee(FirstName, LastName, PhoneNumber) values(?,?,?)");
					for (int j = 0; j < arrList.get(i).size(); j++) {
						switch (j) {
						case 0: stmt.setString(j+1, arrList.get(i).get(j));
								break;
						case 1: stmt.setString(j+1, arrList.get(i).get(j));
								break;
						case 2: stmt.setLong(j+1, Long.valueOf(arrList.get(i).get(j)));
								break;
						}
					}
					System.out.println(stmt);
					stmt.execute();
				}
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
}
