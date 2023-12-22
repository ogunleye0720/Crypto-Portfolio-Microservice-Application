package pl.kargolek.pages.balance;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.kargolek.pages.BasePage;
import pl.kargolek.pages.balance.component.avalanche.AvalancheTableActionBarPage;
import pl.kargolek.pages.balance.component.avalanche.AvalancheTableAmountPage;
import pl.kargolek.pages.balance.component.avalanche.AvalancheTableBalancePage;
import pl.kargolek.pages.balance.component.ethereum.EthereumTableActionBarPage;
import pl.kargolek.pages.balance.component.ethereum.EthereumTableAmountPage;
import pl.kargolek.pages.balance.component.ethereum.EthereumTableBalancePage;
import pl.kargolek.pages.balance.component.polygon.PolygonTableActionBarPage;
import pl.kargolek.pages.balance.component.polygon.PolygonTableAmountPage;
import pl.kargolek.pages.balance.component.polygon.PolygonTableBalancePage;
import pl.kargolek.pages.balance.totals.TotalValuePage;
import pl.kargolek.pages.balance.totals.treemap.TokenTreeMapChartPage;

import java.time.Duration;

/**
 * @author Karol Kuta-Orlowicz
 */
public class WalletBalancePage extends BasePage {
    @FindBy(css = "app-ethereum-balance .amount-container")
    private WebElement ethereumAmountContainer;

    @FindBy(css = "app-polygon-balance .amount-container")
    private WebElement polygonAmountContainer;

    @FindBy(css = "app-avalanche-balance .amount-container")
    private WebElement avalancheAmountContainer;

    public WalletBalancePage(WebDriver driver) {
        super(driver);
    }

    @Step("Get total amounts container of ethereum token")
    public WebElement getEthBalanceAmountContainer() {
        return this.waitForElementVisibility(ethereumAmountContainer, Duration.ofSeconds(10));
    }

    @Step("Get total amounts container of polygon token")
    public WebElement getPolygonBalanceAmountContainer() {
        return this.waitForElementVisibility(polygonAmountContainer, Duration.ofSeconds(10));
    }

    @Step("Get total amounts container of avalanche token")
    public WebElement getAvalancheAmountContainer() {
        return this.waitForElementVisibility(avalancheAmountContainer, Duration.ofSeconds(10));
    }

    @Step("Get ethereum table amount page")
    public EthereumTableAmountPage getEthereumTableAmountPage() {
        return new EthereumTableAmountPage(this.driver);
    }

    @Step("Get ethereum table action bar page")
    public EthereumTableActionBarPage getEthereumTableActionBarPage() {
        return new EthereumTableActionBarPage(this.driver);
    }

    @Step("Get ethereum table balance page")
    public EthereumTableBalancePage getEthereumTableBalancePage() {
        return new EthereumTableBalancePage(this.driver);
    }

    @Step("Get polygon table amount page")
    public PolygonTableAmountPage getPolygonTableAmountPage() {
        return new PolygonTableAmountPage(this.driver);
    }

    @Step("Get polygon table action bar page")
    public PolygonTableActionBarPage getPolygonTableActionBarPage() {
        return new PolygonTableActionBarPage(this.driver);
    }

    @Step("Get polygon table balance page")
    public PolygonTableBalancePage getPolygonTableBalancePage() {
        return new PolygonTableBalancePage(this.driver);
    }

    @Step("Get avalanche table amount page")
    public AvalancheTableAmountPage getAvalancheTableAmountPage() {
        return new AvalancheTableAmountPage(this.driver);
    }

    @Step("Get avalanche table action page")
    public AvalancheTableActionBarPage getAvalancheTableActionBarPage() {
        return new AvalancheTableActionBarPage(this.driver);
    }

    @Step("Get avalanche table balance page")
    public AvalancheTableBalancePage getAvalancheTableBalancePage() {
        return new AvalancheTableBalancePage(this.driver);
    }

    @Step("Get total value page")
    public TotalValuePage getTotalValuePage() {
        return new TotalValuePage(this.driver);
    }

    @Step("Get token tree map chart page")
    public TokenTreeMapChartPage getTokenTreeMapChartPage() {
        return new TokenTreeMapChartPage(this.driver);
    }

}
