package com.flashcards.Language_and_Properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import com.flashcards.Language_and_Properties.*;

public class LanguageManager {
    private static Locale locale;
    private static ResourceBundle resourceBundle;

    public static void setLocale(Locale localeUS) {
        locale = localeUS;
    }
    public static void setLanguage(Locale newLocale) {
        locale = newLocale;
        resourceBundle = ResourceBundle.getBundle("com.flashcards.Resource_Bundle.lang", locale);
    }
    public static void readLanguage() {
        try{
            String language = PropertiesManager.getConfigProperty("language");
            locale = new Locale(language);
            setLanguage(locale);
        } catch(Exception e) {
            e.printStackTrace();
            locale = Locale.US;
            setLanguage(locale);
        }
    }
    public static ResourceBundle getBundle() {
        return resourceBundle;
    }

    public static Locale getLocale() {
        return locale;
    }
}
