package org.switch2022.project.domain.valueobject.taxidentificationnumber;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * The ConfigurationFileReader class is responsible for reading the configuration properties from the
 * config.properties file.
 */
public class ConfigurationFileReader {

    public final Properties properties;

    /**
     * Instantiates a new ConfigurationFileReader with the values in the config.properties file.
     *
     * @throws Exception when file does not exist
     */
    public ConfigurationFileReader() throws Exception {
        try (FileInputStream fileInputStream = new FileInputStream("config.properties")) {
            Properties propertiesValue = new Properties();
            propertiesValue.load(fileInputStream);
            this.properties = propertiesValue;
        }

    }
}
