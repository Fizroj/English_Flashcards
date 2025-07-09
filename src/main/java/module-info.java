module com.flashcards.english_flashcards {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;

    opens com.flashcards.english_flashcards to javafx.fxml;
    exports com.flashcards.english_flashcards;
}