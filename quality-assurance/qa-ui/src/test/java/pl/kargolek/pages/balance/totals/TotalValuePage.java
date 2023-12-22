package pl.kargolek.pages.balance.totals;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.kargolek.pages.BasePage;

import java.time.Duration;

/**
 * @author Karol Kuta-Orlowicz
 */
public class TotalValuePage extends BasePage {

    @FindBy(css = ".total-value-container .total-value-data")
    private WebElement totalValue;

    @FindBy(css = ".total-value-diff-quote")
    private WebElement totalQuoteDiff;

    @FindBy(css = ".total-value-diff-percent")
    private WebElement totalPercentDiff;

    @FindBy(css = ".value-chart-container")
    private WebElement trendLineChartContainer;

    @FindBy(css = "app-trend-balance-chart .ng-animating")
    private WebElement trendAnimation;

    @FindBy(css = "app-total-value .total-value-label-trend")
    private WebElement trendLineLabel;

    public TotalValuePage(WebDriver driver) {
        super(driver);
    }

    @Step("Get total value text")
    public String getTotalValueText() {
        return this.waitForElementVisibility(totalValue, Duration.ofSeconds(10)).getText();
    }

    @Step("Wait for text change from 0.00 USD")
    public TotalValuePage waitForTotalValueTextChange() {
        this.waitForElementVisibility(totalValue, Duration.ofSeconds(10));
        this.waitForElementTextToChange(totalValue, "0.00 USD", Duration.ofSeconds(6));
        return this;
    }

    @Step("Get total value quote difference")
    public WebElement getTotalValueDiffQuote(){
        return this.waitForElementVisibility(totalQuoteDiff, Duration.ofSeconds(10));
    }

    @Step("Get total value percent difference")
    public WebElement getTotalValueDiffPercent(){
        return this.waitForElementVisibility(totalPercentDiff, Duration.ofSeconds(10));
    }

    @Step("Click on the total value")
    public TotalValuePage clickTotalValue(){
        this.waitForElementVisibility(totalValue, Duration.ofSeconds(10)).click();
        return this;
    }

    @Step("Get trend line chart container")
    public WebElement getTrendLineChartContainer(){
        return this.waitForElementVisibility(trendLineChartContainer, Duration.ofSeconds(10));
    }

    @Step("Wait for trend chart animation stop")
    public TotalValuePage waitForStopAnimation(){
        this.waitForElementInvisibility(trendAnimation, Duration.ofSeconds(5));
        return this;
    }

    @Step("Get trend line chart label")
    public WebElement getTrendChartLabel(){
        return this.waitForElementVisibility(this.trendLineLabel, Duration.ofSeconds(10));
    }

}
