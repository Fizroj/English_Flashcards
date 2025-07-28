package com.flashcards.Controllers;

import com.flashcards.Database.InsertManager;
import com.flashcards.Database.QueryManager;
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

public class AddNewCategoryController {

    private Stage primaryStage;

    private Category newCategory = new Category();

    @FXML
    private TextField categoryNameTextField;

    @FXML
    private Button saveAndLeaveButton;

    @FXML
    private Button leaveWithoutSavingButton;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize() {
        categoryNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {newCategory.setName(newValue);});
    }

    @FXML
    public void onCategoryNameTextField(ActionEvent event) {

    }

    @FXML
    public void onSaveAndLeaveButton(ActionEvent event) throws IOException {
        InsertManager.addCategory(newCategory);
        MainController.setNumberOfCategories(QueryManager.countAllCategories());

        CategorySettingsController.backToCategorySettings(primaryStage);
    }

    @FXML
    public void onLeaveWithoutSavingButton(ActionEvent event) throws IOException {
        CategorySettingsController.backToCategorySettings(primaryStage);
    }
}
