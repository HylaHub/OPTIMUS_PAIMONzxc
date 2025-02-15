package config;

import java.io.*;
import java.util.Properties;

public class ConfigReader {

    public static String getProperty(String key) {
        Properties properties = new Properties();
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream != null) {
                // Читаем файл с явным указанием кодировки UTF-8
                InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
                properties.load(reader);
                return properties.getProperty(key);
            } else {
                throw new FileNotFoundException("Property file not found in the classpath");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
