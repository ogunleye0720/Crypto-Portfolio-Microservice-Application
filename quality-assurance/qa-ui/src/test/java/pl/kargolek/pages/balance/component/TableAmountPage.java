package pl.kargolek.pages.balance.component;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.kargolek.pages.BasePage;

import java.time.Duration;

/**
 * @author Karol Kuta-Orlowicz
 */
public abstract class TableAmountPage extends BasePage {

    private final By totalAmount = By.cssSelector(".total-amount");
    private final By symbol = By.cssSelector(".symbol");
    private final By totalTokenValue = By.cssSelector(".total-value");

    private final By symbolHeader = By.cssSelector(".header-symbol");

    private final By totalAmountHeader = By.cssSelector(".header-symbol");

    private final By totalTokenValueHeader = By.cssSelector(".header-symbol");

    public TableAmountPage(WebDriver driver) {
        super(driver);
    }

    protected abstract WebElement getParent();

    @Step("Get symbol header")
    public WebElement getSymbolHeader() {
        return this.waitForElementVisibility(getParent(), Duration.ofSeconds(10))
                .findElement(symbolHeader);
    }

    @Step("Get total amount header")
    public WebElement getTotalAmountHeader() {
        return this.waitForElementVisibility(getParent(), Duration.ofSeconds(10))
                .findElement(totalAmountHeader);
    }

    @Step("Get total value header")
    public WebElement getTotalTokenValueHeader() {
        return this.waitForElementVisibility(getParent(), Duration.ofSeconds(10))
                .findElement(totalTokenValueHeader);
    }

    @Step("Get token amount text")
    public String getTotalAmountText() {
        return this.waitForElementVisibility(getParent(), Duration.ofSeconds(10))
                .findElement(this.totalAmount)
                .getText();
    }

    @Step("Get token symbol text")
    public String getSymbolText() {
        return this.waitForElementVisibility(getParent(), Duration.ofSeconds(10))
                .findElement(this.symbol)
                .getText();
    }

    @Step("Get token total value text")
    public String getTotalValueText() {
        return this.waitForElementVisibility(getParent(), Duration.ofSeconds(10))
                .findElement(this.totalTokenValue)
                .getText();
    }

}
