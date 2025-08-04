module ph.edu.dlsu.lbycpei.finalproj {
    requires javafx.controls;
    requires javafx.fxml;


    opens ph.edu.dlsu.lbycpei.finalproj to javafx.fxml;
    exports ph.edu.dlsu.lbycpei.finalproj;
    exports ph.edu.dlsu.lbycpei.finalproj.controller;
    opens ph.edu.dlsu.lbycpei.finalproj.controller to javafx.fxml;
}