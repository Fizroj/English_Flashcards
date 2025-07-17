module com.flashcards.english_flashcards {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires jdk.compiler;

    exports com.flashcards.App;
    opens com.flashcards.App to javafx.fxml;
    exports com.flashcards.Controllers;
    opens com.flashcards.Controllers to javafx.fxml;
}