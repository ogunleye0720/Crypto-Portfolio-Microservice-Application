package pl.kargolek.pages.toast;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.kargolek.pages.BasePage;

import java.time.Duration;

/**
 * @author Karol Kuta-Orlowicz
 */
public class ToastPage extends BasePage {

    @FindBy(css = ".toast-container .toast-title")
    private WebElement toastTitle;

    @FindBy(css = ".toast-container .toast-message")
    private WebElement toastMessage;

    public ToastPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get toast popup title text")
    public String getToastTitle(){
        return this.waitForElementVisibility(this.toastTitle, Duration.ofSeconds(15)).getText();
    }

    @Step("Get toast popup message text")
    public String getToastMessage(){
        return this.waitForElementVisibility(this.toastMessage, Duration.ofSeconds(15)).getText();
    }
}
