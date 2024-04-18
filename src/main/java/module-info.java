module com.example.bestwish {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.bestwish to javafx.fxml;
    exports com.example.bestwish;
}