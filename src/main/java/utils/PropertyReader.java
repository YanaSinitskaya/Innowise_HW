package utils;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Properties;

@Log4j2
public class PropertyReader {

    @Getter
    private String propertiesPath = "config.properties";
    private static Properties properties;

    public PropertyReader() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(propertiesPath));
        } catch (IOException ex) {
            log.error("IOException caught in reading {}", propertiesPath);
            System.err.println("Error reading properties file: " + ex.getMessage());
        }
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            log.warn("Property {} is not found in file {}", key, propertiesPath);
        }
        return value;
    }
}


