package com.flashcards.Controllers;

import com.flashcards.App.*;
import com.flashcards.Controllers.*;
import com.flashcards.Database.*;
import com.flashcards.Language_and_Properties.*;
import com.flashcards.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

// Main Controller of the JavaFX project. The main page of the frontend and all submodes in general are administered by
// this very controller
public class MainController {

    private Flashcard mainFlashcard;

    private Category mainCategory;

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
        practiceButton.setText(LanguageManager.getBundle().getString("practiceButton"));
        flashcardSettingsButton.setText(LanguageManager.getBundle().getString("flashcardSettingsButton"));
        categorySettingsButton.setText(LanguageManager.getBundle().getString("categorySettingsButton"));
        settingsButton.setText(LanguageManager.getBundle().getString("settingsButton"));
        exitButton.setText(LanguageManager.getBundle().getString("exitButton"));
    }

    // TODO: Fill in the methods here
    @FXML
    public void onPracticeButton(ActionEvent event) throws IOException {
        // TODO: Query from database randomly from a given category

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("FXML/Flashcard_Settings_View/flashcard-settings-view.fxml"));
        Parent root = fxmlLoader.load();
        FlashcardsSettingsController controller = fxmlLoader.getController();
        controller.setPrimaryStage(mainStage);
        controller.setMainController(this);
        mainStage.setTitle(LanguageManager.getBundle().getString("flashcardsSettingsTitle"));
        mainStage.setScene(new Scene(root));
        mainStage.show();
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
