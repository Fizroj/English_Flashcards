package com.flashcards.Database;

import com.flashcards.Model.Category;
import com.flashcards.Model.Flashcard;


import java.sql.*;

public class InsertManager extends DatabaseManager{
    public static void addSingleFlashcard(Flashcard flashcard) throws IllegalArgumentException{

        // this method adds a single Flashcard to the database, with proper distinction
        // and connection between phrases and translations

        if(flashcard.getPhrase() == null || flashcard.getTranslations() == null){
            throw new IllegalArgumentException("Cannot add null flashcard");
        }
        String sqlInsertPhrase = "INSERT INTO phrases (phrase) VALUES (?)";
        String sqlInsertTranslation = "INSERT INTO translations (phrase_id, translation) VALUES (?,?)";

        try (Connection conn = DriverManager.getConnection(master_url);
             PreparedStatement pstmtPhrase = conn.prepareStatement(sqlInsertPhrase, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pstmtTranslation = conn.prepareStatement(sqlInsertTranslation)) {

            // inserting the phrase
            pstmtPhrase.setString(1, flashcard.getPhrase());
            pstmtPhrase.executeUpdate();

            ResultSet phraseKey = pstmtPhrase.getGeneratedKeys();
            int phraseID = -1;
            if(phraseKey.next()) {
                phraseID = phraseKey.getInt(1);
                flashcard.setId(phraseID);
            }
            // inserting translations of the phrase
            for(int i=0; i<flashcard.getTranslations().size(); i++) {
                pstmtTranslation.setInt(1, phraseID);
                pstmtTranslation.setString(2, flashcard.getTranslations().get(i));
                pstmtTranslation.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void addCategory(Category category) throws IllegalArgumentException {

        // self-explanatory, just adds new category to the database

        if(category.getName() == null) {
            throw new IllegalArgumentException("Cannot add null name category");
        }

        try(
                Connection conn = DriverManager.getConnection(master_url);
                PreparedStatement categoryPstmt = conn.prepareStatement("INSERT INTO categories (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        ){
            categoryPstmt.setString(1, category.getName());
            categoryPstmt.executeUpdate();
            ResultSet categoryRS = categoryPstmt.getGeneratedKeys();
            if(categoryRS.next()) {
                category.setId(categoryRS.getInt(1));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void addFlashcardToCategory(Flashcard flashcard, Category category) throws IllegalArgumentException {

        // this method saves into the database link between flashcards and categories.
        // that's because single flashcard can be in multiple categories and we need to store in which ones each
        // flashcard is

        if(flashcard.getPhrase() == null || flashcard.getTranslations() == null || flashcard.getId() == null) {
            throw new IllegalArgumentException("Cannot add null flashcard to category");
        }
        if(category.getId() == null || category.getName() == null) {
            throw new IllegalArgumentException("Cannot add flashcard to null category");
        }

        String sqlLinkFlashcardToCategory = "INSERT INTO flashcard_category (flashcard_id, category_id) VALUES (?,?)";

        try(
                Connection conn = DriverManager.getConnection(master_url);
                PreparedStatement linkPstmt = conn.prepareStatement(sqlLinkFlashcardToCategory);
        ){
            linkPstmt.setInt(1, flashcard.getId());
            linkPstmt.setInt(2, category.getId());
            linkPstmt.executeUpdate();
            // after adding a link to the database, it's useful to add flashcard logically
            // to the category in the code as well
            category.addFlashcardToCategory(flashcard);
            flashcard.addCategoryToFlashcard(category.getId());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
