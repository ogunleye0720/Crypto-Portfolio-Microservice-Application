package pl.kargolek.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Karol Kuta-Orlowicz
 */
@Slf4j
public class ReportEnvironment {

    private final PropertiesWriter propertiesWriter = new PropertiesWriter(System.getProperty("user.dir") +
            "/target/allure-results/", "environment.properties");

    public void writeAllureEnvProperties(String browserName,
                                         String browserVersion,
                                         String osName,
                                         String parallelism) {
        this.propertiesWriter.writeProperty("Browser Name", browserName);
        this.propertiesWriter.writeProperty("Browser Version", browserVersion);
        this.propertiesWriter.writeProperty("OS Name", osName);
        this.propertiesWriter.writeProperty("Tests Parallelism", parallelism);
    }
}
