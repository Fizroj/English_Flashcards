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

public class DeleteCategoryController {
    private Stage primaryStage;

    private int pageNumber = 0;

    private ObservableList<Category> categoryList = FXCollections.observableArrayList();

    @FXML
    private TableView<Category> categoriesTable;

    @FXML
    private TableColumn<Category, String> nameColumn;

    @FXML
    private TableColumn<Category, Void> deleteColumn;

    @FXML
    private Button leaveButton;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize(){
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));

        deleteColumn.setCellFactory(column -> new TableCell<>() {
            private final Button removeButton = new Button("Delete");
            {
                removeButton.setOnAction(event -> {
                    Category category = getTableView().getItems().get(getIndex());
                    String currentCategoryName = PropertiesManager.getConfigProperty("currentCategory");
                    Category currentCategory = new Category(currentCategoryName, QueryManager.queryCategory(currentCategoryName).getId());
                    DeleteManager.deleteCategory(category);
                    categoryList.remove(category);
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

        categoryList.addAll(QueryManager.query10CategoriesPagination(0));
        categoriesTable.setItems(categoryList);
    }

    @FXML
    public void onPreviousPageButton(ActionEvent event) {
        if(pageNumber>0){
            pageNumber--;
            categoryList.setAll(QueryManager.query10CategoriesPagination(pageNumber));
            categoriesTable.setItems(categoryList);
        }
    }

    @FXML
    public void onNextPageButton(ActionEvent event) {
        if((pageNumber+1)<MainController.getNumberOfCategories()){
            pageNumber++;
            categoryList.setAll(QueryManager.query10CategoriesPagination(pageNumber));
            categoriesTable.setItems(categoryList);
        }
    }

    @FXML
    public void onLeaveButton(ActionEvent event) throws IOException {
        MainController.setNumberOfCategories(QueryManager.countAllCategories());

        CategorySettingsController.backToCategorySettings(primaryStage);
    }
}
