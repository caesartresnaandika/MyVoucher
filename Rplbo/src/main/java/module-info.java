module com.example.rplbo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.rplbo to javafx.fxml;
    exports com.example.rplbo;
}