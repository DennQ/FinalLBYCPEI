package ph.edu.dlsu.lbycpei.finalproj.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainLayoutController {

    @FXML private StackPane contentArea;
    @FXML private Label userLabel;

    private String currentUser;

    private Map<String, Parent> loadedViews = new HashMap<>();
    private Map<String, Object> loadedControllers = new HashMap<>();

    // Called by LoginController after successful login
    public void setUser(String username) {
        this.currentUser = username;
        userLabel.setText("Welcome, " + username + "!");
    }

    // Load Calendar view by default or when clicked
    @FXML
    private void showCalendar() throws IOException {
        System.out.println("Calendar button clicked!");
        loadViewWithCache("/ph/edu/dlsu/lbycpei/finalproj/calendar.fxml", "calendar");
    }

    // Load Tasks view
    @FXML
    private void showTasks() throws IOException {
        loadViewWithCache("/ph/edu/dlsu/lbycpei/finalproj/tasks.fxml", "tasks");
    }

    // Load Pomodoro view
    @FXML
    private void showPomodoro() throws IOException {
        loadViewWithCache("/ph/edu/dlsu/lbycpei/finalproj/pomodoro.fxml", "pomodoro");
    }

    private void loadViewWithCache(String fxmlPath, String viewKey) throws IOException {
        Parent view;

        if (loadedViews.containsKey(viewKey)) {
            view = loadedViews.get(viewKey);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            view = loader.load();

            loadedViews.put(viewKey, view);
            loadedControllers.put(viewKey, loader.getController());
        }

        contentArea.getChildren().setAll(view);
    }

    public Object getController(String viewKey) {
        return loadedControllers.get(viewKey);
    }

    @FXML
    private void handleLogout() throws IOException {
        loadedViews.clear();
        loadedControllers.clear();

        Stage stage = (Stage) contentArea.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/edu/dlsu/lbycpei/finalproj/login.fxml"));
        Parent loginRoot = loader.load();

        Scene scene = new Scene(loginRoot, 1440, 960);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }
}
