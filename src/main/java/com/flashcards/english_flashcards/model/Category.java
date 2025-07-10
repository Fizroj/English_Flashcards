package com.flashcards.english_flashcards.model;

import java.util.ArrayList;

// Categories are for storing Flashcards. It's done this way because it is better to
// divide and balance the learning process by putting each Flashcard in a relevant category, instead of
// putting every Flashcard into one cluster of a list. One Flashcard can be in single Category, or
// multiple Categories as well, because certain words could fit in more than just one.
// If one does not like multiple Categories and wants to learn everything at once, then it's
// still possible to stick to the single Category, no problem.
public class Category {
    private ArrayList<Flashcard> flashcards; // list of Flashcards
    private Integer id;
    private String name;
    // Constructors
    public Category(ArrayList<Flashcard> flashcard, String name) {
        this.flashcards = flashcard;
        this.name = name;
    }
    public Category() {
        this.flashcards = new ArrayList<>();
    }

    // Getter and Setter
    public ArrayList<Flashcard> getFlashcards() {
        return flashcards;
    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setFlashcards(ArrayList<Flashcard> flashcard) {
        this.flashcards = flashcard;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
}
