package pl.kargolek.pages.balance.totals.treemap;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.kargolek.pages.BasePage;

import java.time.Duration;

/**
 * @author Karol Kuta-Orlowicz
 */
public class TokenTreeMapChartPage extends BasePage {

    @FindBy(css = "app-total-value .tree-map")
    private WebElement chartContainer;

    @FindBy(xpath = "//*[@class='cell' and @fill='#393939']")
    private WebElement ethereumCell;

    @FindBy(xpath = "//*[@class='cell' and @fill='#7e45de']")
    private WebElement polygonCell;

    @FindBy(xpath = "//*[@class='cell' and @fill='#e84142']")
    private WebElement avalancheCell;

    @FindBy(css = ".tree-map-chart-container .no-data-label")
    private WebElement noDataLabel;

    @FindBy(xpath = "//*[@class='treemap-label' and text()='ETH']")
    private WebElement ethereumSymbolLabel;

    @FindBy(xpath = "//*[@class='treemap-label' and text()='MATIC']")
    private WebElement polygonSymbolLabel;

    @FindBy(xpath = "//*[@class='treemap-label' and text()='AVAX']")
    private WebElement avalancheSymbolLabel;

    @FindBy(xpath = "//*[@class='treemap-label' and text()='ETH']//..//span[@class='treemap-val ng-star-inserted']")
    private WebElement ethereumBalanceLabel;

    @FindBy(xpath = "//*[@class='treemap-label' and text()='MATIC']//..//span[@class='treemap-val ng-star-inserted']")
    private WebElement polygonBalanceLabel;

    @FindBy(xpath = "//*[@class='treemap-label' and text()='AVAX']//..//span[@class='treemap-val ng-star-inserted']")
    private WebElement avalancheBalanceLabel;

    public TokenTreeMapChartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get tree map chart container")
    public WebElement getChartContainer() {
        return this.waitForElementVisibility(this.chartContainer, Duration.ofSeconds(10));
    }

    @Step("Get chart ethereum cell")
    public WebElement getEthereumCell() {
        return this.waitForElementVisibility(this.ethereumCell, Duration.ofSeconds(10));
    }

    @Step("Get chart polygon cell")
    public WebElement getPolygonCell() {
        return this.waitForElementVisibility(this.polygonCell, Duration.ofSeconds(10));
    }

    @Step("Get chart avalanche cell")
    public WebElement getAvalancheCell() {
        return this.waitForElementVisibility(this.avalancheCell, Duration.ofSeconds(10));
    }

    @Step("Get no data label")
    public WebElement getNoDataLabel() {
        return this.waitForElementVisibility(this.noDataLabel, Duration.ofSeconds(10));
    }

    @Step("Get ethereum symbol label")
    public String getEthereumSymbolLabelText() {
        return this.waitForElementVisibility(this.ethereumSymbolLabel, Duration.ofSeconds(10)).getText();
    }

    @Step("Get polygon symbol label")
    public String getPolygonSymbolLabelText() {
        return this.waitForElementVisibility(this.polygonSymbolLabel, Duration.ofSeconds(10)).getText();
    }

    @Step("Get avalanche symbol label")
    public String getAvalancheSymbolLabelText() {
        return this.waitForElementVisibility(this.avalancheSymbolLabel, Duration.ofSeconds(10)).getText();
    }

    @Step("Get ethereum balance label")
    public String getEthereumBalanceLabelText() {
        return this.waitForElementVisibility(this.ethereumBalanceLabel, Duration.ofSeconds(10)).getText();
    }

    @Step("Get polygon balance label")
    public String getPolygonBalanceLabelText() {
        return this.waitForElementVisibility(this.polygonBalanceLabel, Duration.ofSeconds(10)).getText();
    }

    @Step("Get avalanche balance label")
    public String getAvalancheBalanceLabelText() {
        return this.waitForElementVisibility(this.avalancheBalanceLabel, Duration.ofSeconds(10)).getText();
    }

}
