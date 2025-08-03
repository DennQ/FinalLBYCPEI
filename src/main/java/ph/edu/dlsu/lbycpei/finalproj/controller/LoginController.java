package ph.edu.dlsu.lbycpei.finalproj.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // TEMPORARY: Hardcoded credentials
        if ("s".equals(username) && "1".equals(password)) {
            try {
                // Load the MainLayout
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/finalproj/main-layout.fxml"));
                System.out.println("FXML URL: " + getClass().getResource("/ph/edu/dlsu/lbycpei/finalproj/main-layout.fxml"));
                Parent root = loader.load();
//                Scene scene = new Scene(root, 1440, 960);   //
//                stage.setScene(scene);
//                stage.show();

                // Pass username to MainController
                MainLayoutController mainController = loader.getController();
                mainController.setUser(username);

                // Switch to MainLayout scene
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                statusLabel.setText("Error loading main dashboard.");
            }
        } else {
            statusLabel.setText("Invalid username or password.");
        }
    }

}
