package pl.kargolek.util;

import pl.kargolek.util.constant.BrowserType;
import pl.kargolek.util.constant.HeadlessMode;
import pl.kargolek.util.constant.NetworkReporter;
import pl.kargolek.util.constant.ParallelTest;

import java.util.Optional;

public class TestProperty {
    private static final TestProperty instance = new TestProperty();
    private static final PropertiesLoader PROPERTIES_LOADER = new PropertiesLoader(PathResolver.TEST_RESOURCES_PATH,
            "conf.properties");

    private TestProperty() {}

    public static TestProperty getInstance() {
        return instance;
    }

    public String getAppBaseURL() {
        return PROPERTIES_LOADER.getPropertyValue("app.base.url");
    }

    public String getAppTitle() {
        return PROPERTIES_LOADER.getPropertyValue("app.title");
    }

    public String getDataSourceURL() {
        return PROPERTIES_LOADER.getPropertyValue("db.datasource.url");
    }

    public String getDbUsername(){
        return System.getenv("MYSQL_USER");
    }

    public String getDbPassword(){
        return System.getenv("MYSQL_PASSWORD");
    }

    public String getSeleniumHubURL(){
        return PROPERTIES_LOADER.getPropertyValue("selenium.hub.url");
    }

    public BrowserType getBrowserType(){
        var browser = Optional.ofNullable(System.getProperty("browser"))
                .orElse("chrome");
        return switch (browser) {
            case "firefox" -> BrowserType.FIREFOX;
            case "edge" -> BrowserType.EDGE;
            case "mobile-chrome" -> BrowserType.MOBILE_CHROME;
            default -> BrowserType.CHROME;
        };
    }

    public HeadlessMode getHeadlessMode(){
        var headlessMode = Optional.ofNullable(System.getProperty("headless"))
                .orElse("disable");
        return headlessMode.equals("enable") ? HeadlessMode.ENABLE : HeadlessMode.DISABLE;
    }

    public ParallelTest getParallelism(){
        var parallel = Optional.ofNullable(System.getProperty("junit.jupiter.execution.parallel.enabled"))
                .orElse("false");
        return parallel.equals("true") ? ParallelTest.ENABLE : ParallelTest.DISABLE;
    }

    public NetworkReporter getNetworkReporter(){
        var network = Optional.ofNullable(System.getProperty("network"))
                .orElse("enable");
        return network.equals("enable") ? NetworkReporter.ENABLE : NetworkReporter.DISABLE;
    }


}
