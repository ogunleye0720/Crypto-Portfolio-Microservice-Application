package pl.kargolek.util;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pl.kargolek.util.exception.NoSuchWindowForOpenException;

/**
 * @author Karol Kuta-Orlowicz
 */
public class WebDriverUtil {

    /**
     * Function switch to the next window or tab even if more than two exist
     * Need to provide parent window (current window) name
     */
    @Step("Switch viewport to the next tab/window")
    public static void switchToNextTab(WebDriver driver, String currentWindow){
        var windowExplorer = driver.getWindowHandles().stream()
                .filter(window -> !window.equals(currentWindow))
                .findFirst()
                .orElseThrow(() -> new NoSuchWindowForOpenException(currentWindow));
        driver.switchTo().window(windowExplorer);
    }

    @Step("Refresh page")
    public static void refreshPage(WebDriver driver){
        driver.navigate().refresh();
    }

    @Step("Navigate forward page")
    public static void navigateForward(WebDriver driver){
        driver.navigate().forward();
    }

    @Step("Navigate back page")
    public static void navigateBack(WebDriver driver){
        driver.navigate().back();
    }

}
