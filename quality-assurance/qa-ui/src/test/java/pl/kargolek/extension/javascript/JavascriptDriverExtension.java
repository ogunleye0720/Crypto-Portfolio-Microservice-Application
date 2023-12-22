package pl.kargolek.extension.javascript;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import pl.kargolek.util.JavascriptDriver;
import pl.kargolek.util.WebDriverFactory;

/**
 * @author Karol Kuta-Orlowicz
 */
public class JavascriptDriverExtension implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(JavascriptDriver.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return JavascriptDriver.builder(WebDriverFactory.getRemoteWebDriverInstance())
                .createJavascriptExecutor()
                .build();
    }

}
