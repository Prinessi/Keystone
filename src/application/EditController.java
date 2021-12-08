package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class EditController {
	//Get the add employee text boxes using the ModelTabel for Employees
	@FXML
	private TextField empAddID;
	@FXML
	private TextField empAddFirst;
	@FXML
	private TextField empAddLast;	
	@FXML
	private TextField empAddArea;
	@FXML
	private TextField empAddPhone;
	@FXML
	private TextField empAddAddress;
	@FXML
	private TextField empAddManager;
	@FXML
	private TextField empAddWage;
	
	// Get the edit employee text boxes
	@FXML
	private TextField empEditID;
	@FXML
	private TextField empEditFirst;
	@FXML
	private TextField empEditLast;	
	@FXML
	private TextField empEditArea;
	@FXML
	private TextField empEditPhone;
	@FXML
	private TextField empEditAddress;
	@FXML
	private TextField empEditManager;
	@FXML
	private TextField empEditWage;
	
	// Get the YoUrE fIrEd SeE yA lAtEr text boxes
	@FXML
	private TextField empDeleteID;
	@FXML
	private TextField empDeleteFirst;
	@FXML
	private TextField empDeleteLast;
	
	String empFirst, empLast, empAddress;
	String empID, empArea, empPhone;
	String empManager, empWage;
	
	ObservableList<ModelTable> addList = FXCollections.observableArrayList();
	ObservableList<SearchTable> oblist = FXCollections.observableArrayList();
	
	// Completed 3/16/21 Last Updated 4/3/21
	public void handleEmpAddButton(ActionEvent event) {
		// Get the text from the textfields
		empID = empAddID.getText();
		empFirst = empAddFirst.getText();
		empLast = empAddLast.getText();
		empArea = empAddArea.getText();
		empPhone = empAddPhone.getText();
		empAddress = empAddAddress.getText();
		empManager = empAddManager.getText();
		empWage = empAddWage.getText();
		
		// Attempt to convert strings into integers where applicable
		try{
			// Create the auto-generated username
			String empUsername, empPassword;
			String a = empFirst.toLowerCase();
			String b = empLast.toLowerCase();
			empUsername = a.charAt(0) + b;
			empPassword = "tempPassword";
			Integer intID = Integer.parseInt(empID);
			Integer intArea = Integer.parseInt(empArea);
			Integer intPhone = Integer.parseInt(empPhone);
			// Must be read as either "True" or "False"
			Boolean boolMan = Boolean.parseBoolean(empManager);
			
			// Connect to the database
			Connection con = DBConnector.getConnection();
			// Create the string for the mySql Query
			String sqlStatement = "INSERT INTO employee VALUES ("+ intID + ", '"+ empUsername + "', '" + empPassword +"', CURRENT_TIME, '" +
					empFirst + "', '" + empLast + "', " + intArea +", "
					+ intPhone + ", '" + empAddress + "', " + boolMan + ", " + empWage + ")";
			// Run the statement
			try {
				con.createStatement().executeUpdate(sqlStatement);
				// TODO Create a bow to show that updates happened successfully, and refresh the employee table.
				infoBox("Successfully added a user, please refresh view to see them.", null, "Success");
				
				// Clear text boxes
				String clear = "";
				empAddID.setText(clear);
				empAddFirst.setText(clear);
				empAddLast.setText(clear);
				empAddArea.setText(clear);
				empAddPhone.setText(clear);
				empAddAddress.setText(clear);
				empAddManager.setText(clear);
				empAddWage.setText(clear);
			} catch (SQLException e) {
				infoBox("Could not add employee, make sure ID, areacode, phone, and wage are all integers, wage to only 2 decimal points, "
						+ "and manager is set to either 'True' or 'False'", null, "Failed"); 
				e.printStackTrace();
			}
		} 
		// Throw error if there is an issue with parsing data.
		catch(NumberFormatException NFE) {
			infoBox("Could not add employee, make sure ID, areacode, phone, and wage are all integers, wage to only 2 decimal points, "
					+ "and manager is set to either 'True' or 'False'", null, "Failed");
			NFE.printStackTrace();
		}
		
		
	}
	
	// Completed 3/17/21 Last Updated 3/17/21
	public void handleEmpEditSearch(ActionEvent event) {
		// Convert textfields to strings
		empID = empEditID.getText();
		empFirst = empEditFirst.getText();
		empLast = empEditLast.getText();
		
			try {
				if(empID == "") {
					// Search using first and last name
					String sqlSearch = "Select * from employee where fName = '" + empFirst + "' and lName = '" + empLast + "'";
					// Search and get result
					Connection con = DBConnector.getConnection();
					
					try {
						ResultSet rsEmpSearch = con.createStatement().executeQuery(sqlSearch);
						
						if(rsEmpSearch.next())
						{
							empEditID.setText(rsEmpSearch.getString("empID"));
							empEditFirst.setText(rsEmpSearch.getString("fName"));
							empEditLast.setText(rsEmpSearch.getString("lName"));
							empEditArea.setText(rsEmpSearch.getString("areaCode"));
							empEditPhone.setText(rsEmpSearch.getString("phoneNumber"));
							empEditAddress.setText(rsEmpSearch.getString("address"));
							empEditManager.setText(rsEmpSearch.getString("manager"));
							empEditWage.setText(rsEmpSearch.getString("Wage"));
						} else {
							if(empFirst == "" || empLast == "") {
								infoBox("Please enter both the first name last name.", null, "Failed");
							} else {
								infoBox("Employee by this name does not exist.", null, "Failed");
							}
						}
						
						// Close connection when done
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					// Convert String to Integer
					Integer intID = Integer.parseInt(empID);
					// Search using empID
					String sqlSearch = "Select * from employee where empID = " + intID;
					// Search and get result
					Connection con = DBConnector.getConnection();
					
					try {
						ResultSet rsEmpSearch = con.createStatement().executeQuery(sqlSearch);
						
						if(rsEmpSearch.next())
						{
							empEditID.setText(rsEmpSearch.getString("empID"));
							empEditFirst.setText(rsEmpSearch.getString("fName"));
							empEditLast.setText(rsEmpSearch.getString("lName"));
							empEditArea.setText(rsEmpSearch.getString("areaCode"));
							empEditPhone.setText(rsEmpSearch.getString("phoneNumber"));
							empEditAddress.setText(rsEmpSearch.getString("address"));
							empEditManager.setText(rsEmpSearch.getString("manager"));
							empEditWage.setText(rsEmpSearch.getString("wage"));
						} else {
							infoBox("Employee with this ID does not exist.", null, "Failed");
						}
						
						// Close connection when done
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			
			
		} catch (NumberFormatException NFE) {
			NFE.printStackTrace();
		}
		
	}
	
	// Completed 3/17/21 Last Updated 3/17/21
	public void handleEmpEditPublish(ActionEvent event) {
		// Get the text from the textfields
		empID = empEditID.getText();
		empFirst = empEditFirst.getText();
		empLast = empEditLast.getText();
		empArea = empEditArea.getText();
		empPhone = empEditPhone.getText();
		empAddress = empEditAddress.getText();
		empManager = empEditManager.getText();
		empWage = empEditWage.getText();
				
		// Attempt to convert strings into integers where applicable
		try{
			Integer intID = Integer.parseInt(empID);
			Integer intArea = Integer.parseInt(empArea);
			Integer intPhone = Integer.parseInt(empPhone);
			
			// Must be read as either "True" or "False"
			Boolean boolMan = Boolean.parseBoolean(empManager);
					
			// Connect to the database
			Connection con = DBConnector.getConnection();
			// Create the string for the mySql Query
			// Note: employee ID CANNOT be changed
			String sqlStatement = "update employee set fName = '" + empFirst + "', lName = '" + empLast + "', areaCode = " 
			+ intArea + ", phoneNumber = " + intPhone + ", address = '" + empAddress + "', manager = " + boolMan + ", wage = " + empWage +" where empID = " + intID;
			// Run the statement
			try {
				con.createStatement().executeUpdate(sqlStatement);
				infoBox("Successfully edited employee", null, "Success");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		// Throw error if there is an issue with parsing data.
		} catch(NumberFormatException NFE) {
			infoBox("Make sure ID, area code, and phone number are all integers, and make sure that manager is either 'True' or 'False'.",
					null, "Failed");
		}
	}
	
	// Completed 3/23/21 Last Updated 3/23/21
	public void handleEmpDeleteSearch(ActionEvent event) {
		empID = empDeleteID.getText();
		empFirst = empDeleteFirst.getText();
		empLast = empDeleteLast.getText();
		
		try {
			if(empID == "") {
				// Search using first and last name
				String sqlSearch = "Select * from employee where fName = '" + empFirst + "' and lName = '" + empLast + "'";
				// Search and get result
				Connection con = DBConnector.getConnection();
				
				try {
					ResultSet rsEmpSearch = con.createStatement().executeQuery(sqlSearch);
					
					if(rsEmpSearch.next())
					{
						empDeleteID.setText(rsEmpSearch.getString("empID"));
						empDeleteFirst.setText(rsEmpSearch.getString("fName"));
						empDeleteLast.setText(rsEmpSearch.getString("lName"));
					} else {
						if(empFirst == "" || empLast == "") {
							infoBox("Please enter both the first name last name.", null, "Failed");
						} else {
							infoBox("Employee by this name does not exist.", null, "Failed");
						}
					}
					
					// Close connection when done
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				// Convert String to Integer
				Integer intID = Integer.parseInt(empID);
				// Search using empID
				String sqlSearch = "Select * from employee where empID = " + intID;
				// Search and get result
				Connection con = DBConnector.getConnection();
				
				try {
					ResultSet rsEmpSearch = con.createStatement().executeQuery(sqlSearch);
					
					if(rsEmpSearch.next())
					{
						empDeleteID.setText(rsEmpSearch.getString("empID"));
						empDeleteFirst.setText(rsEmpSearch.getString("fName"));
						empDeleteLast.setText(rsEmpSearch.getString("lName"));
					} else {
						infoBox("Employee with this ID does not exist.", null, "Failed");
					}
					
					// Close connection when done
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
		
	} catch (NumberFormatException NFE) {
		NFE.printStackTrace();
	}
	}
	
	// Completed 3/23/21 Last Updated 3/23/21
	public void handleEmpDelete(ActionEvent event) {
		empID = empDeleteID.getText();
		empFirst = empDeleteFirst.getText();
		empLast = empDeleteLast.getText();
		
		try{
			// Change String to INT
			Integer intID = Integer.parseInt(empID);
			// Connect to the database
			Connection con = DBConnector.getConnection();
			// Create the string for the mySql Query
			String preSqlStatement = "DELETE FROM `schedule` where empID = " + intID;
			String sqlStatement = "DELETE FROM `employee` WHERE empID = " + intID;
			
			// Make sure all fields are filled either manually or through the search function.
			if(empID == "" || empFirst == "" || empLast == "") {
				infoBox("Please make sure all fields are filled using the search function to ensure user exists.", null, "Failed");
			} else {
				// Run the statement
				try {
					con.createStatement().executeUpdate(preSqlStatement);
					con.createStatement().executeUpdate(sqlStatement);
					infoBox("User successfully deleted.", null, "Success");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		// Throw error if there is an issue with parsing data.
		} catch(NumberFormatException NFE) {
			System.out.println("Uh oh, user did a nono. Make sure ID is an integer");
		}
	}
	
	// For alerts to show people they did bad things. /s
	public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }	
}