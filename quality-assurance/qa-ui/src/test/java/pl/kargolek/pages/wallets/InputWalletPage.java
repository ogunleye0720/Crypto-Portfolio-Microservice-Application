package pl.kargolek.pages.wallets;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.kargolek.pages.BasePage;
import pl.kargolek.pages.balance.WalletBalancePage;

import java.time.Duration;

/**
 * @author Karol Kuta-Orlowicz
 */
public class InputWalletPage extends BasePage {

    @FindBy(id = "input-wallets")
    private WebElement inputWalletField;

    @FindBy(xpath = ".//div[@class='wallet-input-container']/button")
    private WebElement sendWalletsButton;

    @FindBy(css = ".title")
    private WebElement appTitle;

    @FindBy(css = ".crypto_type_title")
    private WebElement tokenDescription;

    public InputWalletPage(WebDriver driver) {
        super(driver);
    }

    @Step("Type wallet addresses: {0}")
    public InputWalletPage inputWallets(String wallets){
        this.waitForElementVisibility(inputWalletField, Duration.ofSeconds(15)).sendKeys(wallets);
        return this;
    }

    @Step("Clear text in input wallet field")
    public InputWalletPage inputWalletsClearText(){
        this.waitForElementVisibility(inputWalletField, Duration.ofSeconds(10)).clear();
        return this;
    }

    @Step("Click send key button")
    public WalletBalancePage sendWalletsButtonClick(){
        this.waitForElementVisibility(sendWalletsButton, Duration.ofSeconds(10)).click();
        return new WalletBalancePage(driver);
    }

    @Step("Click enter key")
    public WalletBalancePage enterKeyPress(){
        this.action.sendKeys(Keys.ENTER);
        this.action.perform();
        return new WalletBalancePage(driver);
    }

    @Step("Get app title text")
    public String getAppTitleText(){
        return this.waitForElementVisibility(this.appTitle, Duration.ofSeconds(10)).getText();
    }

    @Step("Get crypto type description text")
    public String getTokenDescriptionText(){
        return this.waitForElementVisibility(this.tokenDescription, Duration.ofSeconds(10)).getText();
    }

    @Step("Get input wallet text")
    public String getInputWalletText(){
        return this.waitForElementVisibility(this.inputWalletField, Duration.ofSeconds(10)).getAttribute("value");
    }
    @Step("Get placeholder text")
    public String getInputWalletPlaceholder(){
        return this.waitForElementVisibility(this.inputWalletField, Duration.ofSeconds(10)).getAttribute("placeholder");
    }
}
