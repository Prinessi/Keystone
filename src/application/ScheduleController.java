package application;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class ScheduleController implements Initializable {
	@FXML
	private TableView<ModelTable> tblEmployeeSample;
	@FXML
	private TableColumn<ModelTable, Integer> colSampleID;
	@FXML
	private TableColumn<ModelTable, String> colSampleFirst;
	@FXML
	private TableColumn<ModelTable, String> colSampleLast;
	@FXML
	private TextField newSchedID;
	@FXML
	private DatePicker newSchedDate;
	@FXML
	private ChoiceBox<String> choiceStart;
	@FXML
	private ChoiceBox<String> choiceEnd;
	@FXML
	private Button btnSchedSubmit;
	
	ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
	// This was an absolute waste of time but I cannot be asked to change it back to how it was so we will just deal with it. /s
	String am1 = "1:00", am2 = "2:00", am3 = "3:00", am4 = "4:00", am5 = "5:00", am6 = "6:00", am7 = "7:00", 
			am8 = "8:00", am9 = "9:00", am10 = "10:00", am11 = "11:00", am12 = "12:00", pm1 = "13:00", pm2 = "14:00", 
			pm3 = "15:00", pm4 = "16:00", pm5 = "17:00", pm6 = "18:00", pm7 ="19:00", pm8 = "20:00", pm9 = "21:00", 
			pm10 = "22:00", pm11 = "23:00", pm12 = "00:00";
	// Add the choices into an array so we can just import the array into both of the choice boxes, since they will show the same values.
	String[] choices = {am1, am2, am3, am4, am5, am6, am7, am8, am9, am10, am11, am12, pm1, pm2, pm3, pm4, pm5, pm6, pm7, pm8, pm9, pm10, pm11, pm12};
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Add items to the employee list
		try {
			Connection con = DBConnector.getConnection();
			
			ResultSet rsEmp = con.createStatement().executeQuery("select * from employee");
			
			while (rsEmp.next()) {
				oblist.add(new ModelTable(rsEmp.getInt("empID"),rsEmp.getString("fName"), 
						rsEmp.getString("lName"), rsEmp.getInt("areaCode"), rsEmp.getInt("phoneNumber"), rsEmp.getString("address"), 
						rsEmp.getBoolean("manager"), rsEmp.getInt("wage")));
				
				colSampleID.setCellValueFactory(new PropertyValueFactory<>("empID"));
				colSampleFirst.setCellValueFactory(new PropertyValueFactory<>("fName"));
				colSampleLast.setCellValueFactory(new PropertyValueFactory<>("lName"));
				
				tblEmployeeSample.setItems(oblist);
			}
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Add items to the choice boxes
		choiceStart.getItems().addAll(choices);
		choiceEnd.getItems().addAll(choices);
	}
	
	// Submit the new schedule
	public void handleShedSubmitButton(ActionEvent event) {
		// SQL Statement needs to be empID, day, 'shiftStart', 'shiftEnd'
		// DatePicker prints out in YYYY-MM-DD and MySQL handles dates as YYYY-MM-DD 
		String emp = newSchedID.getText();
		LocalDate date = newSchedDate.getValue(); // Can be read as string
		String startChoice = choiceStart.getValue();
		String endChoice = choiceEnd.getValue();
		
		// Must add additional :00 for seconds and get rid of the AM or PM at the end of the string for mySQL time stamp.
		// Get rid of extra values for startChoice and add :00
		if(startChoice.length()==4) {
			startChoice = startChoice.substring(0, 4) + ":00";
		} else if(startChoice.length()==5) {
			startChoice = startChoice.substring(0, 5) + ":00";
		}
		
		// Get rid of extra values for endChoice and add :00
		if(endChoice.length()==4) {
			endChoice = endChoice.substring(0, 4) + ":00";
		} else if(endChoice.length()==5) {
			endChoice = endChoice.substring(0, 5) + ":00";
		}

		String sqlStatement = "INSERT INTO schedule (empID, day, shiftStart, shiftEnd) VALUES(" + emp + ", '" + date + "', '" + startChoice + "', '" + endChoice + "')";	
		try {
			Connection con = DBConnector.getConnection();
			con.createStatement().executeUpdate(sqlStatement);
			infoBox("Shift added.", null, "Success");
			// Clear all the doodoo
		} catch(SQLException e) {
			infoBox("Make sure ID is valid.", null, "Failed");
			e.printStackTrace();
			System.out.println(sqlStatement);
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
