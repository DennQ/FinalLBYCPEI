package ph.edu.dlsu.lbycpei.finalproj.model;


import javafx.beans.property.*;

import java.time.LocalDate;

public class TasksModel {
    private final SimpleBooleanProperty done;
    private final SimpleStringProperty name;
    private final ObjectProperty<LocalDate> dueDate;
    private final SimpleStringProperty priority;

    public TasksModel(boolean done, String name, LocalDate dueDate, String priority) {
        this.done = new SimpleBooleanProperty(done);
        this.name = new SimpleStringProperty(name);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.priority = new SimpleStringProperty(priority);
    }

    // Getters for TableView
    public BooleanProperty doneProperty() { return done; }
    public StringProperty nameProperty() { return name; }
    public ObjectProperty<LocalDate> dueDateProperty() { return dueDate; }
    public StringProperty priorityProperty() { return priority; }
}
