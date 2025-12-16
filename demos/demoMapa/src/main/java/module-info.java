module com.example.demomapa {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demomapa to javafx.fxml;
    exports com.example.demomapa;
}