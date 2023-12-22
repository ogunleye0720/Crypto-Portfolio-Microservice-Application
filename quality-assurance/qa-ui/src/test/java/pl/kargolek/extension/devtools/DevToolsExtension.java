package pl.kargolek.extension.devtools;

import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.devtools.v114.network.model.MonotonicTime;
import pl.kargolek.extension.util.AnnotationResolver;
import pl.kargolek.util.DevToolsDriver;
import pl.kargolek.util.ReportAttachment;
import pl.kargolek.util.TestProperty;
import pl.kargolek.util.WebDriverFactory;
import pl.kargolek.util.constant.BrowserType;
import pl.kargolek.util.constant.NetworkReporter;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

/**
 * @author Karol Kuta-Orlowicz
 */
public class DevToolsExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {
    private DevToolsDriver devToolsDriver;
    private final ReportAttachment reportAttachment = new ReportAttachment();
    private final AnnotationResolver annotationResolver = new AnnotationResolver();
    private final BrowserType browserType = TestProperty.getInstance().getBrowserType();

    @Override
    public void beforeAll(ExtensionContext context) throws MalformedURLException {
        if (annotationResolver.getSeleniumWebDriverAnnotation(context).isBeforeAll()
                && isDriverSupportDevTools() && isNetworkReporterEnable()) {
            initDevTools();
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) throws MalformedURLException {
        if (!annotationResolver.getSeleniumWebDriverAnnotation(context).isBeforeAll()
                && isDriverSupportDevTools() && isNetworkReporterEnable()) {
            initDevTools();
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {
        if (isDriverSupportDevTools() && isNetworkReporterEnable()) {
            var data = this.formatData();
            this.attachNetworkData(data);
            if (!annotationResolver.getSeleniumWebDriverAnnotation(context).isBeforeAll()) {
                devToolsDriver.closeConnection();
            }
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        if (annotationResolver.getSeleniumWebDriverAnnotation(context).isBeforeAll()
                && isDriverSupportDevTools() && isNetworkReporterEnable()) {
            devToolsDriver.closeConnection();
        }
    }

    private synchronized void initDevTools() {
        devToolsDriver = DevToolsDriver
                .builder(WebDriverFactory.getRemoteWebDriverInstance())
                .createConnection()
                .build();
        devToolsDriver.setNetworkRequestListener();
        devToolsDriver.setNetworkResponsesListener();
    }

    private String formatData() {
        var requestsData = devToolsDriver.getRequests().stream()
                .map(requestModel -> String.format("\nRequest:[%s]\n%s URL:%s\nHeader:%s\nPayload:%s\n",
                        this.convert(requestModel.timestamp()),
                        requestModel.request().getMethod(),
                        requestModel.request().getUrl(),
                        requestModel.request().getHeaders(),
                        requestModel.request().getPostData().orElse("No post data")))
                .collect(Collectors.joining(System.lineSeparator()));

        var responseData = devToolsDriver.getResponses().stream()
                .map(responseModel -> String.format("\nResponse: [%s]\nURL:%s\nStatus:%s %s\nHeader:%s\nBody:%s\n",
                        this.convert(responseModel.timestamp()),
                        responseModel.response().getUrl(),
                        responseModel.response().getStatus(),
                        responseModel.response().getStatusText(),
                        responseModel.response().getHeaders(),
                        responseModel.body()))
                .collect(Collectors.joining(System.lineSeparator()));

        return String.format("[REQUESTS DATA]:\n%s\n\n[RESPONSE DATA]:\n%s", requestsData, responseData);
    }

    private LocalDateTime convert(MonotonicTime timestamp) {
        Duration duration = Duration.of((long) (Double.parseDouble(timestamp.toString())), ChronoUnit.MILLIS);
        return LocalDateTime.now(ZoneOffset.UTC).minus(duration);
    }

    private void attachNetworkData(String attachedData) {
        reportAttachment.createAttachment(
                attachedData.getBytes(StandardCharsets.UTF_8),
                "Network",
                ReportAttachment.AttachmentType.TEXT_LOG);
    }

    private boolean isDriverSupportDevTools() {
        return browserType == BrowserType.CHROME || browserType == BrowserType.MOBILE_CHROME
                || browserType == BrowserType.EDGE;
    }

    private boolean isNetworkReporterEnable() {
        return TestProperty.getInstance().getNetworkReporter() == NetworkReporter.ENABLE;
    }
}
