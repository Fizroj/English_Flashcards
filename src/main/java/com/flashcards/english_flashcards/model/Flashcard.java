package com.flashcards.english_flashcards.model;

import java.util.ArrayList;

// Basic "building block" of this app. Each Flashcard contains unique phrase in one's native
// language, that you can train your English vocabulary on. It's intended to be for native-to-English
// vocabulary training, but honestly it could be any-to-any language...
public class Flashcard {
    private Integer id; //field for database purposes only
    private String phrase; // description of the word in your native language
    private ArrayList<String> translations; // table of possible translations to THAT phrase

    // Constructors
    public Flashcard(String phrase, ArrayList<String> translations) {
        this.phrase = phrase;
        this.translations = translations;
    }
    public Flashcard() {
        this.phrase = null;
        this.translations = new ArrayList<>();
    }

    // Getters and Setters
    public String getPhrase() {
        return phrase;
    }
    public ArrayList<String> getTranslations() {
        return translations;
    }
    public Integer getId() {
        return id;
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
}
