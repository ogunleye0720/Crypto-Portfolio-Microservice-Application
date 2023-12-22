package pl.kargolek.pages.balance.component.ethereum;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.kargolek.pages.balance.component.TableAmountPage;

/**
 * @author Karol Kuta-Orlowicz
 */
public class EthereumTableAmountPage extends TableAmountPage {

    @FindBy(css = "app-ethereum-balance")
    private WebElement ethereumBalanceComponent;

    public EthereumTableAmountPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get ethereum balance component")
    @Override
    protected WebElement getParent() {
        return this.ethereumBalanceComponent;
    }
}
