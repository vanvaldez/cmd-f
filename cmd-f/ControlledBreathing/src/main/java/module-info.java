module com.example.controlledbreathing {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.controlledbreathing to javafx.fxml;
    exports com.example.controlledbreathing;
}