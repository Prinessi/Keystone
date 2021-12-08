package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;


public class ItemController {
	// Get FXML
	@FXML
	private TextField addItemName;
	
	@FXML
	private TextField addItemID;

	@FXML
	private TextField addItemPrice;
	
	@FXML
	private TextField addItemAmount;
	
	@FXML
	private TextField addItemMan;

	@FXML
	private TextField addItemSupplier;

	@FXML
	private TextField addItemDesc;

	@FXML
	private TextField editItemName;
	
	@FXML
	private TextField editItemID;
	
	@FXML
	private TextField editItemPrice;
	
	@FXML
	private TextField editItemAmount;
	
	@FXML
	private TextField editItemMan;
	
	@FXML
	private TextField editItemSupplier;
	
	@FXML
	private TextField editItemDesc;
	
	String itemName, itemID, itemPrice, itemMan, itemSupplier, itemDesc, itemAmount;
	Integer intID, intAmount;
	Double intPrice;
	
	public void handeAddItemButton(ActionEvent event) {
		// Get the text from the textfields
		itemName = addItemName.getText();
		itemID = addItemID.getText();
		itemPrice = addItemPrice.getText();
		itemAmount = addItemAmount.getText();
		itemMan = addItemMan.getText();
		itemSupplier = addItemSupplier.getText();
		itemDesc = addItemDesc.getText();
		
		
		try {
			intID = Integer.parseInt(itemID);
			intPrice = Double.parseDouble(itemPrice);
			intAmount = Integer.parseInt(itemAmount);
			
			Connection con = DBConnector.getConnection();
			String sqlStatement = "INSERT INTO inventory VALUES ('" + itemName + "', " + itemID + ", " +
					itemPrice + ", " + itemAmount +", '" + itemMan + "', '" + itemSupplier + "', '" + itemDesc + "')";
			try {
				con.createStatement().executeUpdate(sqlStatement);
				infoBox("Successfully added a item", null, "Success");
				
				// Clear text boxes
				String clear = "";
				addItemName.setText(clear);
				addItemID.setText(clear);
				addItemPrice.setText(clear);
				addItemMan.setText(clear);
				addItemSupplier.setText(clear);
				addItemDesc.setText(clear);
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch(NumberFormatException NFE) {
			infoBox("Make sure itemID is an integer, item price is a number, and item amount is an integer.", null, "Failed");
		}
		
	}
	
	public void handleSearchItemButton(ActionEvent event) {
		// Convert textfields to strings
		itemName = editItemName.getText();
		itemID = editItemID.getText();
			
			try {
				if(itemID == "") {
					// Search using first and last name
					String sqlSearch = "Select * from inventory where product = '" + itemName + "'";
					// Search and get result
					Connection con = DBConnector.getConnection();
						
					try {
						ResultSet rsEmpSearch = con.createStatement().executeQuery(sqlSearch);
							
						if(rsEmpSearch.next())
						{
							editItemName.setText(rsEmpSearch.getString("product"));
							editItemID.setText(rsEmpSearch.getString("prodID"));
							editItemPrice.setText(rsEmpSearch.getString("price"));
							editItemAmount.setText(rsEmpSearch.getString("amount"));
							editItemMan.setText(rsEmpSearch.getString("manufacturer"));
							editItemSupplier.setText(rsEmpSearch.getString("supplier"));
							editItemDesc.setText(rsEmpSearch.getString("description"));
						} else {
							infoBox("Product by this name does not exist.", null, "Failed");
						}
							
						// Close connection when done
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					// Convert String to Integer
					Integer intID = Integer.parseInt(itemID);
					// Search using empID
					String sqlSearch = "Select * from inventory where prodID = " + intID;
					// Search and get result
					Connection con = DBConnector.getConnection();
						
					try {
						ResultSet rsEmpSearch = con.createStatement().executeQuery(sqlSearch);
							
						if(rsEmpSearch.next())
						{
							editItemName.setText(rsEmpSearch.getString("product"));
							editItemID.setText(rsEmpSearch.getString("prodID"));
							editItemPrice.setText(rsEmpSearch.getString("price"));
							editItemAmount.setText(rsEmpSearch.getString("amount"));
							editItemMan.setText(rsEmpSearch.getString("manufacturer"));
							editItemSupplier.setText(rsEmpSearch.getString("supplier"));
							editItemDesc.setText(rsEmpSearch.getString("description"));
						} else {
							infoBox("Product with this ID does not exist.", null, "Failed");
						}
							
						// Close connection when done
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (NumberFormatException NFE) {
				NFE.printStackTrace();
			}
		
	}
	
	public void handleEditItemButton(ActionEvent event) {
		// Get the text
		itemName = editItemName.getText();
		itemID = editItemID.getText();
		itemPrice = editItemPrice.getText();
		itemAmount = editItemAmount.getText();
		itemMan = editItemMan.getText();
		itemSupplier = editItemSupplier.getText();
		itemDesc = editItemDesc.getText();
		
		// Attempt to convert strings into integers where applicable
		try{
			Integer intID = Integer.parseInt(itemID);
			Double intPrice = Double.parseDouble(itemPrice);
			Integer intAmount = Integer.parseInt(itemAmount);
								
			// Connect to the database
			Connection con = DBConnector.getConnection();
			// Create the string for the mySql Query
			// Note: employee ID CANNOT be changed
			String sqlStatement = "update inventory set product = '" + itemName + "', description = '" + itemDesc + "', price = " 
					+ intPrice + ", amount = " + intAmount + ", manufacturer = '" + itemMan + "', supplier = '" + itemSupplier + "' where prodID = " + intID;
			// Run the statement
			try {
				con.createStatement().executeUpdate(sqlStatement);
				// TODO Create an alert to show that updates happened successfully, and refresh the employee table.
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			infoBox("Product successfully edited.", null, "Success");
		// Throw error if there is an issue with parsing data.
		} catch(NumberFormatException NFE) {
			infoBox("Make sure items are entered correctly.", null, "Failed");
		}
	}
	
	public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }	
	
	/*
	String clear = "";
	addItemName.setText(clear);
	addItemID.setText(clear);
	addItemPrice.setText(clear);
	addItemAmount.setText(clear);
	addItemMan.setText(clear);
	addItemSupplier.setText(clear);
	addItemDesc.setText(clear);
	*/
}
