module com.example.learningcards {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    exports com.example.learningcards.GUI;
    opens com.example.learningcards.GUI to javafx.fxml;
    exports com.example.learningcards.Business;
    opens com.example.learningcards.Business to javafx.fxml;
    exports com.example.learningcards.Data;
    opens com.example.learningcards.Data to javafx.fxml;
}