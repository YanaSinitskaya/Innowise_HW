package utils;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log4j2
public class PropertyReader {

    @Getter
    private static String propertiesPath = "/config.properties";
    private static volatile Properties properties;

    private PropertyReader() {

    }

    private static String getCorrectPath() {
        return propertiesPath.startsWith("/") ? propertiesPath : "/" + propertiesPath;
    }

    public static Properties readProperties() {
        properties = new Properties();
        try (InputStream inputStream = PropertyReader.class.getResourceAsStream(getCorrectPath())) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                log.error("Properties file {} not found.", propertiesPath);
                System.err.println("Error reading properties file: " + propertiesPath);
            }
        } catch (IOException ex) {
            log.error("IOException caught in reading {}", propertiesPath);
            System.err.println("Error reading properties file: " + ex.getMessage());
        }
        return properties;
    }

    private static Properties loadProperties() {
        return properties != null ? properties : readProperties();
    }

    public static String getProperty(String propertyName) {
        return loadProperties().getProperty(propertyName);
    }
}


