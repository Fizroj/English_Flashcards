package com.flashcards.Model;

import java.util.ArrayList;

// Basic "building block" of this app. Each Flashcard contains unique phrase in one's native
// language, that you can train your English vocabulary on. It's intended to be for native-to-English
// vocabulary training, but honestly it could be any-to-any language...
public class Flashcard {
    private Integer id; // field for database purposes only
    private String phrase; // description of the word in your native language
    private ArrayList<String> translations; // table of possible translations to THAT phrase
    private ArrayList<Integer> categories; // it's for storing id's of which categories the flashcard belongs to

    // Constructors
    public Flashcard(String phrase, ArrayList<String> translations, ArrayList<Integer> categories) {
        this.phrase = phrase;
        this.translations = translations;
        this.categories = categories;
    }
    public Flashcard() {
        this.phrase = null;
        this.translations = new ArrayList<>();
        this.categories = new ArrayList<>();
    }
    public Flashcard(String phrase) {
        this.phrase = phrase;
    }

    // Getters and Setters
    public String getPhrase() {
        return phrase;
    }
    public ArrayList<String> getTranslations() {
        return translations;
    }
    public String getTranslationsString(){
        String outString = "";
        for(int i=0; i<translations.size()-1; i++){
            outString = outString + translations.get(i) + ", ";
        }
        outString = outString + translations.get(translations.size());
        return outString;
    }
    public Integer getId() {
        return id;
    }
    public ArrayList<Integer> getCategories() {
        return categories;
    }
    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
    public void setTranslations(ArrayList<String> translations) {
        this.translations = translations;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setCategories(ArrayList<Integer> categories) {
        this.categories = categories;
    }

    // for debugging, and in the future for something else maybe
    public void printFlashcard(){
        System.out.println("id: " + id);
        System.out.println("phrase: " + phrase);
        System.out.println("translations: " + translations);
        System.out.println("categories: " + categories);
    }
    // adds one category to the flashcard table
    public void addCategoryToFlashcard(Integer category){
        this.categories.add(category);
    }
    public void addTranslation(String translation){
        this.translations.add(translation);
    }
    public void deleteTranslation(String translation){ this.translations.remove(translation); }
}
