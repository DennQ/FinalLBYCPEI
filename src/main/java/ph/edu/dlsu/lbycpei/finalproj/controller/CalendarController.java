package ph.edu.dlsu.lbycpei.finalproj.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CalendarController {

    @FXML private DatePicker datePicker;
    @FXML private ListView<String> taskList;

    @FXML
    public void initialize() {
        // Default date = today
        datePicker.setValue(java.time.LocalDate.now());

        // Example tasks
        taskList.getItems().addAll(
                "Math homework - Due 5PM",
                "Physics review session - 7PM"
        );

        // Update tasks when date changes (for now just clear)
        datePicker.valueProperty().addListener((obs, oldDate, newDate) -> {
            taskList.getItems().clear();
            taskList.getItems().add("No tasks yet for " + newDate);
        });
    }

    @FXML
    private void handleAddTask() {
        taskList.getItems().add("New Task added on " + datePicker.getValue());
    }
}
