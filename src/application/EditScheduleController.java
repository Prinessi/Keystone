package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class EditScheduleController implements Initializable {
	@FXML
	private TextArea schedEditComments;
	@FXML
	private TableView<ScheduleModel> tblSchedEditSample;
	@FXML
	private TableColumn<ScheduleModel, String> schedEditShiftID;
	@FXML
	private TableColumn<ScheduleModel, String> schedEditStart;
	@FXML
	private TableColumn<ScheduleModel, String> schedEditEnd;
	@FXML
	private ChoiceBox<String> schedEditNewStart;
	@FXML
	private ChoiceBox<String> schedEditNewEnd;
	@FXML
	private TextField schedEditGetID;
	@FXML
	private Button btnSchedEditSubmit;
	@FXML
	private Button btnSchedEditDelete;
	
	ObservableList<ScheduleModel> slist = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			Connection con = DBConnector.getConnection();
			
			// Result sets to hold SQL Queries
			ResultSet rsSched = con.createStatement().executeQuery("select * from paysched");
			
			while(rsSched.next()) {
				slist.add(new ScheduleModel(rsSched.getString("empID"), rsSched.getString("day"), rsSched.getString("shiftStart"), 
						rsSched.getString("shiftEnd"), rsSched.getString("comments"), rsSched.getInt("shiftID"), rsSched.getDouble("wage"), rsSched.getDouble("shiftCost")));
				
				schedEditShiftID.setCellValueFactory(new PropertyValueFactory<>("shiftID"));
				schedEditStart.setCellValueFactory(new PropertyValueFactory<>("shiftStart"));
				schedEditEnd.setCellValueFactory(new PropertyValueFactory<>("shiftEnd"));
						
				tblSchedEditSample.setItems(slist);
			}		
			con.close();
		} catch (SQLException e) {
		e.printStackTrace();
		}
		
		// Fill Choice Boxes
		String am1 = "1:00", am2 = "2:00", am3 = "3:00", am4 = "4:00", am5 = "5:00", am6 = "6:00", am7 = "7:00", 
				am8 = "8:00", am9 = "9:00", am10 = "10:00", am11 = "11:00", am12 = "12:00", pm1 = "13:00", pm2 = "14:00", 
				pm3 = "15:00", pm4 = "16:00", pm5 = "17:00", pm6 = "18:00", pm7 ="19:00", pm8 = "20:00", pm9 = "21:00", 
				pm10 = "22:00", pm11 = "23:00", pm12 = "00:00";
		// Add the choices into an array so we can just import the array into both of the choice boxes, since they will show the same values.
		String[] choices = {am1, am2, am3, am4, am5, am6, am7, am8, am9, am10, am11, am12, pm1, pm2, pm3, pm4, pm5, pm6, pm7, pm8, pm9, pm10, pm11, pm12};
		
		// Add items to the choice boxes
		schedEditNewStart.getItems().addAll(choices);
		schedEditNewEnd.getItems().addAll(choices);
	}
	
	public void handleSchedEditSubmitButton(ActionEvent event) {
		String shiftID = schedEditGetID.getText();
		String startChoice = schedEditNewStart.getValue();
		String endChoice = schedEditNewEnd.getValue();
		String comments = schedEditComments.getText();
		
		// Must add additional :00 for seconds and get rid of the AM or PM at the end of the string for mySQL time stamp.
		// Get rid of extra values for startChoice and add :00
		if(startChoice.length()==7) {
			startChoice = startChoice.substring(0, 4) + ":00";
		} else if(startChoice.length()==8) {
			startChoice = startChoice.substring(0, 5) + ":00";
		}
		
		// Get rid of extra values for endChoice and add :00
		if(endChoice.length()==7) {
			endChoice = endChoice.substring(0, 4) + ":00";
		} else if(endChoice.length()==8) {
			endChoice = endChoice.substring(0, 5) + ":00";
		}
		
		String sqlStatement = "update schedule set shiftStart = '" + startChoice + "', shiftEnd = '" + endChoice + "', comments = '"+ comments + "' where shiftID = " + shiftID;	
		try {
			Connection con = DBConnector.getConnection();
			con.createStatement().executeUpdate(sqlStatement);
			infoBox("Shift Edited.", null, "Success");
			// Clear all the doodoo
		} catch(SQLException e) {
			infoBox("Make sure shift ID is valid and an Integer.", null, "Failed");
			e.printStackTrace();
		}		
	}
	
	public void handleSchedEditDeleteButton(ActionEvent event) {
		String shiftID = schedEditGetID.getText();
		String sqlStatement = "delete from schedule where shiftID = " + shiftID;
		try {
			Connection con = DBConnector.getConnection();
			con.createStatement().executeUpdate(sqlStatement);
			infoBox("Shift Deleted.", null, "Success");
			// Clear all the doodoo
		} catch(SQLException e) {
			infoBox("Make sure shift ID is valid and an Integer.", null, "Failed");
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
