package ph.edu.dlsu.lbycpei.finalproj.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import ph.edu.dlsu.lbycpei.finalproj.model.TasksModel;

import java.time.LocalDate;

public class TasksController {

    @FXML
    private TableView<TasksModel> tasksTable;
    @FXML private TableColumn<TasksModel, Boolean> doneColumn;
    @FXML private TableColumn<TasksModel, String> nameColumn;
    @FXML private TableColumn<TasksModel, LocalDate> dueColumn;
    @FXML private TableColumn<TasksModel, String> priorityColumn;

    @FXML private TextField taskNameField;
    @FXML private DatePicker dueDatePicker;
    @FXML private ComboBox<String> priorityBox;

    public void initialize() {
        // ✅ Setup table columns
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        dueColumn.setCellValueFactory(data -> data.getValue().dueDateProperty());
        priorityColumn.setCellValueFactory(data -> data.getValue().priorityProperty());

        doneColumn.setCellValueFactory(data -> data.getValue().doneProperty());
        doneColumn.setCellFactory(CheckBoxTableCell.forTableColumn(doneColumn));

        tasksTable.setEditable(true);

        tasksTable.setItems(CalendarController.getTasksList());

        priorityBox.getItems().addAll("Low", "Medium", "High");

        priorityBox.setValue("Medium");

        tasksTable.setRowFactory(tv -> {
            TableRow<TasksModel> row = new TableRow<>();
            row.itemProperty().addListener((obs, oldTask, newTask) -> {
                if (newTask != null) {
                    newTask.doneProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue) {
                            row.setStyle("-fx-background-color: #d4edda; -fx-text-fill: #155724;");
                        } else {
                            row.setStyle("");
                        }
                    });

                    if (newTask.doneProperty().get()) {
                        row.setStyle("-fx-background-color: #d4edda; -fx-text-fill: #155724;");
                    } else {
                        row.setStyle("");
                    }
                }
            });
            return row;
        });
    }

    @FXML
    private void addTask() {
        String name = taskNameField.getText();
        LocalDate dueDate = dueDatePicker.getValue();
        String priority = priorityBox.getValue();

        if (name.isEmpty() || dueDate == null || priority == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill all fields!");
            alert.show();
            return;
        }

        // ✅ Add new task to the list
        TasksModel newTask = new TasksModel(false, name, dueDate, priority);
        CalendarController.getTasksList().add(newTask);

        // ✅ Clear input fields
        taskNameField.clear();
        dueDatePicker.setValue(null);
        priorityBox.setValue("Medium");
    }

    @FXML
    private void deleteTask() {
        TasksModel selectedTask = tasksTable.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            CalendarController.getTasksList().remove(selectedTask);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a task to delete!");
            alert.show();
        }
    }
}
