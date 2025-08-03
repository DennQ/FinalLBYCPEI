package ph.edu.dlsu.lbycpei.finalproj.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainLayoutController {

    @FXML private StackPane contentArea;
    @FXML private Label userLabel;

    private String currentUser;

    // Called by LoginController after successful login
    public void setUser(String username) {
        this.currentUser = username;
        userLabel.setText("Welcome, " + username + "!");
    }

    // Load Calendar view by default or when clicked
    @FXML
    private void showCalendar() throws IOException {
        System.out.println("Calendar button clicked!");
        loadView("/ph/edu/dlsu/lbycpei/finalproj/calendar.fxml");
    }

    // Load Tasks view
    @FXML
    private void showTasks() throws IOException {
        loadView("/ph/edu/dlsu/lbycpei/finalproj/tasks.fxml");
    }

    // Load Settings view
    @FXML
    private void showPomodoro() throws IOException {
        loadView("/ph/edu/dlsu/lbycpei/finalproj/pomodoro.fxml");
    }

    // Reusable method to load FXML into contentArea
    private void loadView(String fxmlPath) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
        contentArea.getChildren().setAll(view);
    }

    //
    @FXML
    private void handleLogout() throws IOException {
        Stage stage = (Stage) contentArea.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/finalproj/view/Login.fxml"));
        stage.setScene(new Scene(loader.load(), 1440, 960));
        stage.show();
    }
}
