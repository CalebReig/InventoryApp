module com.calebreigada {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.calebreigada to javafx.fxml;
    exports com.calebreigada;
}