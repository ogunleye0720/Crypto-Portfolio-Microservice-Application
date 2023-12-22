package pl.kargolek.util;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import pl.kargolek.util.constant.BrowserType;
import pl.kargolek.util.constant.HeadlessMode;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Karol Kuta-Orlowicz
 */
@Slf4j
public class WebDriverFactory {
    private static final ThreadLocal<RemoteWebDriver> webDriverThreadLocal = new ThreadLocal<>();
    private static final String hubURL = TestProperty.getInstance().getSeleniumHubURL();
    private static final BrowserType browserType = TestProperty.getInstance().getBrowserType();
    private static final HeadlessMode headless = TestProperty.getInstance().getHeadlessMode();

    public static synchronized RemoteWebDriver getRemoteWebDriverInstance() {
        if (webDriverThreadLocal.get() == null) {
            log.info("Starting browser: {} for thread group: {} headless: {}",
                    browserType,
                    Thread.currentThread().getName(),
                    headless);
            switch (browserType) {
                case FIREFOX -> {
                    FirefoxOptions firefoxOptions = (FirefoxOptions) getOptions();
                    return getRemoteWebDriver(firefoxOptions);
                }
                case EDGE -> {
                    EdgeOptions edgeOptions = (EdgeOptions) getOptions();
                    return getRemoteWebDriver(edgeOptions);
                }
                case MOBILE_CHROME -> {
                    ChromeOptions mobileChromeOptions = (ChromeOptions) getOptions();
                    Map<String, String> mobileEmulation = new HashMap<>();
                    mobileEmulation.put("deviceName", "Nexus 5");
                    mobileChromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                    return getRemoteWebDriver(mobileChromeOptions);
                }
                default -> {
                    ChromeOptions chromeOptions = (ChromeOptions) getOptions();
                    return getRemoteWebDriver(chromeOptions);
                }
            }
        }
        return webDriverThreadLocal.get();
    }

    public static void closeWebDriverInstance() {
        if (webDriverThreadLocal.get() != null) {
            webDriverThreadLocal.get().quit();
            webDriverThreadLocal.remove();
        }
    }

    private static RemoteWebDriver getRemoteWebDriver(MutableCapabilities capabilities) {
        try {
            var driver = new RemoteWebDriver(new URL(hubURL), capabilities, false);
            webDriverThreadLocal.set(driver);
            return driver;
        } catch (MalformedURLException e) {
            throw new RuntimeException(
                    String.format("Unable to init remote web driver instance. URL: %s, BrowserType: %s",
                            WebDriverFactory.hubURL,
                            browserType));
        }
    }

    private static MutableCapabilities getOptions() {
        switch (browserType) {
            case FIREFOX -> {
                var firefoxOptions = new FirefoxOptions();
                if (headless == HeadlessMode.ENABLE) {
                    firefoxOptions.addArguments("--headless");
                }
                firefoxOptions.addArguments("--width=1920");
                firefoxOptions.addArguments("--height=1080");
                return firefoxOptions;
            }
            case EDGE -> {
                var edgeOptions = new EdgeOptions();
                if (headless == HeadlessMode.ENABLE) {
                    edgeOptions.addArguments("--headless");
                }
                edgeOptions.addArguments("--window-size=1920,1080");
                return edgeOptions;
            }
            default -> {
                var chromeOptions = new ChromeOptions();
                if (headless == HeadlessMode.ENABLE) {
                    chromeOptions.addArguments("--headless=new.");
                }
                chromeOptions.addArguments("--window-size=1920,1080");
                return chromeOptions;
            }
        }
    }
}
