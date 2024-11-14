module assignment3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    

    opens assignment3 to javafx.fxml;
    exports assignment3;
}
