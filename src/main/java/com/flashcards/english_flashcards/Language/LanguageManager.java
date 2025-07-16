package com.flashcards.english_flashcards.Language;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class LanguageManager {
    private static Locale locale;
    private static ResourceBundle resourceBundle;

    public static void setLocale(Locale localeUS) {
        locale = localeUS;
    }
    public static void setLanguage(Locale newLocale) {
        locale = newLocale;
        resourceBundle = ResourceBundle.getBundle("com.flashcards.english_flashcards.Resource_Bundle.lang", locale);
    }
    public static void readLanguage() {
        Properties properties = new Properties();
        try (
                InputStream stream = LanguageManager.class.getClassLoader().getResourceAsStream("Resource_Bundle/config.properties");
        ) {
            if(stream != null) {
                properties.load(stream);
                String language = properties.getProperty("language");
                locale = new Locale(language);
                setLanguage(locale);
            } else {
                locale = Locale.US;
                setLanguage(locale);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ResourceBundle getBundle() {
        return resourceBundle;
    }

    public static Locale getLocale() {
        return locale;
    }
}
