package com.flashcards.App;

import com.flashcards.Controllers.*;
import com.flashcards.Language_and_Properties.*;
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
        File dbFile = new File("databases/flashcards.db");
        if (!dbFile.exists()) {
            fxmlLoader = new FXMLLoader(Application.class.getResource("/com/flashcards/FXML/First_Time_User_View/language-prompt.fxml"));
            scene = new Scene(fxmlLoader.load());
            LanguagePromptController languagePromptController = fxmlLoader.getController();
            languagePromptController.setPrimaryStage(stage);
            stage.setTitle("Language Selection");
        } else {
            LanguageManager.readLanguage();
            fxmlLoader = new FXMLLoader(Application.class.getResource("/com/flashcards/FXML/main-view.fxml"));
            scene = new Scene(fxmlLoader.load());
            MainController mainController = fxmlLoader.getController();
            mainController.setPrimaryStage(stage);
            stage.setTitle(LanguageManager.getBundle().getString("windowTitle"));
        }

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}