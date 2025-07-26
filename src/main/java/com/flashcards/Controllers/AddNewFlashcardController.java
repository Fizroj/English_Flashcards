package com.flashcards.Controllers;

import com.flashcards.App.Application;
import com.flashcards.Database.InsertManager;
import com.flashcards.Database.QueryManager;
import com.flashcards.Language_and_Properties.LanguageManager;
import com.flashcards.Language_and_Properties.PropertiesManager;
import com.flashcards.Model.Category;
import com.flashcards.Model.Flashcard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AddNewFlashcardController {

    private Stage primaryStage;

    private Flashcard newFlashcard = new Flashcard();

    @FXML
    private TextField phraseTextField;

    @FXML
    private Button addAnotherTranslationButton;

    @FXML
    private VBox translationsVBox;

    @FXML
    private TextField firstTranslationTextField;

    @FXML
    private Button saveAndLeaveButton;

    @FXML
    private Button leaveWithoutSavingButton;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void addTranslationTextField(){
        TextField newTranslationTextField = new TextField();
        //newTranslationTextField.setPromptText("Enter Translation");
        newTranslationTextField.textProperty().addListener((observable, oldValue, newValue) -> {newFlashcard.deleteTranslation(oldValue); newFlashcard.addTranslation(newValue);});

        HBox wrapperHBox = new HBox(newTranslationTextField);
        wrapperHBox.setAlignment(Pos.CENTER);

        translationsVBox.getChildren().add(translationsVBox.getChildren().size(), wrapperHBox);
    }

    public void initialize() {
        addAnotherTranslationButton.setOnAction(event ->  {addTranslationTextField();});

        phraseTextField.textProperty().addListener((observable, oldValue, newValue) -> {newFlashcard.setPhrase(newValue);});
        firstTranslationTextField.textProperty().addListener((observable, oldValue, newValue) -> {newFlashcard.deleteTranslation(oldValue); newFlashcard.addTranslation(newValue);});
    }

    @FXML
    public void onPhraseTextField(ActionEvent event) {

    }

    @FXML
    public void onAddAnotherTranslationButton(ActionEvent event) {

    }

    @FXML
    public void onFirstTranslationTextField(ActionEvent event) {

    }

    @FXML
    public void onSaveAndLeaveButton(ActionEvent event) throws IOException {
        InsertManager.addSingleFlashcard(newFlashcard);
        String currentCategory = PropertiesManager.getConfigProperty("currentCategory");
        Category category = QueryManager.queryCategory(currentCategory);
        InsertManager.addFlashcardToCategory(newFlashcard, category);
        MainController.setNumberOfFlashcards(QueryManager.countAllFlashcards());

        FlashcardsSettingsController.backToFlashcardsSettings(primaryStage);
    }

    @FXML
    public void onLeaveWithoutSavingButton(ActionEvent event) throws IOException {
        FlashcardsSettingsController.backToFlashcardsSettings(primaryStage);
    }
}