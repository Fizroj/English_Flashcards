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

public class CategorySettingsController {

    private Stage primaryStage;

    @FXML
    private Button addNewCategoryButton;

    @FXML
    private Button editCategoryButton;

    @FXML
    private Button deleteCategoryButton;

    @FXML
    private Button leaveSettingsButton;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {

    }

    @FXML
    private void onAddNewCategoryButton(ActionEvent event) {

    }

    @FXML
    private void onEditCategoryButton(ActionEvent event) {

    }

    @FXML
    private void onDeleteCategoryButton(ActionEvent event) {

    }

    @FXML
    private void onLeaveSettingsButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/flashcards/FXML/main-view.fxml"));
        Parent root = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.setPrimaryStage(primaryStage);
        primaryStage.setTitle(LanguageManager.getBundle().getString("flashcardsSettingsTitle"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
