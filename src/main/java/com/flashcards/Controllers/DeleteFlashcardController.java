package com.flashcards.Controllers;

import com.flashcards.Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.ArrayList;

public class DeleteFlashcardController {

    private Stage primaryStage;

    @FXML
    private TableView<Flashcard> flashcardsTable;

    @FXML
    private TableColumn<Flashcard, String> phraseColumn;

    @FXML
    private TableColumn<Flashcard, ArrayList<String>> translationsColumn;

    @FXML
    private TableColumn<Flashcard, Void> deleteColumn;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize(){
        phraseColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPhrase()));
        translationsColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTranslations()));
    }
}
