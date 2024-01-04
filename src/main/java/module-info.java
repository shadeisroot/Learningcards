module com.example.learningcards {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.learningcards to javafx.fxml;
    exports com.example.learningcards;
}