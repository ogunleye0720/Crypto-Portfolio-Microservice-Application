package pl.kargolek.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Karol Kuta-Orlowicz
 */
@Slf4j
public class PropertiesLoader {

    private final String path;
    private final String fileName;
    private Properties properties;

    public PropertiesLoader(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
        loadProperties();
    }

    private void loadProperties(){
        try {
            this.properties = new Properties();
            this.properties.load(new FileReader(this.path + fileName));
        } catch (IOException e) {
            log.error("Unable to load properties file. Path:{}, FileName:{}", this.path, this.fileName);
            throw new RuntimeException(e);
        }
    }

    public String getPropertyValue(String propertyKey){
        return this.properties.getProperty(propertyKey);
    }
}
