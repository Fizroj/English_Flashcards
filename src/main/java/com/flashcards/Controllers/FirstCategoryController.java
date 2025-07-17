package com.flashcards.Controllers;

import com.flashcards.App.Application;
import com.flashcards.Database.*;
import com.flashcards.Language_and_Properties.*;
import com.flashcards.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstCategoryController {

    private Stage primaryStage;

    private String firstCategoryName;

    private MainController mainController;

    @FXML
    private TextField nameOfTheCategoryTextField;

    @FXML
    private Button submitButton;

    public void setPrimaryStage(Stage stage){
        primaryStage = stage;
    }
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }
    public void initialize(){
        DatabaseManager.connect();
        nameOfTheCategoryTextField.textProperty().addListener((observable, oldValue, newValue) -> {firstCategoryName = newValue;});
    }

    @FXML
    public void onSubmitButton(ActionEvent event) throws IOException {
        Category firstCategory = new Category(firstCategoryName);
        InsertManager.addCategory(firstCategory);
        PropertiesManager.changeConfigProperty("currentCategory", firstCategoryName);

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/flashcards/FXML/main-view.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        MainController mainController = fxmlLoader.getController();
        setMainController(mainController);
        mainController.setPrimaryStage(stage);
        stage.setTitle(LanguageManager.getBundle().getString("windowTitle"));
        stage.setScene(new Scene(root));
        stage.show();
        mainController.initialize();
        primaryStage.close();
    }
}
