package pl.kargolek.extension.driver;

import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import pl.kargolek.extension.util.AnnotationResolver;
import pl.kargolek.util.ReportEnvironment;
import pl.kargolek.util.TestProperty;
import pl.kargolek.util.WebDriverFactory;

/**
 * @author Karol Kuta-Orlowicz
 */
public class WebDriverExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback, ParameterResolver {

    private final AnnotationResolver annotationResolver = new AnnotationResolver();
    private final ReportEnvironment reportEnvironment = new ReportEnvironment();
    private String browserName;
    private String browserVersion;
    private String osName;
    private String parallelism;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (annotationResolver.getSeleniumWebDriverAnnotation(context)
                .isBeforeAll()) {
            this.initWebDriver();
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        if (!annotationResolver.getSeleniumWebDriverAnnotation(context)
                .isBeforeAll()) {
            initWebDriver();
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {
        if (!annotationResolver.getSeleniumWebDriverAnnotation(context)
                .isBeforeAll()) {
            this.tearDownDriver();
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        if (annotationResolver.getSeleniumWebDriverAnnotation(context).isBeforeAll()) {
            this.tearDownDriver();
        }

        reportEnvironment.writeAllureEnvProperties(
                browserName,
                browserVersion,
                osName,
                parallelism);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter()
                .getType()
                .equals(WebDriver.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return WebDriverFactory.getRemoteWebDriverInstance();
    }

    private void initWebDriver() {
        WebDriverFactory.getRemoteWebDriverInstance().manage()
                .window()
                .maximize();

        setBrowserName();
        setBrowserVersion();
        setOsName();
        setParallelism();
    }

    private void tearDownDriver() {
        WebDriverFactory.closeWebDriverInstance();
    }

    public void setBrowserName() {
        if (this.browserName == null) {
            this.browserName = WebDriverFactory.getRemoteWebDriverInstance()
                    .getCapabilities()
                    .getBrowserName();
        }
    }

    public void setBrowserVersion() {
        if (this.browserVersion == null) {
            this.browserVersion = WebDriverFactory.getRemoteWebDriverInstance()
                    .getCapabilities()
                    .getBrowserVersion();
        }
    }

    public void setOsName() {
        if (this.osName == null) {
            this.osName = WebDriverFactory.getRemoteWebDriverInstance()
                    .getCapabilities()
                    .getPlatformName()
                    .name();
        }
    }

    public void setParallelism() {
        if (this.parallelism == null) {
            this.parallelism = TestProperty.getInstance().getParallelism().toString();
        }
    }
}
