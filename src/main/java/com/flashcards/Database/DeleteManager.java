package com.flashcards.Database;

import com.flashcards.Model.Category;
import com.flashcards.Model.Flashcard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteManager extends DatabaseManager{
    public static void deleteSingleFlashcard(Flashcard flashcard) throws IllegalArgumentException {

        // this method is for deleting a flashcard based on its "id" field. No flashcard takes a deleted one's
        // place in the database, that's why randomExcludingTheTable makes sense in this project - to make sure you
        // don't randomize from an empty record

        if(flashcard.getPhrase() == null || flashcard.getTranslations() == null) {
            throw new IllegalArgumentException("Cannot delete empty flashcard");
        }
        try(
                Connection conn = DriverManager.getConnection(master_url);
                PreparedStatement phrasePstmt = conn.prepareStatement("DELETE FROM phrases WHERE id = ?");
                // vvv not necessary, because of ON DELETE CASCADE vvv
                // PreparedStatement translationPstmt = conn.prepareStatement("DELETE FROM translations WHERE phrase_id = ?");
                PreparedStatement deletedFlashcardPsmt = conn.prepareStatement("INSERT INTO deleted_ids (deleted_id) VALUES (?)")
        ){
            phrasePstmt.setInt(1, flashcard.getId());
            //translationPstmt.setInt(1, flashcard.getId());
            deletedFlashcardPsmt.setInt(1, flashcard.getId());

            // this line is to check if the method actually deleted anything from the database, just to be sure
            int phraseChanges = phrasePstmt.executeUpdate();
            if(phraseChanges == 0) {
                System.out.println("No phrases were deleted...");
            }

            //translationPstmt.executeUpdate();
            deletedFlashcardPsmt.executeUpdate();
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public static void deleteCategory(Category category){

        // self-explanatory, just removes a category from the database

        if(category.getId() == null) {
            throw new IllegalArgumentException("Cannot delete null name category");
        }

        try(
                Connection conn = DriverManager.getConnection(master_url);
                PreparedStatement categoryPstmt = conn.prepareStatement("DELETE FROM categories where id = ?");
        ){
            categoryPstmt.setInt(1, category.getId());

            // this line here is for checking that the method actually deleted something from
            // the database
            int changes = categoryPstmt.executeUpdate();
            if(changes == 0) {
                System.out.println("No categories were deleted...");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteFlashcardFromCategory(Flashcard flashcard, Category category) throws IllegalArgumentException {

        // this method deletes all links in the database between the provided flashcard
        // and category. It does NOT delete flashcard nor does it delete a category

        if(flashcard.getId() == null || category.getId() == null) {
            throw new IllegalArgumentException("Cannot delete (null) flashcard from (null) category");
        }

        String sqlDeleteLinkFlashcardFromCategory = "DELETE FROM flashcard_category WHERE category_id = ? AND flashcard_id = ?";

        try(
                Connection conn = DriverManager.getConnection(master_url);
                PreparedStatement deleteLinkPstmt = conn.prepareStatement(sqlDeleteLinkFlashcardFromCategory);
        ){
            deleteLinkPstmt.setInt(1, category.getId());
            deleteLinkPstmt.setInt(2, flashcard.getId());
            int changes = deleteLinkPstmt.executeUpdate();
            if(changes == 0){
                System.out.println("No links between flashcards and categories were deleted...");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
