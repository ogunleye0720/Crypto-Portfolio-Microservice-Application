package pl.kargolek.extension.util;

import org.junit.jupiter.api.extension.ExtensionContext;
import pl.kargolek.extension.driver.SeleniumWebDriver;
import pl.kargolek.extension.util.exception.NoSuchAnnotationException;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Karol Kuta-Orlowicz
 */
public class AnnotationResolver {

    public SeleniumWebDriver getSeleniumWebDriverAnnotation(ExtensionContext extensionContext) {
        var driverAnnotation = extensionContext.getTestClass()
                .orElseThrow()
                .getAnnotation(SeleniumWebDriver.class);

        if (driverAnnotation == null) {
            var configAnnotations = Arrays.stream(extensionContext.getTestClass()
                            .orElseThrow()
                            .getAnnotations())
                    .sequential()
                    .filter(annotation -> annotation.annotationType().getSimpleName().contains("Config"))
                    .toList();

            var seleniumAnnotation = configAnnotations
                    .stream()
                    .map(annotation -> annotation.annotationType().getAnnotation(SeleniumWebDriver.class))
                    .filter(Objects::nonNull)
                    .toList();

            if (seleniumAnnotation.isEmpty()) {
                throw new NoSuchAnnotationException(
                        "Unable to get @SeleniumWebDriver annotation from test classes or @*Config annotation");
            }
            return seleniumAnnotation.get(0);
        }
        return driverAnnotation;
    }
}
