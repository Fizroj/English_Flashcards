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
    private Button addFlashcardToCategoryButton;

    @FXML
    private Button deleteFlashcardFromCategoryButton;

    @FXML
    private Button leaveSettingsButton;

    public static void backToCategorySettings(Stage primaryStage) throws IOException {
        // TODO: Fix the titles of each scene to be derived from ResourceBundle
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/flashcards/FXML/Category_Settings_View/category-settings-view.fxml"));
        Parent root = fxmlLoader.load();
        CategorySettingsController controller = fxmlLoader.getController();
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
    private void onAddNewCategoryButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/flashcards/FXML/Category_Settings_View/add-new-category-view.fxml"));
        Parent root = fxmlLoader.load();
        AddNewCategoryController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setTitle(LanguageManager.getBundle().getString("flashcardsSettingsTitle"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    private void onEditCategoryButton(ActionEvent event) {

    }

    @FXML
    private void onDeleteCategoryButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/flashcards/FXML/Category_Settings_View/delete-category-view.fxml"));
        Parent root = fxmlLoader.load();
        DeleteCategoryController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setTitle(LanguageManager.getBundle().getString("flashcardsSettingsTitle"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    public void onAddFlashcardToCategoryButton(ActionEvent event) {

    }

    @FXML
    public void onDeleteFlashcardFromCategoryButton(ActionEvent event) {

    }

    @FXML
    private void onLeaveSettingsButton(ActionEvent event) throws IOException {
        MainController.backToMain(primaryStage);
    }
}
