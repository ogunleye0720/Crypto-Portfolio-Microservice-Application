package pl.kargolek.pages.balance.component;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.kargolek.pages.BasePage;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * @author Karol Kuta-Orlowicz
 */
public abstract class TableBalancePage extends BasePage {

    private final By headerAddress = By.cssSelector(".header-address");
    private final By headerAmount = By.cssSelector(".header-amount");
    private final By headerTokenValue = By.cssSelector(".header-value");
    private final By header1hValue = By.cssSelector(".header-1h");
    private final By header24hValue = By.cssSelector(".header-24h");
    private final By header7dValue = By.cssSelector(".header-7d");
    private final By header30dValue = By.cssSelector(".header-30d");
    private final By header60dValue = By.cssSelector(".header-60d");
    private final By header90dValue = By.cssSelector(".header-90d");
    private final By cellAddress = By.cssSelector(".cell-address");
    private final By cellAmount = By.cssSelector(".cell-amount");
    private final By cellTokenValue = By.cssSelector(".cell-value");
    private final By cell1hValue = By.cssSelector(".cell-1h");
    private final By cell24hValue = By.cssSelector(".cell-24h");
    private final By cell7dValue = By.cssSelector(".cell-7d");
    private final By cell30dValue = By.cssSelector(".cell-30d");
    private final By cell60dValue = By.cssSelector(".cell-60d");
    private final By cell90dValue = By.cssSelector(".cell-90d");

    public TableBalancePage(WebDriver driver) {
        super(driver);
    }

    public abstract WebElement getParent();

    @Step("Get header address")
    public WebElement getHeaderAddress() {
        return this.waitForElementVisibility(getParent(), Duration.ofSeconds(10))
                .findElement(headerAddress);
    }

    @Step("Get header amount")
    public WebElement getHeaderAmount() {
        return this.waitForElementVisibility(getParent(), Duration.ofSeconds(10))
                .findElement(headerAmount);
    }
    @Step("Get header token value")
    public WebElement getHeaderTokenValue() {
        return this.waitForElementVisibility(getParent(), Duration.ofSeconds(10))
                .findElement(headerTokenValue);
    }

    @Step("Get header 1h value")
    public WebElement getHeader1h() {
        return this.waitForElementVisibility(getParent(), Duration.ofSeconds(10))
                .findElement(header1hValue);
    }

    @Step("Get header 24h value")
    public WebElement getHeader24h() {
        return this.waitForElementVisibility(getParent(), Duration.ofSeconds(10))
                .findElement(header24hValue);
    }
    @Step("Get header 7d value")
    public WebElement getHeader7d() {
        return this.waitForElementVisibility(getParent(), Duration.ofSeconds(10))
                .findElement(header7dValue);
    }

    @Step("Get header 30d value")
    public WebElement getHeader30d() {
        return this.waitForElementVisibility(getParent(), Duration.ofSeconds(10))
                .findElement(header30dValue);
    }

    @Step("Get header 60d value")
    public WebElement getHeader60d() {
        return this.waitForElementVisibility(getParent(), Duration.ofSeconds(10))
                .findElement(header60dValue);
    }

    @Step("Get header 90d value")
    public WebElement getHeader90d() {
        return this.waitForElementVisibility(getParent(), Duration.ofSeconds(10))
                .findElement(header90dValue);
    }

    @Step("Get last cell address column")
    public WebElement getLastCellAddress() {
        var elements = this.getAddressCells();
        Collections.reverse(elements);
        return elements.stream()
                .findFirst()
                .orElseThrow();
    }

    @Step("Get last cell amount column")
    public WebElement getLastCellAmount() {
        var elements = this.getAmountCells();
        Collections.reverse(elements);
        return elements.stream()
                .findFirst()
                .orElseThrow();
    }

    @Step("Get last cell token value column")
    public WebElement getLastCellTokenValue() {
        var elements = this.getTokenValueCells();
        Collections.reverse(elements);
        return elements.stream()
                .findFirst()
                .orElseThrow();
    }

    @Step("Get last cell 1h value column")
    public WebElement getLastCell1hValue() {
        var elements = this.get1hValueCells();
        Collections.reverse(elements);
        return this.get1hValueCells().stream()
                .findFirst()
                .orElseThrow();
    }

