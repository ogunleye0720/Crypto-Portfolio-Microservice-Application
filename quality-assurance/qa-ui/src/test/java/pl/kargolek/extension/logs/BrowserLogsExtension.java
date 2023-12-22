package pl.kargolek.extension.logs;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import pl.kargolek.util.ReportAttachment;
import pl.kargolek.util.TestProperty;
import pl.kargolek.util.WebDriverFactory;
import pl.kargolek.util.constant.BrowserType;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @author Karol Kuta-Orlowicz
 */
public class BrowserLogsExtension implements AfterTestExecutionCallback {

    private final ReportAttachment reportAttachment = new ReportAttachment();
    private final BrowserType browserType = TestProperty.getInstance().getBrowserType();

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) {
        if (isDriverSupportBrowserLog()) {
            var driver = WebDriverFactory.getRemoteWebDriverInstance();
            var consoleLogs = driver
                    .manage()
                    .logs()
                    .get(LogType.BROWSER)
                    .getAll();

            reportAttachment.createAttachment(toByteArray(consoleLogs), "Browser logs",
                    ReportAttachment.AttachmentType.TEXT_LOG);
        }
    }

    private byte[] toByteArray(List<LogEntry> logEntries) {
        StringBuilder builder = new StringBuilder();
        logEntries.forEach(logEntry -> {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(logEntry.getTimestamp()),
                    ZoneId.systemDefault()
            );
            builder
                    .append("[")
                    .append(logEntry.getLevel().getName())
                    .append(" ")
                    .append(localDateTime)
                    .append("]\n")
                    .append(logEntry.getMessage())
                    .append("\n\n");
        });
        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }

    private boolean isDriverSupportBrowserLog() {
        return this.browserType == BrowserType.CHROME || this.browserType == BrowserType.MOBILE_CHROME
                || this.browserType == BrowserType.EDGE;
    }
}
