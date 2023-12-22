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
public abstract class TableActionBarPage extends BasePage {

    private final By switchButton = By.cssSelector(".table-action-button");
    private final By numberWallet = By.cssSelector(".number-wallets");

    public TableActionBarPage(WebDriver driver) {
        super(driver);
    }

    protected abstract WebElement getParent();

    @Step("Click on switch balance table view")
    public void clickSwitchViewButton() {
        this.waitForElementVisibility(getParent(), Duration.ofSeconds(10))
                .findElement(switchButton)
                .click();
    }

    @Step("Get text number wallets")
    public String getNumberWalletsText() {
        return this.waitForElementVisibility(getParent(), Duration.ofSeconds(10))
                .findElement(this.numberWallet)
                .getText();
    }

}
