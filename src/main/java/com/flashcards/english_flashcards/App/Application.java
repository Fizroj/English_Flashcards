package com.flashcards.english_flashcards.App;

import com.flashcards.english_flashcards.Controllers.LanguagePromptController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File dbFile = new File("databases/flashcards.db");
        if (!dbFile.exists()) {
            fxmlLoader = new FXMLLoader(Application.class.getResource("/com/flashcards/english_flashcards/language-prompt.fxml"));
        } else {
            fxmlLoader = new FXMLLoader(Application.class.getResource("/com/flashcards/english_flashcards/main-view.fxml"));
        }
        Scene scene = new Scene(fxmlLoader.load());
        LanguagePromptController languagePromptController = fxmlLoader.getController();
        languagePromptController.setPrimaryStage(stage);
        stage.setTitle("Language Selection");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}