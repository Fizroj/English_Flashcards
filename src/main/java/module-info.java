module com.flashcards.english_flashcards {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.flashcards.english_flashcards to javafx.fxml;
    exports com.flashcards.english_flashcards;
}