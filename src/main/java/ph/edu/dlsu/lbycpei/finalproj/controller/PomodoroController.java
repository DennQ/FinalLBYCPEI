package ph.edu.dlsu.lbycpei.finalproj.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class PomodoroController {

    @FXML private Label timerLabel;

    private int workTime = 25 * 60; // 25 minutes in seconds
    private int remainingTime = workTime;
    private Timeline timeline;
    private boolean running = false;

    @FXML
    public void initialize() {
        updateTimerLabel();
    }

    @FXML
    private void handleStart() {
        if (timeline == null) {
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> tick()));
            timeline.setCycleCount(Timeline.INDEFINITE);
        }
        if (!running) {
            timeline.play();
            running = true;
        }
    }

    @FXML
    private void handlePause() {
        if (timeline != null) {
            timeline.pause();
            running = false;
        }
    }

    @FXML
    private void handleReset() {
        if (timeline != null) {
            timeline.stop();
        }
        running = false;
        remainingTime = workTime;
        updateTimerLabel();
    }

    private void tick() {
        if (remainingTime > 0) {
            remainingTime--;
            updateTimerLabel();
        } else {
            timeline.stop();
            running = false;
            timerLabel.setText("Time's up!");
        }
    }

    private void updateTimerLabel() {
        int minutes = remainingTime / 60;
        int seconds = remainingTime % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }
}
