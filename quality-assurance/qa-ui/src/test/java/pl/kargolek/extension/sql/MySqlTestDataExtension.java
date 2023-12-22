package pl.kargolek.extension.sql;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import pl.kargolek.data.jdbc.MySqlDbConnector;
import pl.kargolek.data.script.TestDataSql;
import pl.kargolek.util.TestProperty;

/**
 * @author Karol Kuta-Orlowicz
 */
public class MySqlTestDataExtension implements ParameterResolver {

    private final MySqlDbConnector mySqlDbConnector = MySqlDbConnector.builder()
            .setDataSourceURL(TestProperty.getInstance().getDataSourceURL())
            .setUsername(TestProperty.getInstance().getDbUsername())
            .setPassword(TestProperty.getInstance().getDbPassword())
            .build();

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(TestDataSql.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return new TestDataSql(this.mySqlDbConnector);
    }
}
