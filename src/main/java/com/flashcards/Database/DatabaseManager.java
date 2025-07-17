package com.flashcards.Database;

import com.flashcards.Model.Category;
import com.flashcards.Model.Flashcard;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

// This class is the base for database management. It contains core database components
// as well as some helper methods to better handle the functionalities of this app.
// Its CRUD components are divided into other classes for readability.
public class DatabaseManager {
    protected final static String master_url = "jdbc:sqlite:databases/flashcards.db"; // visible to all classes managing
    // the database, it's the route to the master database

    public static int randomExcludingTheTable(int start, int end, Set<Integer> excludes) throws IllegalArgumentException{

        // this method is for randomizing an integer from a given range excluding the table of integers
        // provided in the last argument.
        // start and end work like in the nextInt: "start" includes the number in the set,
        // but "end" does not.

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

    public static void connect() {

        // this method handles opening (or creating if nonexistent) database with all the
        // necessary tables, which store phrases, translations (together forming Flashcards) and other tables
        // that maintain proper connections between the tables and preserve overall functionality.

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

    public static void fillCategoryWithFlashcards(Category category) throws IllegalArgumentException {

        // this is a method that takes all flashcards associated with a provided category (it's stored in the
        // database) and then fills this.category.flashcards with it

        if(category.getName() == null || category.getId() == null) {
            throw new IllegalArgumentException("Cannot fill a null category");
        }

        ArrayList<Integer> flashcard_ids = new ArrayList<>(); // for storing id's from flashcard_category
        try(
                Connection conn = DriverManager.getConnection(master_url);
                PreparedStatement categoryPstmt = conn.prepareStatement("SELECT * FROM flashcard_category WHERE category_id = ?");
                // Future idea: maybe improve the method by including more complex but effective queries
                //PreparedStatement flashcardsPstmt = conn.prepareStatement("SELECT phrases.id, phrases.phrase, translations.translation"
                //                                                            + "FROM phrases"
                //                                                            + "JOIN translations ON phrases.id = translations.phrase_id");
                ){
            // add selected id's to the inner ArrayList
            categoryPstmt.setInt(1, category.getId());
            ResultSet categoryRS = categoryPstmt.executeQuery();
            while(categoryRS.next()) {
                flashcard_ids.add(categoryRS.getInt("flashcard_id"));
            }
            // assign all of the flashcards with id's from flashcard_ids to the category
            for(int i=0; i<flashcard_ids.size(); i++) {
                category.addFlashcardToCategory(QueryManager.querySingleFlashcard(flashcard_ids.get(i)));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // start with the databases' initialization
        connect();
        //everything down here is just for testing
        /*ArrayList<String> array = new ArrayList<>();
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
        DatabaseManager.addFlashcardToCategory(flashcard3, category);*/
        Category category2 = QueryManager.queryCategory("CAT_TESTTTTT2");
        Flashcard flashcard3 = QueryManager.querySingleFlashcard(2);
        flashcard3.printFlashcard();
        //DatabaseManager.addFlashcardToCategory(flashcard3, category2);
        DatabaseManager.fillCategoryWithFlashcards(category2);
        category2.printCategory();
        category2.printFlashcardsOfACategory();

    }
}
