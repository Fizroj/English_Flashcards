package com.flashcards.english_flashcards.Controllers;

import java.util.ResourceBundle;
import java.util.Locale;

import com.flashcards.english_flashcards.App.Application;
import com.flashcards.english_flashcards.Language.LanguageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.stage.Stage;

// Main Controller of the JavaFX project. The main page of the frontend and all submodes in general are administered by
// this very controller
public class MainController {

    @FXML
    private Label welcomeTitle;

    private Stage mainStage;
    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
    public void initialize(){
        LanguageManager.setLocale(Locale.US);
        LanguageManager.readLanguage();
        // it's for setting language of UI
        welcomeTitle.setText(LanguageManager.getBundle().getString("welcomeTitle"));
    }
}
