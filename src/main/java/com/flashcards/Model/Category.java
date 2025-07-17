package com.flashcards.Model;

import java.util.ArrayList;

// Categories are for storing Flashcards. It's done this way because it is better to
// divide and balance the learning process by putting each Flashcard in a relevant category, instead of
// putting every Flashcard into one cluster of a list. One Flashcard can be in single Category, or
// multiple Categories as well, because certain words could fit in more than just one.
// If one does not like multiple Categories and wants to learn everything at once, then it's
// still possible to stick to the single Category, no problem.
public class Category {
    private ArrayList<Flashcard> flashcards; // list of Flashcards (not all of them are stored here,
    // because database is for that, here you store only some flashcards
    private Integer id; // field for database purposes
    private String name; // name of the category

    // Constructors
    public Category(ArrayList<Flashcard> flashcard, String name) {
        this.flashcards = flashcard;
        this.name = name;
    }
    public Category(String name) {
        this.flashcards = new ArrayList<>();
        this.name = name;
    }
    public Category() {
        this.flashcards = new ArrayList<>();
    }

    // Getters and Setters
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

    // for debugging, and in the future for something else maybe
    public void printCategory(){
        System.out.println("id: " + id);
        System.out.println("name: " + name);
        System.out.println("flashcards: " + flashcards);
    }
    // add a new flashcard to the category
    public void addFlashcardToCategory(Flashcard flashcard){
        this.flashcards.add(flashcard);
    }
    // delete selected flashcard from the category
    public void removeFlashcardFromCategory(Flashcard flashcard){
        flashcards.remove(flashcard);
    }
    // for debugging, prints out all of the flashcards present in this category
    public void printFlashcardsOfACategory(){
        for(Flashcard flashcard : flashcards){
            flashcard.printFlashcard();
            System.out.println("///");
        }
    }
}
