package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginController {
	@FXML
	private TextField txtUsername;
	
	@FXML
	private PasswordField txtPassword;
	
	@FXML
	private Button btnLogin;
	
	public void login(ActionEvent event) throws SQLException{
		Window owner = btnLogin.getScene().getWindow();
		
		if (txtUsername.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, owner, "Form Error",
					"Please enter your username.");
			return;
		}
		if (txtPassword.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, owner, "Form Error",
					"Please enter your password.");
			return;
		}
		
		String userID = txtUsername.getText();
		String password = txtPassword.getText();
		
		DBConnector jdbcDao = new DBConnector();
		boolean flag = jdbcDao.validate(userID, password);

        if (!flag) {
            infoBox("Please enter correct Username and Password", null, "Failed");
        } else {
        	Stage stage = (Stage) btnLogin.getScene().getWindow();
        	stage.close();
            try {
            	Stage main = new Stage();
            	Parent root = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
            	
            	Scene scene = new Scene(root, 1080, 624);
       	     	main.setTitle("Keystone");
       	     	main.setMinWidth(1080);
       	     	main.setMinHeight(664);
       	     	main.setScene(scene);
       	     	main.show();
            	
            } catch (IOException e) {
            	Logger logger = Logger.getLogger(getClass().getName());
            	logger.log(Level.SEVERE, "Failed to create new Window.", e);
            }
        }
    }
		
    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}