package ph.edu.dlsu.lbycpei.finalproj.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

import java.time.LocalDate;
import java.time.YearMonth;

public class CalendarController {

    @FXML private GridPane calendarGrid;
    @FXML private Label monthLabel;

    //  Keep track of the currently displayed month
    private YearMonth currentMonth;

    public void initialize() {
        // Set current month
        currentMonth = YearMonth.now();

        //  Clear any existing constraints
        calendarGrid.getColumnConstraints().clear();
        calendarGrid.getRowConstraints().clear();

        //  Add 7 equal-width columns
        for (int i = 0; i < 7; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100.0 / 7);
            calendarGrid.getColumnConstraints().add(col);
        }

        //  Add 6 equal-height rows
        for (int i = 0; i < 7; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / 7);
            calendarGrid.getRowConstraints().add(row);
        }

        loadMonth(currentMonth);
    }


    public void loadMonth(YearMonth month) {
        currentMonth = month;
        monthLabel.setText(month.getMonth().toString() + " " + month.getYear());
        calendarGrid.getChildren().clear();

        LocalDate firstDay = month.atDay(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue(); // Monday=1

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
            Label dayLabel = new Label(String.valueOf(day));
            dayLabel.setStyle("-fx-border-color: lightgray; -fx-alignment: center; -fx-font-size: 14;");

            StackPane dayBox = new StackPane(dayLabel);
            dayBox.setStyle("-fx-border-color: lightgray;");
            dayBox.setPrefSize(100, 80);

            //  Correct column & row calculation
            int col = (day + dayOfWeek - 1) % 7;
            int row = ((day + dayOfWeek - 1) / 7) + 1; // start at 0 if no header

            calendarGrid.add(dayBox, col, row);
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
}
