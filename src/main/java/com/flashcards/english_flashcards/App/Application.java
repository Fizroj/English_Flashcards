package com.flashcards.english_flashcards.App;

import com.flashcards.english_flashcards.Controllers.LanguagePromptController;
import com.flashcards.english_flashcards.Controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader;
        Scene scene;
        File dbFile = new File("databases/ashcards.db");
        if (!dbFile.exists()) {
            fxmlLoader = new FXMLLoader(Application.class.getResource("/com/flashcards/english_flashcards/language-prompt.fxml"));
            scene = new Scene(fxmlLoader.load());
            LanguagePromptController languagePromptController = fxmlLoader.getController();
            languagePromptController.setPrimaryStage(stage);
        } else {
            fxmlLoader = new FXMLLoader(Application.class.getResource("/com/flashcards/english_flashcards/main-view.fxml"));
            scene = new Scene(fxmlLoader.load());
            MainController mainController = fxmlLoader.getController();
            mainController.setPrimaryStage(stage);
        }
        stage.setTitle("Language Selection");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}