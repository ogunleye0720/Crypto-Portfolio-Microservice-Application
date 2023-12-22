package pl.kargolek.pages.balance.component.ethereum;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.kargolek.pages.balance.component.TableBalancePage;

/**
 * @author Karol Kuta-Orlowicz
 */
public class EthereumTableBalancePage extends TableBalancePage {

    @FindBy(css = "app-ethereum-balance")
    private WebElement ethereumBalanceComponent;

    public EthereumTableBalancePage(WebDriver driver) {
        super(driver);
    }

    @Step("Get ethereum balance component")
    @Override
    public WebElement getParent() {
        return this.ethereumBalanceComponent;
    }
}
