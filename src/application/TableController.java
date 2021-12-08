package application;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TableController implements Initializable{
	// Get the table column elements for employee tab
	@FXML
	private TableView<ModelTable> tblEmployees;
	@FXML
	private TableColumn<ModelTable, Integer> colEmpID;
	@FXML
	private TableColumn<ModelTable, String> colEmpFirst;
	@FXML
	private TableColumn<ModelTable, String> colEmpLast;
	@FXML
	private TableColumn<ModelTable, Integer> colEmpArea;
	@FXML
	private TableColumn<ModelTable, Integer> colEmpPhone;
	@FXML
	private TableColumn<ModelTable, String> colEmpAddress;
	@FXML
	private TableColumn<ModelTable, Boolean> colEmpManager;
	@FXML
	private Button btnEmpEdit;
	@FXML
	private Button btnEmpRefresh;
	
	// Get the table column elements and Buttons for Inventory tab
	@FXML
	private TableView<InventoryModel> tblInventory;
	@FXML
	private TableColumn<InventoryModel, Integer> colInvProd;
	@FXML
	private TableColumn<InventoryModel, String> colInvID;
	@FXML
	private TableColumn<InventoryModel, Double> colInvPrice;
	@FXML
	private TableColumn<InventoryModel, Integer> colInvAmt;
	@FXML
	private TableColumn<InventoryModel, Integer> colInvMan;
	@FXML
	private TableColumn<InventoryModel, String> colInvSupp;
	@FXML
	private TableColumn<InventoryModel, String> colInvDesc;
	@FXML
	private Button btnInvEdit;
	@FXML
	private Button btnInvRefresh;
	
	// Get the table Columns and buttons for Schedule tab
	@FXML
	private TableView<ScheduleModel> tblSchedule;
	@FXML
	private TableColumn<ScheduleModel, String> colSchedEmp;
	@FXML
	private TableColumn<ScheduleModel, String> colSchedDate;
	@FXML
	private TableColumn<ScheduleModel, String> colSchedStart;
	@FXML
	private TableColumn<ScheduleModel, String> colSchedEnd;
	@FXML
	private TableColumn<ScheduleModel, String> colSchedCom;
	@FXML
	private TableColumn<ScheduleModel, String> colSchedID;
	@FXML
	private TableColumn<ScheduleModel, Double> colSchedWage;
	@FXML
	private TableColumn<ScheduleModel, Double> colSchedCost;
	@FXML
	private DatePicker dateScheduleShow;
	@FXML
	private Button btnNewSched;
	@FXML
	private Button btnSchedSearch;
	@FXML
	private Button btnSchedEdit;
	@FXML
	private Button btnSchedGo;
	@FXML
	private Button btnSchedRefresh;
	@FXML 
	private TextField txtEmpSearch;
		
	// Observable list for the Model Table for Employee
	ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
	ObservableList<InventoryModel> ilist = FXCollections.observableArrayList();
	ObservableList<ScheduleModel> slist = FXCollections.observableArrayList();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Fill all the tables
		try {
			Connection con = DBConnector.getConnection();
			
			// Result sets to hold SQL Queries
			ResultSet rsEmp = con.createStatement().executeQuery("select * from employee");
			ResultSet rsInv = con.createStatement().executeQuery("select * from Inventory");
			ResultSet rsSched = con.createStatement().executeQuery("select * from paysched");
			
			// Employee table
			while (rsEmp.next()) {
				oblist.add(new ModelTable(rsEmp.getInt("empID"),rsEmp.getString("fName"), 
						rsEmp.getString("lName"), rsEmp.getInt("areaCode"), rsEmp.getInt("phoneNumber"), rsEmp.getString("address"), 
						rsEmp.getBoolean("manager"), rsEmp.getInt("wage")));
				
				colEmpID.setCellValueFactory(new PropertyValueFactory<>("empID"));
				colEmpFirst.setCellValueFactory(new PropertyValueFactory<>("fName"));
				colEmpLast.setCellValueFactory(new PropertyValueFactory<>("lName"));
				colEmpArea.setCellValueFactory(new PropertyValueFactory<>("areaCode"));
				colEmpPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
				colEmpAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
				colEmpManager.setCellValueFactory(new PropertyValueFactory<>("manager"));
				
				tblEmployees.setItems(oblist);
			}
			
			// Inventory Table
			while (rsInv.next()) {
				ilist.add(new InventoryModel(rsInv.getString("product"),rsInv.getInt("prodID"), 
						rsInv.getDouble("price"), rsInv.getInt("amount"), rsInv.getString("manufacturer"), rsInv.getString("supplier"), 
						rsInv.getString("description")));
				
				colInvProd.setCellValueFactory(new PropertyValueFactory<>("product"));
				colInvID.setCellValueFactory(new PropertyValueFactory<>("id"));
				colInvPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
				colInvAmt.setCellValueFactory(new PropertyValueFactory<>("inventory"));
				colInvMan.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
				colInvSupp.setCellValueFactory(new PropertyValueFactory<>("supplier"));
				colInvDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
					
				tblInventory.setItems(ilist);
			}
			
			// Schedule Table
			while(rsSched.next()) {
				slist.add(new ScheduleModel(rsSched.getString("empID"), rsSched.getString("day"), rsSched.getString("shiftStart"), 
						rsSched.getString("shiftEnd"), rsSched.getString("comments"), rsSched.getInt("shiftID"), rsSched.getDouble("wage"), rsSched.getDouble("shiftCost")));
					
				colSchedEmp.setCellValueFactory(new PropertyValueFactory<>("empID"));
				colSchedDate.setCellValueFactory(new PropertyValueFactory<>("day"));
				colSchedStart.setCellValueFactory(new PropertyValueFactory<>("shiftStart"));
				colSchedEnd.setCellValueFactory(new PropertyValueFactory<>("shiftEnd"));
				colSchedID.setCellValueFactory(new PropertyValueFactory<>("shiftID"));
				colSchedCom.setCellValueFactory(new PropertyValueFactory<>("comments"));
				colSchedWage.setCellValueFactory(new PropertyValueFactory<>("wage"));
				colSchedCost.setCellValueFactory(new PropertyValueFactory<>("shiftCost"));
					
				tblSchedule.setItems(slist);
			}
			
			con.close();		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML 
	public void handleEditButtonAction(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/EmpEdit.fxml"));
			Scene scene = new Scene(root, 900, 420);
			stage.setTitle("Edit Employee Table");
			stage.setScene(scene);
			stage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void handleEmpRefreshButton(ActionEvent event) {
		// Empty the table before refreshing
		tblEmployees.getItems().clear();
		// Refresh the table
		try {
			Connection con = DBConnector.getConnection();
			
			ResultSet rsEmp = con.createStatement().executeQuery("select * from employee");
			System.out.println(rsEmp);
			
			while (rsEmp.next()) {
				oblist.add(new ModelTable(rsEmp.getInt("empID"),rsEmp.getString("fName"), 
						rsEmp.getString("lName"), rsEmp.getInt("areaCode"), rsEmp.getInt("phoneNumber"), rsEmp.getString("address"), 
						rsEmp.getBoolean("manager"), rsEmp.getInt("wage")));
				
				colEmpID.setCellValueFactory(new PropertyValueFactory<>("empID"));
				colEmpFirst.setCellValueFactory(new PropertyValueFactory<>("fName"));
				colEmpLast.setCellValueFactory(new PropertyValueFactory<>("lName"));
				colEmpArea.setCellValueFactory(new PropertyValueFactory<>("areaCode"));
				colEmpPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
				colEmpAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
				colEmpManager.setCellValueFactory(new PropertyValueFactory<>("manager"));
				
				tblEmployees.setItems(oblist);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void handleEditInvButton(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/InventoryEdit.fxml"));
			Scene scene = new Scene(root, 534, 366);
			stage.setTitle("Edit Inventory Table");
			stage.setScene(scene);
			stage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void handleRefreshInvButton(ActionEvent event) {
		// Empty the table before refreshing
		tblInventory.getItems().clear();
		// Refresh the table
		
		try {
			Connection con = DBConnector.getConnection();

			ResultSet rsInv = con.createStatement().executeQuery("select * from Inventory");
			
			while (rsInv.next()) {
				ilist.add(new InventoryModel(rsInv.getString("product"),rsInv.getInt("prodID"), 
						rsInv.getDouble("price"), rsInv.getInt("amount"), rsInv.getString("manufacturer"), rsInv.getString("supplier"), 
						rsInv.getString("description")));
				
				colInvProd.setCellValueFactory(new PropertyValueFactory<>("product"));
				colInvID.setCellValueFactory(new PropertyValueFactory<>("id"));
				colInvPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
				colInvAmt.setCellValueFactory(new PropertyValueFactory<>("inventory"));
				colInvMan.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
				colInvSupp.setCellValueFactory(new PropertyValueFactory<>("supplier"));
				colInvDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
					
				tblInventory.setItems(ilist);
			}
			
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void handelScheduleEditButton(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/EditSchedule.fxml"));
			Scene scene = new Scene(root, 668, 511);
			stage.setTitle("Edit Schedule Table");
			stage.setScene(scene);
			stage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void handleNewSchedButton(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/ScheduleEmployee.fxml"));
			Scene scene = new Scene(root, 700, 270);
			stage.setTitle("Edit Employee Table");
			stage.setScene(scene);
			stage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void handleSchuleSearchButton(ActionEvent event) {
		// Remove the data that's in the table
		tblSchedule.getItems().clear();
		String empID = txtEmpSearch.getText();
		LocalDate showDate = dateScheduleShow.getValue();
		
		if (showDate == null) {
			try {
				Connection con = DBConnector.getConnection();
				ResultSet rsSched = con.createStatement().executeQuery("select * from paysched where empID = " + empID);
				// Schedule Table
				while(rsSched.next()) {
					slist.add(new ScheduleModel(rsSched.getString("empID"), rsSched.getString("day"), rsSched.getString("shiftStart"), 
							rsSched.getString("shiftEnd"), rsSched.getString("comments"), rsSched.getInt("shiftID"), rsSched.getDouble("wage"), rsSched.getDouble("shiftCost")));
									
					colSchedEmp.setCellValueFactory(new PropertyValueFactory<>("empID"));
					colSchedDate.setCellValueFactory(new PropertyValueFactory<>("day"));
					colSchedStart.setCellValueFactory(new PropertyValueFactory<>("shiftStart"));
					colSchedEnd.setCellValueFactory(new PropertyValueFactory<>("shiftEnd"));
					colSchedID.setCellValueFactory(new PropertyValueFactory<>("shiftID"));
					colSchedCom.setCellValueFactory(new PropertyValueFactory<>("comments"));
					colSchedWage.setCellValueFactory(new PropertyValueFactory<>("wage"));
					colSchedCost.setCellValueFactory(new PropertyValueFactory<>("shiftCost"));
									
					tblSchedule.setItems(slist);
				}
							
				con.close();
			} catch (SQLException e) {
				infoBox("Make sure the ID is an integer, and that an employee with the ID exists.", null, "Failed");
				e.printStackTrace();
			}
		} else
		{
			try {
				Connection con = DBConnector.getConnection();
				ResultSet rsSched = con.createStatement().executeQuery("select * from paysched where day = '" + showDate + "' and empID = " + empID);
				// Schedule Table
				while(rsSched.next()) {
					slist.add(new ScheduleModel(rsSched.getString("empID"), rsSched.getString("day"), rsSched.getString("shiftStart"), 
							rsSched.getString("shiftEnd"), rsSched.getString("comments"), rsSched.getInt("shiftID"), rsSched.getDouble("wage"), rsSched.getDouble("shiftCost")));
									
					colSchedEmp.setCellValueFactory(new PropertyValueFactory<>("empID"));
					colSchedDate.setCellValueFactory(new PropertyValueFactory<>("day"));
					colSchedStart.setCellValueFactory(new PropertyValueFactory<>("shiftStart"));
					colSchedEnd.setCellValueFactory(new PropertyValueFactory<>("shiftEnd"));
					colSchedID.setCellValueFactory(new PropertyValueFactory<>("shiftID"));
					colSchedCom.setCellValueFactory(new PropertyValueFactory<>("comments"));
					colSchedWage.setCellValueFactory(new PropertyValueFactory<>("wage"));
					colSchedCost.setCellValueFactory(new PropertyValueFactory<>("shiftCost"));;
									
					tblSchedule.setItems(slist);
				}
							
				con.close();
			} catch (SQLException e) {
				infoBox("Make sure the ID is an integer, and that an employee with the ID exists.", null, "Failed");
				e.printStackTrace();
			}
		}		
	}
	
	public void handleScheduleGoButton(ActionEvent event) {
		tblSchedule.getItems().clear();
		LocalDate showDate = dateScheduleShow.getValue();
		String empID = txtEmpSearch.getText();
		
		if (!"".equals(empID)) {
			try {
				Connection con = DBConnector.getConnection();
				ResultSet rsSched = con.createStatement().executeQuery("select * from paysched where day = '" + showDate + "' and empID = " + empID);
				// Schedule Table
				while(rsSched.next()) {
					slist.add(new ScheduleModel(rsSched.getString("empID"), rsSched.getString("day"), rsSched.getString("shiftStart"), 
							rsSched.getString("shiftEnd"), rsSched.getString("comments"), rsSched.getInt("shiftID"), rsSched.getDouble("wage"), rsSched.getDouble("shiftCost")));
									
					colSchedEmp.setCellValueFactory(new PropertyValueFactory<>("empID"));
					colSchedDate.setCellValueFactory(new PropertyValueFactory<>("day"));
					colSchedStart.setCellValueFactory(new PropertyValueFactory<>("shiftStart"));
					colSchedEnd.setCellValueFactory(new PropertyValueFactory<>("shiftEnd"));
					colSchedID.setCellValueFactory(new PropertyValueFactory<>("shiftID"));
					colSchedCom.setCellValueFactory(new PropertyValueFactory<>("comments"));
					colSchedWage.setCellValueFactory(new PropertyValueFactory<>("wage"));
					colSchedCost.setCellValueFactory(new PropertyValueFactory<>("shiftCost"));
									
					tblSchedule.setItems(slist);
				}
							
				con.close();
			} catch (SQLException e) {
				infoBox("Make sure the ID is an integer, and that an employee with the ID exists.", null, "Failed");
				e.printStackTrace();
			}
		} else if(empID == "")
		{
			try {
				Connection con = DBConnector.getConnection();
				ResultSet rsSched = con.createStatement().executeQuery("select * from paysched where day = '" + showDate + "'");
				// Schedule Table
				while(rsSched.next()) {
					slist.add(new ScheduleModel(rsSched.getString("empID"), rsSched.getString("day"), rsSched.getString("shiftStart"), 
							rsSched.getString("shiftEnd"), rsSched.getString("comments"), rsSched.getInt("shiftID"), rsSched.getDouble("wage"), rsSched.getDouble("shiftCost")));
									
					colSchedEmp.setCellValueFactory(new PropertyValueFactory<>("empID"));
					colSchedDate.setCellValueFactory(new PropertyValueFactory<>("day"));
					colSchedStart.setCellValueFactory(new PropertyValueFactory<>("shiftStart"));
					colSchedEnd.setCellValueFactory(new PropertyValueFactory<>("shiftEnd"));
					colSchedID.setCellValueFactory(new PropertyValueFactory<>("shiftID"));
					colSchedCom.setCellValueFactory(new PropertyValueFactory<>("comments"));
					colSchedWage.setCellValueFactory(new PropertyValueFactory<>("wage"));
					colSchedCost.setCellValueFactory(new PropertyValueFactory<>("shiftCost"));
									
					tblSchedule.setItems(slist);
				}
							
				con.close();
			} catch (SQLException e) {
				infoBox("Make sure the ID is an integer, and that an employee with the ID exists.", null, "Failed");
				e.printStackTrace();
			}
		} else if(empID == "" && showDate == null) {
			try {
				Connection con = DBConnector.getConnection();
				ResultSet rsSched = con.createStatement().executeQuery("select * from paysched");
				// Schedule Table
				while(rsSched.next()) {
					slist.add(new ScheduleModel(rsSched.getString("empID"), rsSched.getString("day"), rsSched.getString("shiftStart"), 
							rsSched.getString("shiftEnd"), rsSched.getString("comments"), rsSched.getInt("shiftID"), rsSched.getDouble("wage"), rsSched.getDouble("shiftCost")));
									
					colSchedEmp.setCellValueFactory(new PropertyValueFactory<>("empID"));
					colSchedDate.setCellValueFactory(new PropertyValueFactory<>("day"));
					colSchedStart.setCellValueFactory(new PropertyValueFactory<>("shiftStart"));
					colSchedEnd.setCellValueFactory(new PropertyValueFactory<>("shiftEnd"));
					colSchedID.setCellValueFactory(new PropertyValueFactory<>("shiftID"));
					colSchedCom.setCellValueFactory(new PropertyValueFactory<>("comments"));
					colSchedWage.setCellValueFactory(new PropertyValueFactory<>("wage"));
					colSchedCost.setCellValueFactory(new PropertyValueFactory<>("shiftCost"));
									
					tblSchedule.setItems(slist);
				}
							
				con.close();
			} catch (SQLException e) {
				infoBox("Make sure the ID is an integer, and that an employee with the ID exists.", null, "Failed");
				e.printStackTrace();
			}
		}
		
	}
	
	public void handleScheduleRefreshButton(ActionEvent event) {
		tblSchedule.getItems().clear();
		dateScheduleShow.setValue(null);
		txtEmpSearch.setText(null);
		
		try {
			Connection con = DBConnector.getConnection();
			ResultSet rsSched = con.createStatement().executeQuery("select * from paysched");
			// Schedule Table
			while(rsSched.next()) {
				slist.add(new ScheduleModel(rsSched.getString("empID"), rsSched.getString("day"), rsSched.getString("shiftStart"), 
						rsSched.getString("shiftEnd"), rsSched.getString("comments"), rsSched.getInt("shiftID"), rsSched.getDouble("wage"), rsSched.getDouble("shiftCost")));
								
				colSchedEmp.setCellValueFactory(new PropertyValueFactory<>("empID"));
				colSchedDate.setCellValueFactory(new PropertyValueFactory<>("day"));
				colSchedStart.setCellValueFactory(new PropertyValueFactory<>("shiftStart"));
				colSchedEnd.setCellValueFactory(new PropertyValueFactory<>("shiftEnd"));
				colSchedID.setCellValueFactory(new PropertyValueFactory<>("shiftID"));
				colSchedCom.setCellValueFactory(new PropertyValueFactory<>("comments"));
				colSchedWage.setCellValueFactory(new PropertyValueFactory<>("wage"));
				colSchedCost.setCellValueFactory(new PropertyValueFactory<>("shiftCost"));
								
				tblSchedule.setItems(slist);
			}
						
			con.close();
		} catch (SQLException e) {
			infoBox("Make sure the ID is an integer, and that an employee with the ID exists.", null, "Failed");
			e.printStackTrace();
		}
	}
	
	public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }	
}	