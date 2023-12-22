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
import pl.kargolek.extension.javascript.JavascriptDriverExecutor;
import pl.kargolek.pages.InitPages;
import pl.kargolek.util.TestProperty;

/**
 * @author Karol Kuta-Orlowicz
 */

@Tag("TokenBalanceUI")
@Epic("Token balance")
@Feature("Token balance UI")
@BaseTestConfig
@TestDataProvider
@SoftAssertion
@JavascriptDriverExecutor
@Isolated
public class BalanceUITest {

    private String appBaseURL;
    private InitPages pages;

    @BeforeEach
    public void setup(TestProperty property, InitPages pages, TestDataSql testDataSql) {
        this.appBaseURL = property.getAppBaseURL();
        this.pages = pages;
        testDataSql.deleteCryptocurrenciesData();
        testDataSql.insertEthereumData();
        testDataSql.insertPolygonData();
        testDataSql.insertAvalancheData();
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Story("As user I can see color green indication for higher 1h, 24h, 7d, 30d, 60d, 90d past values")
    @Description("Higher past token values should have color green: rgba(7, 243, 107, 1)")
    public void whenCurrentPriceLowerThanPastPrices_thenValuesAreGreen(TestDataSql dataSql,
                                                                       TestData data,
                                                                       SoftAssertions softAssertions) {
        dataSql.deleteCryptocurrenciesData();
        dataSql.insertEthereumDataPricePastNegative();
        dataSql.insertPolygonData();
        dataSql.insertAvalancheData();

        var wallets = data.getAvalancheTestNetWallet().getAddress() + "," +
                data.getAvalancheTestNetWallet().getAddress();

        var ethTableActionBar = this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWallets(wallets)
                .enterKeyPress()
                .getEthereumTableActionBarPage();

        ethTableActionBar.clickSwitchViewButton();

        var element1hCssColorValue = this.pages.getWalletBalancePage()
                .getEthereumTableBalancePage()
                .getFirstCell1hValue()
                .getCssValue("color");

        var element24hCssColorValue = this.pages.getWalletBalancePage()
                .getEthereumTableBalancePage()
                .getFirstCell24hValue()
                .getCssValue("color");

        var element7dCssColorValue = this.pages.getWalletBalancePage()
                .getEthereumTableBalancePage()
                .getFirstCell7dValue()
                .getCssValue("color");

        ethTableActionBar.clickSwitchViewButton();

        var element30dCssColorValue = this.pages.getWalletBalancePage()
                .getEthereumTableBalancePage()
                .getFirstCell30dValue()
                .getCssValue("color");

        var element60dCssColorValue = this.pages.getWalletBalancePage()
                .getEthereumTableBalancePage()
                .getFirstCell60dValue()
                .getCssValue("color");

        var element90dCssColorValue = this.pages.getWalletBalancePage()
                .getEthereumTableBalancePage()
                .getFirstCell90dValue()
                .getCssValue("color");

        softAssertions.assertThat(element1hCssColorValue)
                .isIn(data.getGreenColorHigherValues(), data.getGreenColorHigherValuesFirefox());

        softAssertions.assertThat(element24hCssColorValue)
                .isIn(data.getGreenColorHigherValues(), data.getGreenColorHigherValuesFirefox());

        softAssertions.assertThat(element7dCssColorValue)
                .isIn(data.getGreenColorHigherValues(), data.getGreenColorHigherValuesFirefox());

        softAssertions.assertThat(element30dCssColorValue)
                .isIn(data.getGreenColorHigherValues(), data.getGreenColorHigherValuesFirefox());

        softAssertions.assertThat(element60dCssColorValue)
                .isIn(data.getGreenColorHigherValues(), data.getGreenColorHigherValuesFirefox());

        softAssertions.assertThat(element90dCssColorValue)
                .isIn(data.getGreenColorHigherValues(), data.getGreenColorHigherValuesFirefox());

        softAssertions.assertAll();
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Story("As user I can see color red indication for lower 1h, 24h, 7d, 30d, 60d, 90d past values")
    @Description("Lower past token values should have color red: rgba(255, 77, 0, 1)")
    public void whenCurrentPriceHigherThanPastPrices_thenValuesAreRed(TestData data, SoftAssertions softAssertions) {
        var wallets = data.getAvalancheTestNetWallet().getAddress() + "," +
                data.getAvalancheTestNetWallet().getAddress();

        var ethTableActionBar = this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWallets(wallets)
                .enterKeyPress()
                .getEthereumTableActionBarPage();

        ethTableActionBar.clickSwitchViewButton();

        var element1hCssColorValue = this.pages.getWalletBalancePage()
                .getEthereumTableBalancePage()
                .getFirstCell1hValue()
                .getCssValue("color");

        var element24hCssColorValue = this.pages.getWalletBalancePage()
                .getEthereumTableBalancePage()
                .getFirstCell24hValue()
                .getCssValue("color");

        var element7dCssColorValue = this.pages.getWalletBalancePage()
                .getEthereumTableBalancePage()
                .getFirstCell7dValue()
                .getCssValue("color");

        ethTableActionBar.clickSwitchViewButton();

        var element30dCssColorValue = this.pages.getWalletBalancePage()
                .getEthereumTableBalancePage()
                .getFirstCell30dValue()
                .getCssValue("color");

        var element60dCssColorValue = this.pages.getWalletBalancePage()
                .getEthereumTableBalancePage()
                .getFirstCell60dValue()
                .getCssValue("color");

        var element90dCssColorValue = this.pages.getWalletBalancePage()
                .getEthereumTableBalancePage()
                .getFirstCell90dValue()
                .getCssValue("color");

        softAssertions.assertThat(element1hCssColorValue)
                .isIn(data.getRedColorLowerValues(), data.getRedColorLowerValuesFirefox());

        softAssertions.assertThat(element24hCssColorValue)
                .isIn(data.getRedColorLowerValues(), data.getRedColorLowerValuesFirefox());

        softAssertions.assertThat(element7dCssColorValue)
                .isIn(data.getRedColorLowerValues(), data.getRedColorLowerValuesFirefox());

        softAssertions.assertThat(element30dCssColorValue)
                .isIn(data.getRedColorLowerValues(), data.getRedColorLowerValuesFirefox());

        softAssertions.assertThat(element60dCssColorValue)
                .isIn(data.getRedColorLowerValues(), data.getRedColorLowerValuesFirefox());

        softAssertions.assertThat(element90dCssColorValue)
                .isIn(data.getRedColorLowerValues(), data.getRedColorLowerValuesFirefox());

        softAssertions.assertAll();
    }
}