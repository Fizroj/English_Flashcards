module com.flashcards.english_flashcards {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires jdk.compiler;

    opens com.flashcards.english_flashcards to javafx.fxml;
    exports com.flashcards.english_flashcards.App;
    opens com.flashcards.english_flashcards.App to javafx.fxml;
    exports com.flashcards.english_flashcards.Controllers;
    opens com.flashcards.english_flashcards.Controllers to javafx.fxml;
}