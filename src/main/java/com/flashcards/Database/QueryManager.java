package com.flashcards.Database;

import com.flashcards.Model.Category;
import com.flashcards.Model.Flashcard;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class QueryManager extends DatabaseManager {
    public static Flashcard querySingleFlashcard(int id) {

        // this method extracts the flashcard form the database. It does it by selecting
        // id of a phrase from the "phrases" table, and then it selects
        // translations from "translations" table, that contain the same phrase_id field,
        // as the phrase's id

        Flashcard flashcard = new Flashcard();
        try(
                Connection conn = DriverManager.getConnection(master_url);
                PreparedStatement phrasePstmt = conn.prepareStatement("SELECT * FROM phrases WHERE id = ?");
                PreparedStatement translationPstmt = conn.prepareStatement("SELECT * FROM translations WHERE phrase_id = ?");
                PreparedStatement whichCategoryPstmt = conn.prepareStatement("SELECT * FROM flashcard_category WHERE flashcard_id = ?");

        ){
            phrasePstmt.setInt(1, id);
            translationPstmt.setInt(1, id);
            whichCategoryPstmt.setInt(1, id);
            ResultSet phraseRS = phrasePstmt.executeQuery();
            ResultSet translationRS = translationPstmt.executeQuery();
            ResultSet whichCategoryRS = whichCategoryPstmt.executeQuery();
            if(phraseRS.next()) {
                flashcard.setPhrase(phraseRS.getString("phrase"));
                flashcard.setId(phraseRS.getInt("id"));
            }
            if(flashcard.getId() == null){
                throw new NullPointerException("Cannot find flashcard with id " + id);
            }
            while(translationRS.next()) {
                flashcard.addTranslation(translationRS.getString("translation"));
            }
            while(whichCategoryRS.next()) {
                flashcard.addCategoryToFlashcard(whichCategoryRS.getInt("category_id"));
            }
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }

        return flashcard;
    }

    public static Flashcard querySingleFlashcard(String phrase){

        // the same drill as above, but it's done with the phrase, instead of id

        Flashcard flashcard = new Flashcard();
        try(
                Connection conn = DriverManager.getConnection(master_url);
                PreparedStatement phrasePstmt = conn.prepareStatement("SELECT * FROM phrases WHERE phrase = ?");
                PreparedStatement translationPstmt = conn.prepareStatement("SELECT * FROM translations WHERE phrase_id = ?");
                PreparedStatement whichCategoryPstmt = conn.prepareStatement("SELECT * FROM flashcard_category WHERE flashcard_id = ?");
        ){
            phrasePstmt.setString(1, phrase);
            ResultSet phraseRS = phrasePstmt.executeQuery();

            if(phraseRS.next()) {
                flashcard.setPhrase(phraseRS.getString("phrase"));
                flashcard.setId(phraseRS.getInt("id"));
            }
            if(flashcard.getId() == null){
                throw new NullPointerException("Cannot find flashcard with string " + phrase);
            }
            translationPstmt.setInt(1, flashcard.getId());
            whichCategoryPstmt.setInt(1, flashcard.getId());
            ResultSet translationRS = translationPstmt.executeQuery();
            ResultSet whichCategoryRS = whichCategoryPstmt.executeQuery();
            while(translationRS.next()) {
                flashcard.getTranslations().add(translationRS.getString("translation"));
            }
            while(whichCategoryRS.next()) {
                flashcard.addCategoryToFlashcard(whichCategoryRS.getInt("category_id"));
            }
        } catch (SQLException e){
            System.err.println(e.getMessage());
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }

        return flashcard;
    }

    public static Flashcard queryRandomFlashcard() {

        // the same drill as querySingleFlashcard, but now it's random member

        Flashcard flashcard = new Flashcard();
        HashSet<Integer> deletedIdsTable = new HashSet<>(); // we only care if id is here, not which place, and HashSet
        // is quicker at this
        int randomID = 0;
        try(
                // I'm using regular statements instead of prepared ones because I first randomize id, and after that
                // I randomize it, so I can just insert that id into the statement, without preparing it beforehand
                Connection conn = DriverManager.getConnection(master_url);
                Statement countStmt = conn.createStatement();
                Statement phraseStmt = conn.createStatement();
                Statement translationStmt = conn.createStatement();
                Statement deletedFlashcardPstmt = conn.createStatement();
        ){
            ResultSet countRS = countStmt.executeQuery("SELECT COUNT(*) FROM phrases");
            ResultSet deletedRS = deletedFlashcardPstmt.executeQuery("SELECT * FROM deleted_ids");
            if(deletedRS.next()){
                for(int i=0; i<countRS.getInt(1); i++){
                    deletedIdsTable.add(deletedRS.getInt(1));
                }
            }

            // this line is the reason why randomExcludingTHeTable exists - so we don't have to rely on RNG to
            // randomize a new id if the randomized one got deleted before
            if(countRS.next()){
                randomID = randomExcludingTheTable(1, countRS.getInt(1), deletedIdsTable);
            }
            ResultSet phraseRS = phraseStmt.executeQuery("SELECT * FROM phrases WHERE id = " + randomID);
            ResultSet translationRS = translationStmt.executeQuery("SELECT * FROM translations WHERE phrase_id = " + randomID);
            if(phraseRS.next()) {
                flashcard.setPhrase(phraseRS.getString("phrase"));
                flashcard.setId(phraseRS.getInt("id"));
            }
            while(translationRS.next()) {
                flashcard.getTranslations().add(translationRS.getString("translation"));
            }
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }

        return flashcard;
    }

    public static Category queryCategory(int id) throws IllegalArgumentException {

        // self-explanatory, just selects the category from the database by id

        if(id <= 0){
            throw new IllegalArgumentException("Cannot query category with id " + id);
        }
        Category outCategory = new Category();

        try(
                Connection conn = DriverManager.getConnection(master_url);
                PreparedStatement categoryPstmt = conn.prepareStatement("SELECT * FROM categories where id = ?");
        ){
            categoryPstmt.setInt(1, id);
            ResultSet categoryRS = categoryPstmt.executeQuery();
            if(categoryRS.next()) {
                outCategory.setName(categoryRS.getString("name"));
                outCategory.setId(categoryRS.getInt("id"));
            }
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }

        return outCategory;
    }

    public static Category queryCategory(String name) throws IllegalArgumentException {

        // the same as above, but it's by the name String

        if(name == null){
            throw new IllegalArgumentException("Cannot query category with empty name category");
        }
        Category outCategory = new Category();

        try(
                Connection conn = DriverManager.getConnection(master_url);
                PreparedStatement categoryPstmt = conn.prepareStatement("SELECT * FROM categories where name = ?");
        ){
            categoryPstmt.setString(1, name);
            ResultSet categoryRS = categoryPstmt.executeQuery();
            if(categoryRS.next()) {
                outCategory.setName(categoryRS.getString("name"));
                outCategory.setId(categoryRS.getInt("id"));
            }
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }

        return outCategory;
    }
    public static List<Category> queryCategories() {
        List<Category> outCategories = new ArrayList<>();
        try(
                Connection conn = DriverManager.getConnection(master_url);
                PreparedStatement categoriesPstmt = conn.prepareStatement("SELECT * FROM categories");
                ){
            ResultSet categoriesRS = categoriesPstmt.executeQuery();
            while(categoriesRS.next()) {
                Category tempCategory = new Category();
                tempCategory.setName(categoriesRS.getString("name"));
                tempCategory.setId(categoriesRS.getInt("id"));
                outCategories.add(tempCategory);
            }
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }

        return outCategories;
    }
}
