package pl.kargolek.tests;

import io.qameta.allure.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;
import pl.kargolek.data.TestData;
import pl.kargolek.data.script.TestDataSql;
import pl.kargolek.extension.BaseTestConfig;
import pl.kargolek.extension.assertion.SoftAssertion;
import pl.kargolek.extension.data.TestDataProvider;
import pl.kargolek.pages.InitPages;
import pl.kargolek.util.TestProperty;

/**
 * @author Karol Kuta-Orlowicz
 */

@Tag("TotalValue")
@Epic("Token balance")
@Feature("Total value service")
@BaseTestConfig
@TestDataProvider
@SoftAssertion
@Isolated
public class TotalValueServiceTest {

    private String baseURL;
    private InitPages pages;

    @BeforeEach
    public void setup(TestProperty property, InitPages pages, TestDataSql dataSql) {
        dataSql.deleteCryptocurrenciesData();
        dataSql.insertEthereumData();
        dataSql.insertAvalancheData();
        dataSql.insertPolygonData();
        this.baseURL = property.getAppBaseURL();
        this.pages = pages;
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("As user for total diff values I want to see color indication for lower past value " +
            "in quote and percentage value")
    @Description("Higher values should have color green: rgba(255, 77, 0, 1)")
    public void whenTotalDiffValuesLower_thenIndicateByRedColor(TestData data,
                                                                TestDataSql dataSql,
                                                                SoftAssertions softAssertions) {
        dataSql.deleteCryptocurrenciesData();
        dataSql.insertEthereumDataPricePastNegative();
        dataSql.insertPolygonDataPricePastNegative();
        dataSql.insertAvalancheDataPricePastNegative();

        var walletData = data.getEthereumTestNetWallet().getAddress();

        this.pages.getHomePage()
                .open(this.baseURL)
                .inputWalletsClearText()
                .inputWallets(walletData)
                .enterKeyPress();

        this.pages.getWalletBalancePage()
                .getEthereumTableAmountPage()
                .getTotalAmountText();

        var totalValuePage = this.pages.getWalletBalancePage()
                .getTotalValuePage();

        var expectedColorDiffQuote1h = totalValuePage
                .getTotalValueDiffQuote()
                .getCssValue("color");

        var expectedColorPercent1h = totalValuePage
                .getTotalValueDiffPercent()
                .getCssValue("color");

        totalValuePage.clickTotalValue();

        var expectedColorDiffQuote24h = totalValuePage
                .getTotalValueDiffQuote()
                .getCssValue("color");

        var expectedColorPercent24h = totalValuePage
                .getTotalValueDiffPercent()
                .getCssValue("color");

        totalValuePage.clickTotalValue();

        var expectedColorDiffQuote7d = totalValuePage
                .getTotalValueDiffQuote()
                .getCssValue("color");

        var expectedColorPercent7d = totalValuePage
                .getTotalValueDiffPercent()
                .getCssValue("color");

        softAssertions.assertThat(expectedColorDiffQuote1h)
                .isIn(data.getRedColorLowerValues(), data.getRedColorLowerValuesFirefox());

        softAssertions.assertThat(expectedColorPercent1h)
                .isIn(data.getRedColorLowerValues(), data.getRedColorLowerValuesFirefox());

        softAssertions.assertThat(expectedColorDiffQuote24h)
                .isIn(data.getRedColorLowerValues(), data.getRedColorLowerValuesFirefox());

        softAssertions.assertThat(expectedColorPercent24h)
                .isIn(data.getRedColorLowerValues(), data.getRedColorLowerValuesFirefox());

        softAssertions.assertThat(expectedColorDiffQuote7d)
                .isIn(data.getRedColorLowerValues(), data.getRedColorLowerValuesFirefox());

        softAssertions.assertThat(expectedColorPercent7d)
                .isIn(data.getRedColorLowerValues(), data.getRedColorLowerValuesFirefox());

        softAssertions.assertAll();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("As user for total diff values I want to see color indication for higher past value " +
            "in quote and percentage value")
    @Description("Higher values should have color green: rgba(7, 243, 107, 1)")
    public void whenTotalDiffValuesHigher_thenIndicateByGreenColor(TestData data, SoftAssertions softAssertions) {
        var walletData = data.getEthereumTestNetWallet().getAddress();

        this.pages.getHomePage()
                .open(this.baseURL)
                .inputWalletsClearText()
                .inputWallets(walletData)
                .enterKeyPress();

        this.pages.getWalletBalancePage()
                .getEthereumTableAmountPage()
                .getTotalAmountText();

        var totalValuePage = this.pages.getWalletBalancePage()
                .getTotalValuePage();

        var expectedColorDiffQuote1h = totalValuePage
                .getTotalValueDiffQuote()
                .getCssValue("color");

        var expectedColorPercent1h = totalValuePage
                .getTotalValueDiffPercent()
                .getCssValue("color");

        totalValuePage.clickTotalValue();

        var expectedColorDiffQuote24h = totalValuePage
                .getTotalValueDiffQuote()
                .getCssValue("color");

        var expectedColorPercent24h = totalValuePage
                .getTotalValueDiffPercent()
                .getCssValue("color");

        totalValuePage.clickTotalValue();

        var expectedColorDiffQuote7d = totalValuePage
                .getTotalValueDiffQuote()
                .getCssValue("color");

        var expectedColorPercent7d = totalValuePage
                .getTotalValueDiffPercent()
                .getCssValue("color");

        softAssertions.assertThat(expectedColorDiffQuote1h)
                .isIn(data.getGreenColorHigherValues(), data.getGreenColorHigherValuesFirefox());

        softAssertions.assertThat(expectedColorPercent1h)
                .isIn(data.getGreenColorHigherValues(), data.getGreenColorHigherValuesFirefox());

        softAssertions.assertThat(expectedColorDiffQuote24h)
                .isIn(data.getGreenColorHigherValues(), data.getGreenColorHigherValuesFirefox());

        softAssertions.assertThat(expectedColorPercent24h)
                .isIn(data.getGreenColorHigherValues(), data.getGreenColorHigherValuesFirefox());

        softAssertions.assertThat(expectedColorDiffQuote7d)
                .isIn(data.getGreenColorHigherValues(), data.getGreenColorHigherValuesFirefox());

        softAssertions.assertThat(expectedColorPercent7d)
                .isIn(data.getGreenColorHigherValues(), data.getGreenColorHigherValuesFirefox());

        softAssertions.assertAll();
    }

}
