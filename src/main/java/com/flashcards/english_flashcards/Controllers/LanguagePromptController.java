package com.flashcards.english_flashcards.Controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Locale;

import com.flashcards.english_flashcards.Language.LanguageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

// A controller meant to appear only once. It's for language setup of the app, on later
// launches Main Controller takes over first
public class LanguagePromptController {

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private Button englishButton;

    @FXML
    private Button polishButton;

    public void initialize(){
        LanguageManager.setLocale(Locale.US);
    }
    @FXML
    public void onEnglishButton(ActionEvent event) {
        LanguageManager.setBundle(new Locale.Builder().setLanguage("en").build());


    }
    @FXML
    public void onPolishButton(ActionEvent event) {
        LanguageManager.setLocale(new Locale.Builder().setLanguage("pl").build());
        try (
                FileWriter fw = new FileWriter("config.properties");
                ){
            fw.write("language=pl");

        } catch (IOException e){
            e.printStackTrace();
        }

    }
}