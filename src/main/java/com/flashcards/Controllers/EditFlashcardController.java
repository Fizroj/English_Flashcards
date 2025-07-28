package com.flashcards.Controllers;

import com.flashcards.Database.DeleteManager;
import com.flashcards.Database.QueryManager;
import com.flashcards.Language_and_Properties.PropertiesManager;
import com.flashcards.Model.Category;
import com.flashcards.Model.Flashcard;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class EditFlashcardController {
    private Stage primaryStage;

    private int pageNumber = 0;

    private ObservableList<Flashcard> flashcardList = FXCollections.observableArrayList();

    @FXML
    private TableView<Flashcard> flashcardsTable;

    @FXML
    private TableColumn<Flashcard, String> phraseColumn;

    @FXML
    private TableColumn<Flashcard, ArrayList<String>> translationsColumn;

    @FXML
    private TableColumn<Flashcard, Void> deleteColumn;

    @FXML
    private Button leaveButton;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize(){
        phraseColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPhrase()));
        translationsColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTranslations()));

        deleteColumn.setCellFactory(column -> new TableCell<>() {
            private final Button removeButton = new Button("Edit");
            {
                removeButton.setOnAction(event -> {
                    Flashcard flashcard = getTableView().getItems().get(getIndex());
                    String categoryName = PropertiesManager.getConfigProperty("currentCategory");
                    Category category = new Category(categoryName, QueryManager.queryCategory(categoryName).getId());

                    // TODO: FINALLY make editing flashcards avaible one freakin' DAY
                    DeleteManager.deleteFlashcardFromCategory(flashcard, category);
                    DeleteManager.deleteSingleFlashcard(flashcard);
                    flashcardList.remove(flashcard);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(removeButton);
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                }
            }
        });

        flashcardList.addAll(QueryManager.query10FlashcardsPagination(0));
        flashcardsTable.setItems(flashcardList);
    }

    @FXML
    public void onPreviousPageButton(ActionEvent event) {
        if(pageNumber>0){
            pageNumber--;
            flashcardList.setAll(QueryManager.query10FlashcardsPagination(pageNumber));
            flashcardsTable.setItems(flashcardList);
        }
    }

    @FXML
    public void onNextPageButton(ActionEvent event) {
        if((pageNumber+1)<MainController.getNumberOfFlashcardsOfCurrentCategory()){
            pageNumber++;
            flashcardList.setAll(QueryManager.query10FlashcardsPagination(pageNumber));
            flashcardsTable.setItems(flashcardList);
        }
    }

    @FXML
    public void onLeaveButton(ActionEvent event) throws IOException {
        MainController.setNumberOfFlashcards(QueryManager.countAllFlashcards());

        FlashcardsSettingsController.backToFlashcardsSettings(primaryStage);
    }
}
