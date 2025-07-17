package com.flashcards.Language_and_Properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {

    private static final String config_path = "databases/config.properties";

    public static void changeConfigProperty(String key, String value) {
        Properties properties = new Properties();

        // Load properties
        try(
                FileInputStream fileInputStream = new FileInputStream(config_path);
        ){
            properties.load(fileInputStream);
        } catch (IOException e){
            e.printStackTrace();
        }

        // Set provided property
        try(
                FileOutputStream fileOutputStream = new FileOutputStream(config_path);
        ){
            properties.setProperty(key, value);
            properties.store(fileOutputStream, null);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getConfigProperty(String key){
        Properties properties = new Properties();
        try(
                FileInputStream fileInputStream = new FileInputStream(config_path);
        ){
            properties.load(fileInputStream);
            return properties.getProperty(key);
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