    @Step("Get last cell 24h value column")
    public WebElement getLastCell24hValue() {
        var elements = this.get24hValueCells();
        Collections.reverse(elements);
        return elements.stream()
                .findFirst()
                .orElseThrow();
    }

    @Step("Get last cell 7d value column")
    public WebElement getLastCell7dValue() {
        var elements = this.get7dValueCells();
        Collections.reverse(elements);
        return elements.stream()
                .findFirst()
                .orElseThrow();
    }

    @Step("Get last cell 30d value column")
    public WebElement getLastCell30dValue() {
        var elements = this.get30dValueCells();
        Collections.reverse(elements);
        return elements.stream()
                .findFirst()
                .orElseThrow();
    }

    @Step("Get last cell 60d value column")
    public WebElement getLastCell60dValue() {
        var elements = this.get60dValueCells();
        Collections.reverse(elements);
        return elements.stream()
                .findFirst()
                .orElseThrow();
    }

    @Step("Get last cell 90d value column")
    public WebElement getLastCell90dValue() {
        var elements = this.get90ValueCells();
        Collections.reverse(elements);
        return elements.stream()
                .findFirst()
                .orElseThrow();
    }

    @Step("Get first cell address column")
    public WebElement getFirstCellAddress() {
        return this.getAddressCells().stream()
                .findFirst()
                .orElseThrow();
    }

    @Step("Get first cell amount column")
    public WebElement getFirstCellAmount() {
        return this.getAmountCells().stream()
                .findFirst()
                .orElseThrow();
    }

    @Step("Get first cell token value column")
    public WebElement getFirstCellTokenValue() {
        return this.getTokenValueCells().stream()
                .findFirst()
                .orElseThrow();
    }

    @Step("Get first cell 1h value column")
    public WebElement getFirstCell1hValue() {
        return this.get1hValueCells().stream()
                .findFirst()
                .orElseThrow();
    }
    @Step("Get first cell 24h value column")
    public WebElement getFirstCell24hValue() {
        return this.get24hValueCells().stream()
                .findFirst()
                .orElseThrow();
    }

    @Step("Get first cell 7d value column")
    public WebElement getFirstCell7dValue() {
        return this.get7dValueCells().stream()
                .findFirst()
                .orElseThrow();
    }

    @Step("Get first cell 30d value column")
    public WebElement getFirstCell30dValue() {
        return this.get30dValueCells().stream()
                .findFirst()
                .orElseThrow();
    }

    @Step("Get first cell 60d value column")
    public WebElement getFirstCell60dValue() {
        return this.get60dValueCells().stream()
                .findFirst()
                .orElseThrow();
    }

    @Step("Get first cell 90d value column")
    public WebElement getFirstCell90dValue() {
        return this.get90ValueCells().stream()
                .findFirst()
                .orElseThrow();
    }

    private List<WebElement> getAddressCells() {
        return this.waitForElementVisibility(this.getParent(), Duration.ofSeconds(10))
                .findElements(cellAddress);
    }

    private List<WebElement> getAmountCells() {
        return this.waitForElementVisibility(this.getParent(), Duration.ofSeconds(10))
                .findElements(cellAmount);
    }

    private List<WebElement> getTokenValueCells() {
        return this.waitForElementVisibility(this.getParent(), Duration.ofSeconds(10))
                .findElements(cellTokenValue);
    }

    private List<WebElement> get1hValueCells() {
        return this.waitForElementVisibility(this.getParent(), Duration.ofSeconds(10))
                .findElements(cell1hValue);
    }

    private List<WebElement> get24hValueCells() {
        return this.waitForElementVisibility(this.getParent(), Duration.ofSeconds(10))
                .findElements(cell24hValue);
    }

    private List<WebElement> get7dValueCells() {
        return this.waitForElementVisibility(this.getParent(), Duration.ofSeconds(10))
                .findElements(cell7dValue);
    }

    private List<WebElement> get30dValueCells() {
        return this.waitForElementVisibility(this.getParent(), Duration.ofSeconds(10))
                .findElements(cell30dValue);
    }

    private List<WebElement> get60dValueCells() {
        return this.waitForElementVisibility(this.getParent(), Duration.ofSeconds(10))
                .findElements(cell60dValue);
    }

    private List<WebElement> get90ValueCells() {
        return this.waitForElementVisibility(this.getParent(), Duration.ofSeconds(10))
                .findElements(cell90dValue);
    }
}
