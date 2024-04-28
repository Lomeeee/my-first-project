module com.example.lomelomejavatest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lomelomejavatest to javafx.fxml;
    exports com.example.lomelomejavatest;
}