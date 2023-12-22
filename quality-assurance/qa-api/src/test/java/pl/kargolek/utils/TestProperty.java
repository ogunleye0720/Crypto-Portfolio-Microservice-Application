package pl.kargolek.utils;

public class TestProperty {
    private static final TestProperty instance = new TestProperty();
    private static final PropertiesLoader PROPERTIES_LOADER = new PropertiesLoader(PathResolver.TEST_RESOURCES_PATH,
            "conf.properties");

    private TestProperty() {}

    public static TestProperty getInstance() {
        return instance;
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
    public String getBaseURL(){
        return PROPERTIES_LOADER.getPropertyValue("base.url");
    }
}
