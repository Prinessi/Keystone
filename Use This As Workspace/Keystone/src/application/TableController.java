package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class TableController implements Initializable{
	@FXML
	private Label lblUsername;
	// Get Panes
	@FXML
	private Pane paneInventory;
	@FXML
	private Pane paneInvoices;
	@FXML
	private Pane paneSchedule;
	@FXML
	private Pane paneEmployee;
	
	// Get the table column elements and Buttons for Inventory tab
	@FXML
	private TableView<InventoryMod> tblInventory;
	@FXML
	private TableColumn<InventoryMod, String> colItemCode;
	@FXML
	private TableColumn<InventoryMod, String> colItemDesc;
	@FXML
	private TableColumn<InventoryMod, Number> colItemCost;
	@FXML
	private TableColumn<InventoryMod, Number> colItemQuantity;
	@FXML
	private Button btnInventoryRefresh;
	
	// Get the table column elements and buttons for Invoices
	@FXML
	private TableView<InvoiceModel> tblInvoices;
	@FXML
	private TableColumn<InvoiceModel, Number> colInvNumber;
	@FXML
	private TableColumn<InvoiceModel, String> colInvComp;
	@FXML
	private TableColumn<InvoiceModel, String> colInvDate;
	@FXML
	private TableColumn<InvoiceModel, String> colInvItemID;
	@FXML
	private TableColumn<InvoiceModel, Number> colInvQuantity;
	@FXML
	private TableColumn<InvoiceModel, String> colInvDesc;
	@FXML
	private TableColumn<InvoiceModel, Number> colInvWeight;
	@FXML
	private TableColumn<InvoiceModel, Number> colInvPrice;
	@FXML
	private TableColumn<InvoiceModel, Number> colInvCost;
	@FXML
	private Button btnNewInvoice;
	@FXML 
	private Button btnInvoiceRefresh;
	
	// Get the table, columns, and buttons for Schedule tab
	@FXML
	private TableView<ScheduleModel> tblSchedule;
	@FXML
	private TableColumn<ScheduleModel, Number> colEmpID;
	@FXML
	private TableColumn<ScheduleModel, String> colFirst;
	@FXML
	private TableColumn<ScheduleModel, String> colLast;
	@FXML
	private TableColumn<ScheduleModel, String> colStart;
	@FXML
	private TableColumn<ScheduleModel, String> colEnd;
	@FXML
	private TableColumn<ScheduleModel, String> colDay;
	@FXML
	private Button btnScheduleRefresh;
	@FXML
	private Button btnNewSchedule;
	
	// Get the table, columns, and buttons for Employee tab
	@FXML
	private TableView<EmployeeModel> tblEmployee;
	@FXML
	private TableColumn<EmployeeModel, Number> colEmployeeID;
	@FXML
	private TableColumn<EmployeeModel, String> colEmployeeFirst;
	@FXML
	private TableColumn<EmployeeModel, String> colEmployeeLast;
	@FXML
	private TableColumn<EmployeeModel, Number> colPhoneNumber;
	
	@FXML
	private Button btnEmployeeRefresh;
	
	// Observable list for the Model Table for Employee
	ObservableList<InvoiceModel> oblist = FXCollections.observableArrayList();
	ObservableList<InventoryMod> ilist = FXCollections.observableArrayList();
	ObservableList<ScheduleModel> slist = FXCollections.observableArrayList();
	ObservableList<EmployeeModel> elist = FXCollections.observableArrayList();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Make Inventory Columns Description and Quantity editable
		tblInventory.setEditable(true);
		colItemQuantity.setEditable(true);
		colItemQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
		colItemDesc.setCellFactory(TextFieldTableCell.forTableColumn());
		colItemQuantity.setOnEditCommit(event -> {
		    InventoryMod inventory = event.getRowValue();
		    inventory.setAmount(event.getNewValue().doubleValue());
		    updateData("inv_amount", event.getNewValue(), inventory.getItem());
		});
		
		colItemDesc.setOnEditCommit(event -> {
		    InventoryMod inventory = event.getRowValue();
		    inventory.setDesc(event.getNewValue());
		    updateDataString("ITEM_DESCRIPTION", event.getNewValue(), inventory.getItem());
		});
		// Fill all the tables
		try {
			Connection con = DBConnector.getConnection();
			
			// Result sets to hold SQL Queries
			ResultSet rsInv = con.createStatement().executeQuery("select * from keyinventory");	
			ResultSet rsEmp = con.createStatement().executeQuery("select EmpID, FirstName, LastName, PhoneNumber from keyemployee");	
			ResultSet rsInvoices = con.createStatement().executeQuery("select * from keyinvoices");
			ResultSet rsSchedule = con.createStatement().executeQuery("select Empid, FirstName, LastName, START_TIME, END_TIME, DAY from keyscheduleview");
			
			// Inventory Table
			while (rsInv.next()) {
				ilist.add(new InventoryMod(rsInv.getString("ITEM_ID"),rsInv.getString("ITEM_DESCRIPTION"), 
						rsInv.getDouble("ITEM_PRICE"), rsInv.getDouble("INV_AMOUNT")));
				
				colItemCode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem()));
				colItemDesc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDesc()));	
				colItemCost.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()));
				colItemQuantity.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAmount()));
				
				tblInventory.setItems(ilist);
			}
			
			// Invoices Table
			while (rsInvoices.next()) {
				oblist.add(new InvoiceModel(rsInvoices.getString("COMPANY_RECIEVED"), rsInvoices.getString("SHIP_DATE"), rsInvoices.getString("ITEM_ID"),
						rsInvoices.getString("ITEM_DESCRIPTION"), rsInvoices.getInt("INVOICE_NUMBER"), rsInvoices.getDouble("QUANTITY"),  
						rsInvoices.getDouble("ITEM_WEIGHT"), rsInvoices.getDouble("ITEM_PRICE"), rsInvoices.getDouble("INVOICE_COST")));
							
				colInvNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInvNumber()));
				colInvComp.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInvCompany()));
				colInvDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInvDate()));
				colInvItemID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInvItemId()));
				colInvQuantity.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getInvQuantity()));
				colInvDesc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInvDesc()));
				colInvWeight.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getInvWeight()));
				colInvPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getInvPrice()));
				colInvCost.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getInvCost()));
								
				tblInvoices.setItems(oblist);
			}
			
			// Schedule Table
			while (rsSchedule.next()) {
				slist.add(new ScheduleModel(rsSchedule.getInt("Empid"), rsSchedule.getString("FirstName"), rsSchedule.getString("LastName"),
						rsSchedule.getString("START_TIME"), rsSchedule.getString("END_TIME"), rsSchedule.getString("DAY")));
				
				colEmpID.setCellValueFactory(schedData -> new SimpleIntegerProperty(schedData.getValue().getEmpID()));
				colFirst.setCellValueFactory(schedData -> new SimpleStringProperty(schedData.getValue().getfName()));
				colLast.setCellValueFactory(schedData -> new SimpleStringProperty(schedData.getValue().getlName()));
				colStart.setCellValueFactory(schedData -> new SimpleStringProperty(schedData.getValue().getShiftStart()));
				colEnd.setCellValueFactory(schedData -> new SimpleStringProperty(schedData.getValue().getShiftEnd()));
				colDay.setCellValueFactory(schedData -> new SimpleStringProperty(schedData.getValue().getDay()));
				
				tblSchedule.setItems(slist);
			}
			
			while (rsEmp.next()) {
				elist.add(new EmployeeModel(rsEmp.getInt("EmpID"), rsEmp.getString("FirstName"), rsEmp.getString("LastName"), rsEmp.getLong("PhoneNumber")));
				
				colEmployeeID.setCellValueFactory(empData -> new SimpleIntegerProperty(empData.getValue().getEmpID()));
				colEmployeeFirst.setCellValueFactory(empData -> new SimpleStringProperty(empData.getValue().getfName()));
				colEmployeeLast.setCellValueFactory(empData -> new SimpleStringProperty(empData.getValue().getlName()));
				colPhoneNumber.setCellValueFactory(empData -> new SimpleLongProperty(empData.getValue().getPhone()));
				
				tblEmployee.setItems(elist);
			}
