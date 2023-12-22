package pl.kargolek.cryptopriceservice.extension;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

/**
 * @author Karol Kuta-Orlowicz
 */
public class MySqlTestContainerExtension implements BeforeAllCallback {

    @Container
    static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8");

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        mySQLContainer.withReuse(true);
        mySQLContainer.start();
        System.setProperty("spring.datasource.url", mySQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", mySQLContainer.getUsername());
        System.setProperty("spring.datasource.password", mySQLContainer.getPassword());
    }
}
