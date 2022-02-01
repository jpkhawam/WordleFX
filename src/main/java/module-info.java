module com.example.javafxwordle {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.javafxwordle to javafx.fxml;
    exports com.example.javafxwordle;
}