module com.miracosta.cs210.cs210 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.miracosta.cs210.cs210 to javafx.fxml;
    exports com.miracosta.cs210.cs210;
}