package ph.edu.dlsu.lbycpei.finalproj.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private ObservableList<TasksModel> tasksList = FXCollections.observableArrayList();

    public void initialize() {
        // ✅ Setup table columns
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        dueColumn.setCellValueFactory(data -> data.getValue().dueDateProperty());
        priorityColumn.setCellValueFactory(data -> data.getValue().priorityProperty());
        doneColumn.setCellValueFactory(data -> data.getValue().doneProperty());

        // ✅ Bind the list to the table
        tasksTable.setItems(tasksList);

        // ✅ Populate priority options
        priorityBox.getItems().addAll("Low", "Medium", "High");
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
        tasksList.add(new TasksModel(false, name, dueDate, priority));

        // ✅ Clear input fields
        taskNameField.clear();
        dueDatePicker.setValue(null);
        priorityBox.setValue(null);
    }
}

