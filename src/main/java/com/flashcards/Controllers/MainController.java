package com.flashcards.Controllers;

import com.flashcards.App.*;
import com.flashcards.Controllers.*;
import com.flashcards.Database.*;
import com.flashcards.Language_and_Properties.*;
import com.flashcards.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

// Main Controller of the JavaFX project. The main page of the frontend and all submodes in general are administered by
// this very controller
public class MainController {

    private Stage mainStage;

    @FXML
    private Label welcomeTitle;

    @FXML
    private Label categoryName;

    @FXML
    private Label categoryLabel;

    @FXML
    private Button practiceButton;

    @FXML
    private Button flashcardSettingsButton;

    @FXML
    private Button categorySettingsButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button exitButton;

    public void setPrimaryStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
    public void initialize(){
        // it's for setting language of UI
        welcomeTitle.setText(LanguageManager.getBundle().getString("welcomeTitle"));
        categoryLabel.setText(LanguageManager.getBundle().getString("categoryLabel"));
        String categoryNameString = PropertiesManager.getConfigProperty("currentCategory");
        categoryName.setText(categoryNameString);
    }

    // TODO: Fill in the methods here
    @FXML
    public void onPracticeButton(ActionEvent event) {
        // TODO: Query from database randomly from a given category
    }

    @FXML
    public void onFlashcardSettingsButton(ActionEvent event) {
        // TODO: Add, edit (avaible in future), delete flashcards in
        //  a given , currently chosen category
    }

    @FXML
    public void onCategorySettingsButton(ActionEvent event) {
        // TODO: Add, edit (avaible in future), delete categories
        //  (there must be at least one category avaible)
    }

    @FXML
    public void onSettingsButton(ActionEvent event) {
        // TODO: Language, font-size, some other options type-shit
        //  (oh, explicit vocabulary mb)
    }

    @FXML
    public void onExitButton(ActionEvent event) {
        // they're leaving let's GOOOOOOOOOOO uuuhhhhh I mean...
        // Who am I kidding... I'm going insane with this code
        // But it's worth it :`)
        mainStage.close();
    }
}
