package pl.kargolek.extension.pages;

import org.junit.jupiter.api.extension.*;
import pl.kargolek.extension.exception.NoSuchExtensionInitObjectException;
import pl.kargolek.extension.util.AnnotationResolver;
import pl.kargolek.pages.InitPages;
import pl.kargolek.util.WebDriverFactory;

import java.net.MalformedURLException;

/**
 * @author Karol Kuta-Orlowicz
 */
public class PageObjectExtension implements BeforeAllCallback, BeforeEachCallback, ParameterResolver {

    private final AnnotationResolver annotationResolver = new AnnotationResolver();
    private InitPages initPages;

    @Override
    public void beforeAll(ExtensionContext context) throws MalformedURLException {
        if (annotationResolver.getSeleniumWebDriverAnnotation(context).isBeforeAll()) {
            var driver = WebDriverFactory.getRemoteWebDriverInstance();
            this.initPages = new InitPages(driver);
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) throws MalformedURLException {
        if (!annotationResolver.getSeleniumWebDriverAnnotation(context).isBeforeAll()) {
            var driver = WebDriverFactory.getRemoteWebDriverInstance();
            this.initPages = new InitPages(driver);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(InitPages.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        if (this.initPages != null) return this.initPages;
        throw new NoSuchExtensionInitObjectException("Unable to resolve param: " + initPages.getClass().getCanonicalName());
    }
}