//865, 524
			con.close();		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void btnInventoryClick(ActionEvent event) {
		paneInventory.setVisible(true);
		paneInvoices.setVisible(false);
		paneSchedule.setVisible(false);
		paneEmployee.setVisible(false);
	}
	
	public void btnInvoicesClick(ActionEvent event) {
		paneInventory.setVisible(false);
		paneInvoices.setVisible(true);
		paneSchedule.setVisible(false);
		paneEmployee.setVisible(false);
	}
	
	public void btnScheduleClick(ActionEvent event) {
		// Disabled until schedule shit is figured out
		/*paneInventory.setVisible(false);
		paneInvoices.setVisible(false);
		paneSchedule.setVisible(true);
		paneEmployee.setVisible(false);*/
	}
	
	public void btnEmployeeClick(ActionEvent event) {
		paneInventory.setVisible(false);
		paneInvoices.setVisible(false);
		paneSchedule.setVisible(false);
		paneEmployee.setVisible(true);
	}
	
	public void btnAddEmployeeClick(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/AddEmployee.fxml"));
		Scene scene = new Scene(root, 807, 479);
		Stage stage = new Stage();
		 
		stage.setTitle("Add New Employee");
		stage.setScene(scene);
		stage.show();
	}
	
	public void btnNewInvoiceClick(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/AddInvoice.fxml"));
		Scene scene = new Scene(root, 807, 479);
		Stage stage = new Stage();
		 
		stage.setTitle("Add New Invoice");
		stage.setScene(scene);
		stage.show();
	}
	
	public void btnNewScheduleClick(ActionEvent event) throws IOException {
		// Disabled until can fix
		Parent root = FXMLLoader.load(getClass().getResource("/AddSchedule.fxml"));
		Scene scene = new Scene(root, 807, 479);
		Stage stage = new Stage();
		 
		stage.setTitle("Create New Schedule");
		stage.setScene(scene);
		stage.show();
	}
	
	public void editQuantityCommit(CellEditEvent<InventoryMod, Double> event) {
		//InventoryMod invetorymod = event.getRowValue();
		//invetorymod.setAmount(event.getNewValue());
	}
	
	public void btnInventoryRefreshClick(ActionEvent event) {
		ilist.clear();
		try {
			Connection con = DBConnector.getConnection();
			ResultSet rsInv = con.createStatement().executeQuery("select * from keyinventory");	
			
			// Inventory Table
			while (rsInv.next()) {
				ilist.add(new InventoryMod(rsInv.getString("ITEM_ID"),rsInv.getString("ITEM_DESCRIPTION"), 
						rsInv.getDouble("ITEM_PRICE"), rsInv.getDouble("INV_AMOUNT")));
				
				colItemCode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem()));
				colItemDesc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDesc()));	
				colItemCost.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()));
				colItemQuantity.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAmount()));
				
				tblInventory.setItems(ilist);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void btnEmployeeRefreshClick(ActionEvent event) {
		elist.clear();
		try {
			Connection con = DBConnector.getConnection();
			ResultSet rsEmp = con.createStatement().executeQuery("select EmpID, FirstName, LastName, PhoneNumber from keyemployee");
			
			while (rsEmp.next()) {
				elist.add(new EmployeeModel(rsEmp.getInt("EmpID"), rsEmp.getString("FirstName"), rsEmp.getString("LastName"), rsEmp.getLong("PhoneNumber")));
				
				colEmployeeID.setCellValueFactory(empData -> new SimpleIntegerProperty(empData.getValue().getEmpID()));
				colEmployeeFirst.setCellValueFactory(empData -> new SimpleStringProperty(empData.getValue().getfName()));
				colEmployeeLast.setCellValueFactory(empData -> new SimpleStringProperty(empData.getValue().getlName()));
				colPhoneNumber.setCellValueFactory(empData -> new SimpleLongProperty(empData.getValue().getPhone()));
				
				tblEmployee.setItems(elist);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void btnInvoiceRefreshClick(ActionEvent event) {
		oblist.clear();
		try {
			Connection con = DBConnector.getConnection();
			ResultSet rsInvoices = con.createStatement().executeQuery("select * from keyinvoices");
			while (rsInvoices.next()) {
				oblist.add(new InvoiceModel(rsInvoices.getString("COMPANY_RECIEVED"), rsInvoices.getString("SHIP_DATE"), rsInvoices.getString("ITEM_ID"),
						rsInvoices.getString("ITEM_DESCRIPTION"), rsInvoices.getInt("INVOICE_NUMBER"), rsInvoices.getDouble("QUANTITY"),  
						rsInvoices.getDouble("ITEM_WEIGHT"), rsInvoices.getDouble("ITEM_PRICE"), rsInvoices.getDouble("INVOICE_COST")));
							
				colInvNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInvNumber()));
				colInvComp.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInvCompany()));
				colInvDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInvDate()));
				colInvItemID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInvItemId()));
				colInvQuantity.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getInvQuantity()));
				colInvDesc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInvDesc()));
				colInvWeight.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getInvWeight()));
				colInvPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getInvPrice()));
				colInvCost.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getInvCost()));
								
				tblInvoices.setItems(oblist);
			}
			con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void btnScheduleRefreshClick(ActionEvent event) {
		slist.clear();
		try {
			Connection con = DBConnector.getConnection();
			ResultSet rsSchedule = con.createStatement().executeQuery("select Empid, FirstName, LastName, START_TIME, END_TIME, DAY from keyscheduleview");
		while (rsSchedule.next()) {
			slist.add(new ScheduleModel(rsSchedule.getInt("Empid"), rsSchedule.getString("FirstName"), rsSchedule.getString("LastName"),
					rsSchedule.getString("START_TIME"), rsSchedule.getString("END_TIME"), rsSchedule.getString("DAY")));
			
			colEmpID.setCellValueFactory(schedData -> new SimpleIntegerProperty(schedData.getValue().getEmpID()));
			colFirst.setCellValueFactory(schedData -> new SimpleStringProperty(schedData.getValue().getfName()));
			colLast.setCellValueFactory(schedData -> new SimpleStringProperty(schedData.getValue().getlName()));
			colStart.setCellValueFactory(schedData -> new SimpleStringProperty(schedData.getValue().getShiftStart()));
			colEnd.setCellValueFactory(schedData -> new SimpleStringProperty(schedData.getValue().getShiftEnd()));
			colDay.setCellValueFactory(schedData -> new SimpleStringProperty(schedData.getValue().getDay()));
			
			tblSchedule.setItems(slist);
			}
		con.close();
		}catch (SQLException e) {
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
	
	private void updateData(String column, Number newValue, String id) {
	    try (
	        Connection con = DBConnector.getConnection();
	        PreparedStatement stmt = con.prepareStatement("UPDATE keyinvoices SET " + column + " = " + newValue + " WHERE ITEM_ID = '" + id + "'");
	    ) {
	    	stmt.execute();
	    } catch (SQLException ex) {
	        System.err.println("Error");
	        ex.printStackTrace(System.err);
	    }
	}
	
	private void updateDataString(String column, String newValue, String id) {
		try (
	        Connection con = DBConnector.getConnection();
	        PreparedStatement stmt = con.prepareStatement("UPDATE keyinvoices SET " + column + " = '" + newValue + "' WHERE ITEM_ID = '" + id + "'");
	    ) {
	    	stmt.execute();
	    } catch (SQLException ex) {
	        System.err.println("Error");
	        ex.printStackTrace(System.err);
	    }
	}
}	