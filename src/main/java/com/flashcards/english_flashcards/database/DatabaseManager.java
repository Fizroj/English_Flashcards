package com.flashcards.english_flashcards.database;

import org.sqlite.SQLiteException;
import com.flashcards.english_flashcards.model.*;

import java.io.PipedReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

// This class is everything regarding the database of this project (at least I hope lmao).
// Whether it's creating the database, tables inside it, inserts, deletes edits or updating,
// this entire class should be able to handle all of it
public class DatabaseManager {
    static String master_url = "jdbc:sqlite:databases/flashcards.db"; // visible to the entire class, it's
    // the route to the master database.

    // this method is for randomizing an integer from a given range excluding the table of integers
    // provided in the last argument.
    // start and end work like in the nextInt: "start" includes the number in the set,
    // but "end" does not.
    public static int randomExcludingTheTable(int start, int end, Set<Integer> excludes) throws IllegalArgumentException{
        // (quick vibe check)
        if(start>end){
            // we don't need to check for the "excludes" list, because even if we did, the mainList
            // later in this method will just recieve a list from start to end excluding end
            throw new IllegalArgumentException("start must be less than end");
        }

        int outRandomInteger = 0;
        ArrayList<Integer> mainList = new ArrayList<>();

        // after the loop, mainList will contain only the integers that we can randomize from
        for(int i = start; i < end; i++){
            if(excludes.contains(i)){
                continue;
            }
            mainList.add(i);
        }
        // (another vibe check, so that mainList isn't actually equal to the "excludes" list)
        if(mainList.isEmpty()){
            throw new IllegalArgumentException("mainList is empty, change arguments");
        }

        // now we're ready to randomize:
        outRandomInteger = mainList.get(new Random().nextInt(mainList.size()));

        return outRandomInteger;
    }

    // this method handles opening (or creating if nonexistent) database with all the
    // necessary tables, which store phrases, translations (together forming Flashcards) and other tables
    // that maintain proper connections between the tables and preserve overall functionality.
    public static void connect() {

        // phrases table
        String sqlStatementPhrases = "CREATE TABLE IF NOT EXISTS phrases ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "phrase TEXT NOT NULL UNIQUE"
                + ");";

        // translaions table
        String sqlStatementTranslations = "CREATE TABLE IF NOT EXISTS translations ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "phrase_id INTEGER NOT NULL, "
                + "translation TEXT NOT NULL, "
                + "FOREIGN KEY (phrase_id) REFERENCES phrases (id) ON DELETE CASCADE"
                + ");";

        // categories table
        String sqlStatementCategories = "CREATE TABLE IF NOT EXISTS categories ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL UNIQUE"
                + ");";

        // relation between the phrases and translations table
        String sqlStatementLinkCategoryWithFlashcards = "CREATE TABLE IF NOT EXISTS flashcard_category ("
                + "flashcard_id INTEGER NOT NULL,"
                + "category_id INTEGER NOT NULL,"
                + "PRIMARY KEY (flashcard_id, category_id),"
                + "FOREIGN KEY (flashcard_id) REFERENCES phrases(id) ON DELETE CASCADE,"
                + "FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE"
                + ");";

        // deleted phrases' id's table
        String sqlStatementDeletedIDs = "CREATE TABLE IF NOT EXISTS deleted_ids ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "deleted_id INTEGER NOT NULL"
                + ");";


        // opening (or creating) a database
        try (Connection conn = DriverManager.getConnection(master_url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                Statement stmt = conn.createStatement();
                stmt.execute(sqlStatementPhrases);
                stmt.execute(sqlStatementTranslations);
                stmt.execute(sqlStatementCategories);
                stmt.execute(sqlStatementLinkCategoryWithFlashcards);
                stmt.execute(sqlStatementDeletedIDs);
                System.out.println("The tables have been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // this method adds a single Flashcard to the database, with proper distinction
    // and connection between phrases and translations
    public static void addSingleFlashcard(Flashcard flashcard) throws IllegalArgumentException{
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

    // this method extracts the flashcard form the database. It does it by selecting
    // id of a phrase from the "phrases" table, and then it selects
    // translations from "translations" table, that contain the same phrase_id field,
    // as the phrase's id
    public static Flashcard querySingleFlashcard(int id) {
        Flashcard flashcard = new Flashcard();
        try(
                Connection conn = DriverManager.getConnection(master_url);
                Statement phraseStmt = conn.createStatement();
                Statement translationStmt = conn.createStatement();
                ResultSet phraseRS = phraseStmt.executeQuery("SELECT * FROM phrases WHERE id = " + id);
                ResultSet translationRS = translationStmt.executeQuery("SELECT * FROM translations WHERE phrase_id = " + id);
        ){
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

    // the same drill as above, but it's done with the phrase, instead of id
    public static Flashcard querySingleFlashcard(String phrase){
        Flashcard flashcard = new Flashcard();
        try(
                Connection conn = DriverManager.getConnection(master_url);
                PreparedStatement phrasePstmt = conn.prepareStatement("SELECT * FROM phrases WHERE phrase = ?");
                PreparedStatement translationPstmt = conn.prepareStatement("SELECT * FROM translations WHERE phrase_id = ?");
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
            ResultSet translationRS = translationPstmt.executeQuery();

            while(translationRS.next()) {
                flashcard.getTranslations().add(translationRS.getString("translation"));
            }
        } catch (SQLException e){
            System.err.println(e.getMessage());
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }

        return flashcard;
    }

    // the same drill as querySingleFlashcard, but now it's random member
    public static Flashcard queryRandomFlashcard() {
        Flashcard flashcard = new Flashcard();
        HashSet<Integer> deletedIdsTable = new HashSet<>();
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

    // this method is for deleting a flashcard based on its "id" field. No flashcard takes a deleted one's
    // place in the database, that's why randomExcludingTheTable makes sense in this project - to make sure you
    // don't randomize from an empty record
    public static void deleteSingleFlashcard(Flashcard flashcard) throws IllegalArgumentException {
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

    // self-explanatory, just adds new category to the database
    public static void addCategory(Category category) throws IllegalArgumentException {
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

    // self-explanatory, just removes a category from the database
    public static void deleteCategory(Category category){
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

    // this method saves into the database connections between flashcards and categories.
    // that's because single flashcard can be in multiple categories and we need to store in which ones each
    // flashcard is
    public static void addFlashcardToCategory(Flashcard flashcard, Category category) throws IllegalArgumentException {
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
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // start with the databases' initialization
        connect();
        //everything down here is just for testing
        ArrayList<String> array = new ArrayList<>();
        array.add("test1");
        array.add("test2");
        array.add("testHEHEHEHA");
        Flashcard f = new Flashcard("testAAAAAAAAAAAAAAAAAAA", array, new ArrayList<>());
        DatabaseManager.addSingleFlashcard(f);
        Flashcard flashcard = DatabaseManager.querySingleFlashcard(1);
        flashcard.printFlashcard();
        Flashcard flashcard2 = DatabaseManager.queryRandomFlashcard();
        flashcard2.printFlashcard();
        try{
            DatabaseManager.deleteSingleFlashcard(flashcard);
        } catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
        }
        Flashcard flashcard3 = DatabaseManager.querySingleFlashcard("tet");
        flashcard3.printFlashcard();

        Category category = new Category("CAT_TESTTTTT3");
        DatabaseManager.addCategory(category);
        DatabaseManager.addFlashcardToCategory(flashcard3, category);
    }
}
