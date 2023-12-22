package pl.kargolek.extension.screenshot;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import pl.kargolek.util.ReportAttachment;
import pl.kargolek.util.WebDriverFactory;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Karol Kuta-Orlowicz
 */

class ScreenshotExtension implements AfterTestExecutionCallback {

    private final ReportAttachment attachment = new ReportAttachment();

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws MalformedURLException {
        if (isScreenshotOnFailedTest(extensionContext)) {
            if (isTestFailed(extensionContext)) {
                takeScreenshotProcess();
            }
        } else {
            takeScreenshotProcess();
        }
    }

    private boolean isScreenshotOnFailedTest(ExtensionContext extensionContext) {
        var annotationMethod = extensionContext
                .getTestMethod()
                .orElseThrow()
                .getAnnotation(TakeScreenshot.class);

        var annotationClass = extensionContext
                .getTestClass()
                .orElseThrow()
                .getAnnotation(TakeScreenshot.class);
        if (annotationClass != null) {
            return annotationClass.onFailedTest();
        }

        if (annotationMethod != null) {
            return annotationMethod.onFailedTest();
        }

        var takeScreenshots = Arrays.stream(extensionContext
                        .getTestClass()
                        .orElseThrow()
                        .getAnnotations())
                .map(annotation -> annotation.annotationType().getAnnotation(TakeScreenshot.class))
                .filter(Objects::nonNull)
                .toList();
        if (takeScreenshots.size() > 0) {
            return takeScreenshots.get(0).onFailedTest();
        }
        return false;
    }

    private boolean isTestFailed(ExtensionContext extensionContext) {
        return extensionContext
                .getExecutionException()
                .isPresent();
    }

    private void takeScreenshotProcess() {
        var driver = WebDriverFactory.getRemoteWebDriverInstance();
        var bytesArray = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        if (bytesArray.length > 0) {
            attachment.createAttachment(
                    bytesArray,
                    "Image attachment",
                    ReportAttachment.AttachmentType.IMAGE_PNG
            );
        }
    }
}
