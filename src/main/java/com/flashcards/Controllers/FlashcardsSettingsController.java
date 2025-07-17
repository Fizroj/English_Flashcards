package com.flashcards.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FlashcardsSettingsController {

    private Stage primaryStage;

    private MainController mainController;

    @FXML
    private Button addNewFlashcardButton;

    @FXML
    private Button editFlashcardButton;

    @FXML
    private Button deleteFlashcardButton;

    @FXML
    private Button leaveSettingsButton;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void initialize() {

    }

    @FXML
    public void onAddNewFlashcardButton(ActionEvent event) {

    }

    @FXML
    public void onEditFlashcardButton(ActionEvent event) {

    }

    @FXML
    public void onDeleteFlashcardButton(ActionEvent event) {

    }

    @FXML
    public void onLeaveSettingsButton(ActionEvent event) {

    }
}
