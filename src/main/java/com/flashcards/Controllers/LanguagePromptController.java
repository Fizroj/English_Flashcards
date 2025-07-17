package com.flashcards.Controllers;

import java.io.*;
import java.util.Locale;

import com.flashcards.App.*;
import com.flashcards.Language_and_Properties.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

// A controller meant to appear only once. It's for language setup of the app, on later
// launches Main Controller takes over first
public class LanguagePromptController {

    private MainController mainController;

    private Stage primaryStage;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    @FXML
    private Button englishButton;

    @FXML
    private Button polishButton;

    @FXML
    private Label nameYourCategoryLabel;

    @FXML
    private Label disclaimerLabel;

    public void initialize(){
        LanguageManager.setLocale(Locale.US);
    }

    public void loadFirstCategoryScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/flashcards/FXML/First_Time_User_View/first-category-view.fxml"));
        Parent root = fxmlLoader.load();
        FirstCategoryController firstCategoryController = fxmlLoader.getController();
        firstCategoryController.setPrimaryStage(primaryStage);
        primaryStage.setTitle(LanguageManager.getBundle().getString("firstTimeCategory"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    @FXML
    public void onEnglishButton(ActionEvent event) {
        LanguageManager.setLanguage(new Locale.Builder().setLanguage("en").build());

        PropertiesManager.changeConfigProperty("language", "en");

        try{
            loadFirstCategoryScene();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void onPolishButton(ActionEvent event) {
        LanguageManager.setLanguage(new Locale.Builder().setLanguage("pl").build());

        PropertiesManager.changeConfigProperty("language", "pl");

        try{
            loadFirstCategoryScene();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}