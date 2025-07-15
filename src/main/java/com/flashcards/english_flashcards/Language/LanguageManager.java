package com.flashcards.english_flashcards.Language;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {
    private static Locale locale;
    private static ResourceBundle resourceBundle;

    public static void setLocale(Locale localeUS) {
        locale = localeUS;
    }
    public static void setBundle(Locale newLocale) {
        locale = newLocale;
        resourceBundle = ResourceBundle.getBundle("com.flashcards.english_flashcards.Resource_Bundle.lang", locale);
    }

    public static ResourceBundle getBundle() {
        return resourceBundle;
    }

    public static Locale getLocale() {
        return locale;
    }
}
