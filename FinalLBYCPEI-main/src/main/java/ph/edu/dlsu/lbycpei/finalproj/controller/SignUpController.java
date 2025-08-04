package ph.edu.dlsu.lbycpei.finalproj.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SignUpController {

    // Navigate back to Login Screen
    @FXML
    private void handleBackToLogin(ActionEvent event) {
        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/ph/edu/dlsu/lbycpei/finalproj/login.fxml"));
            Scene scene = new Scene(loginRoot);

            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handle Sign Up Button (for later logic)
    @FXML
    private void handleSignUp(ActionEvent event) {
        // TODO: Add user registration logic (save to DB or file)
        System.out.println("Sign Up button clicked!");

        // After successful signup, go back to login
        handleBackToLogin(event);
    }
}
