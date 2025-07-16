package com.flashcards.english_flashcards.Controllers;

import java.io.*;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Locale;

import com.flashcards.english_flashcards.App.Application;
import com.flashcards.english_flashcards.Language.LanguageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    public void initialize(){
        LanguageManager.setLocale(Locale.US);
    }
    @FXML
    public void onEnglishButton(ActionEvent event) throws IOException {
        LanguageManager.setLanguage(new Locale.Builder().setLanguage("en").build());

        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/com/flashcards/english_flashcards/Resource_Bundle/config.properties"));
        properties.setProperty("language", "en");
        try(
                FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/com/flashcards/english_flashcards/Resource_Bundle/config.properties");
                ){
            properties.store(fileOutputStream, null);
        } catch (IOException e){
            e.printStackTrace();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/flashcards/english_flashcards/main-view.fxml"));
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
    @FXML
    public void onPolishButton(ActionEvent event) throws IOException {
        LanguageManager.setLanguage(new Locale.Builder().setLanguage("pl").build());

        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/com/flashcards/english_flashcards/Resource_Bundle/config.properties"));
        properties.setProperty("language", "pl");
        try(
                FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/com/flashcards/english_flashcards/Resource_Bundle/config.properties");
        ){
            properties.store(fileOutputStream, null);
        } catch (IOException e){
            e.printStackTrace();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/flashcards/english_flashcards/main-view.fxml"));
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