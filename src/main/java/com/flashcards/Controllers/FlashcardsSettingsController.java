package com.flashcards.Controllers;

import com.flashcards.App.Application;
import com.flashcards.Language_and_Properties.LanguageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class FlashcardsSettingsController {

    private Stage primaryStage;

    @FXML
    private Button addNewFlashcardButton;

    @FXML
    private Button editFlashcardButton;

    @FXML
    private Button deleteFlashcardButton;

    @FXML
    private Button leaveSettingsButton;

    public static void backToFlashcardsSettings(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/flashcards/FXML/Flashcards_Settings_View/flashcards-settings-view.fxml"));
        Parent root = fxmlLoader.load();
        FlashcardsSettingsController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setTitle(LanguageManager.getBundle().getString("flashcardsSettingsTitle"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {

    }

    @FXML
    public void onAddNewFlashcardButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/flashcards/FXML/Flashcards_Settings_View/add-new-flashcard-view.fxml"));
        Parent root = fxmlLoader.load();
        AddNewFlashcardController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setTitle(LanguageManager.getBundle().getString("flashcardsSettingsTitle"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    public void onEditFlashcardButton(ActionEvent event) {

    }

    @FXML
    public void onDeleteFlashcardButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/flashcards/FXML/Flashcards_Settings_View/delete-flashcard-view.fxml"));
        Parent root = fxmlLoader.load();
        DeleteFlashcardController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setTitle(LanguageManager.getBundle().getString("flashcardsSettingsTitle"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    public void onLeaveSettingsButton(ActionEvent event) throws IOException {
        MainController.backToMain(primaryStage);
    }
}
