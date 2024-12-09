package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private static String propertiesPath = "/config.properties";
    private static Properties properties;

    public PropertyReader(String propertiesPath) {
        properties = new Properties();
        try (InputStream inputStream = new FileInputStream(propertiesPath)) {
            properties.load(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}


