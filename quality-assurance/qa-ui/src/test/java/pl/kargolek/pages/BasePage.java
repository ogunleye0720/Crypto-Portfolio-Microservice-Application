package pl.kargolek.pages;

import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * @author Karol Kuta-Orlowicz
 */
@Getter
public class BasePage {

    public final WebDriver driver;
    public final Actions action;
    public final FluentWait<WebDriver> fluentWait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.action = new Actions(driver);
        this.fluentWait = new FluentWait<>(driver);

        PageFactory.initElements(driver, this);
    }

    public WebElement waitForElementVisibility(WebElement element, Duration timeout) {
        return new WebDriverWait(this.driver, timeout).until(ExpectedConditions.visibilityOf(element));
    }
    public Boolean waitForElementInvisibility(WebElement element, Duration timeout) {
        return new WebDriverWait(this.driver, timeout).until(ExpectedConditions.invisibilityOf(element));
    }

    public List<WebElement> waitForElementAllVisibility(WebElement element, Duration timeout) {
        return new WebDriverWait(this.driver, timeout).until(ExpectedConditions.visibilityOfAllElements(element));
    }

    public List<WebElement> waitForElementsVisibility(List<WebElement> elements, Duration timeout) {
        return new WebDriverWait(this.driver, timeout).until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public List<WebElement> waitForElementAllVisibility(By locator, Duration timeout) {
        return new WebDriverWait(this.driver, timeout).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public WebElement waitForElementVisibility(WebElement element, Duration timeout, Duration interval) {
        return this.fluentWait.withTimeout(timeout)
                .pollingEvery(interval)
                .until(ExpectedConditions.visibilityOf(element));
    }

    @SafeVarargs
    public final WebElement waitForElementVisibility(WebElement element, Duration timeout, Duration interval, Class<? extends Throwable>... clazz) {
        return this.fluentWait.withTimeout(timeout)
                .pollingEvery(interval)
                .ignoreAll(Arrays.asList(clazz))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public boolean waitForElementTextToChange(WebElement element, String text, Duration timeout) {
        return new WebDriverWait(this.driver, timeout).until(
                ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(element, text)));
    }

    public boolean waitForContainsURL(String partURL, Duration timeout){
        return new WebDriverWait(driver, timeout).until(ExpectedConditions.urlContains(partURL));
    }

    @Step("Click on the element {0}")
    public WebElement clickElement(WebElement element, Duration timeout) {
        new WebDriverWait(this.driver, timeout).until(ExpectedConditions.elementToBeClickable(element)).click();
        return element;
    }

    @Step("Click on the element {0}")
    public String getTextElement(WebElement element) {
        return element.getText();
    }

}
