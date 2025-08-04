package ph.edu.dlsu.lbycpei.finalproj.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;
import ph.edu.dlsu.lbycpei.finalproj.model.TasksModel;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarController {

    @FXML private GridPane calendarGrid;
    @FXML private Label monthLabel;
    @FXML private VBox taskDetailsPanel;
    @FXML private Label selectedDateLabel;
    @FXML private ScrollPane taskScrollPane;
    @FXML private VBox taskListContainer;

    //  Keep track of the currently displayed month
    private YearMonth currentMonth;
    private LocalDate selectedDate;

    private static ObservableList<TasksModel> tasksList = FXCollections.observableArrayList();

    public static ObservableList<TasksModel> getTasksList() {
        return tasksList;
    }

    public void initialize() {
        // Set current month
        currentMonth = YearMonth.now();

        // Initialize selected date to today
        selectedDate = LocalDate.now();

        //  Clear any existing constraints
        calendarGrid.getColumnConstraints().clear();
        calendarGrid.getRowConstraints().clear();

        //  Add 7 equal-width columns
        for (int i = 0; i < 7; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100.0 / 7);
            calendarGrid.getColumnConstraints().add(col);
        }

        //  Add 7 equal-height rows (6 for calendar + 1 for header)
        for (int i = 0; i < 7; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / 7);
            calendarGrid.getRowConstraints().add(row);
        }

        // Initialize task details panel
        if (taskDetailsPanel != null) {
            selectedDateLabel.setText("Selected: " + selectedDate.toString());
            updateTaskDisplay(selectedDate);
        }

        loadMonth(currentMonth);
    }

    public void loadMonth(YearMonth month) {
        currentMonth = month;
        monthLabel.setText(month.getMonth().toString() + " " + month.getYear());
        calendarGrid.getChildren().clear();

        LocalDate firstDay = month.atDay(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue(); // Monday=1, but we need Sunday=0

        //  Days of the week header
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < days.length; i++) {
            Label dayName = new Label(days[i]);
            dayName.setStyle("-fx-font-weight: bold; -fx-alignment: center;");

            StackPane headerBox = new StackPane(dayName);
            headerBox.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: gray;");
            headerBox.setPrefSize(100, 40);

            calendarGrid.add(headerBox, i, 0); //  put in first row (row 0)
        }

        for (int day = 1; day <= month.lengthOfMonth(); day++) {
            LocalDate date = month.atDay(day);

            Label dayLabel = new Label(String.valueOf(day));
            dayLabel.setStyle("-fx-alignment: center; -fx-font-size: 14;");

            VBox dayContent = new VBox(2);
            dayContent.getChildren().add(dayLabel);

            List<TasksModel> tasksOnDate = getTasksForDate(date);
            if (!tasksOnDate.isEmpty()) {
                Label taskIndicator = new Label("● " + tasksOnDate.size());
                taskIndicator.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 10; -fx-alignment: center;");
                dayContent.getChildren().add(taskIndicator);
            }

            StackPane dayBox = new StackPane(dayContent);

            String baseStyle = "-fx-border-color: lightgray; -fx-cursor: hand;";
            if (date.equals(LocalDate.now())) {
                baseStyle += " -fx-background-color: #3498db; -fx-text-fill: white;";
            } else if (date.equals(selectedDate)) {
                baseStyle += " -fx-background-color: #2ecc71; -fx-text-fill: white;";
            } else if (!tasksOnDate.isEmpty()) {
                baseStyle += " -fx-background-color: #fff3cd;";
            }

            dayBox.setStyle(baseStyle);
            dayBox.setPrefSize(100, 80);

            final LocalDate clickedDate = date;
            final String finalBaseStyle = baseStyle; // Make baseStyle effectively final
            dayBox.setOnMouseClicked(event -> {
                selectedDate = clickedDate;
                updateTaskDisplay(clickedDate);
                loadMonth(currentMonth); // Refresh to update highlighting
            });

            dayBox.setOnMouseEntered(event -> {
                if (!clickedDate.equals(selectedDate) && !clickedDate.equals(LocalDate.now())) {
                    dayBox.setStyle(finalBaseStyle + " -fx-background-color: #f8f9fa;");
                }
            });

            dayBox.setOnMouseExited(event -> {
                dayBox.setStyle(finalBaseStyle);
            });

            //  Correct column & row calculation (adjust for Sunday start)
            int adjustedDayOfWeek = (dayOfWeek == 7) ? 0 : dayOfWeek; // Convert Monday=1 to Sunday=0
            int col = (day + adjustedDayOfWeek - 1) % 7;
            int row = ((day + adjustedDayOfWeek - 1) / 7) + 1;

            calendarGrid.add(dayBox, col, row);
        }
    }

    private List<TasksModel> getTasksForDate(LocalDate date) {
        return tasksList.stream()
                .filter(task -> task.dueDateProperty().get().equals(date))
                .collect(Collectors.toList());
    }

    private void updateTaskDisplay(LocalDate date) {
        if (selectedDateLabel != null) {
            selectedDateLabel.setText("Tasks for: " + date.toString());
        }

        if (taskListContainer != null) {
            taskListContainer.getChildren().clear();

            List<TasksModel> tasksOnDate = getTasksForDate(date);

            if (tasksOnDate.isEmpty()) {
                Label noTasksLabel = new Label("No tasks scheduled for this date");
                noTasksLabel.setStyle("-fx-text-fill: gray; -fx-font-style: italic;");
                taskListContainer.getChildren().add(noTasksLabel);
            } else {
                for (TasksModel task : tasksOnDate) {
                    VBox taskBox = createTaskDisplayBox(task);
                    taskListContainer.getChildren().add(taskBox);
                }
            }
        }
    }

    private VBox createTaskDisplayBox(TasksModel task) {
        VBox taskBox = new VBox(5);
        taskBox.setPadding(new Insets(10));

        Label nameLabel = new Label(task.nameProperty().get());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        Label priorityLabel = new Label("Priority: " + task.priorityProperty().get());
        String priorityColor = getPriorityColor(task.priorityProperty().get());
        priorityLabel.setStyle("-fx-text-fill: " + priorityColor + ";");

        Label statusLabel = new Label(task.doneProperty().get() ? "✓ Completed" : "○ Pending");
        statusLabel.setStyle("-fx-text-fill: " + (task.doneProperty().get() ? "green" : "orange") + ";");

        taskBox.getChildren().addAll(nameLabel, priorityLabel, statusLabel);

        String backgroundColor = task.doneProperty().get() ? "#d4edda" : "#fff3cd";
        taskBox.setStyle("-fx-background-color: " + backgroundColor + "; -fx-border-color: #dee2e6; -fx-border-radius: 5; -fx-background-radius: 5;");

        return taskBox;
    }

    private String getPriorityColor(String priority) {
        switch (priority.toLowerCase()) {
            case "high": return "#e74c3c";
            case "medium": return "#f39c12";
            case "low": return "#27ae60";
            default: return "#6c757d";
        }
    }

    @FXML
    private void prevMonth() {
        loadMonth(currentMonth.minusMonths(1));
    }

    @FXML
    private void nextMonth() {
        loadMonth(currentMonth.plusMonths(1));
    }

    @FXML
    private void goToToday() {
        selectedDate = LocalDate.now();
        currentMonth = YearMonth.now();
        loadMonth(currentMonth);
        updateTaskDisplay(selectedDate);
    }
}
