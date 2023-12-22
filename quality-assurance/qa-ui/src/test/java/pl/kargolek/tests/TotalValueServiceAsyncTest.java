package pl.kargolek.tests;

import io.qameta.allure.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pl.kargolek.data.TestData;
import pl.kargolek.data.script.TestDataSql;
import pl.kargolek.extension.BaseTestConfig;
import pl.kargolek.extension.assertion.SoftAssertion;
import pl.kargolek.extension.data.TestDataProvider;
import pl.kargolek.pages.InitPages;
import pl.kargolek.util.TestProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Karol Kuta-Orlowicz
 */

@Tag("TotalValue")
@Epic("Token balance")
@Feature("Total value service")
@BaseTestConfig
@TestDataProvider
@SoftAssertion
public class TotalValueServiceAsyncTest {

    private String baseURL;
    private InitPages pages;

    @BeforeAll
    public static void setupAll(TestDataSql dataSql){
        dataSql.deleteCryptocurrenciesData();
        dataSql.insertEthereumData();
        dataSql.insertAvalancheData();
        dataSql.insertPolygonData();
    }

    @BeforeEach
    public void setup(TestProperty property, InitPages pages) {
        this.baseURL = property.getAppBaseURL();
        this.pages = pages;
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("As user I want see proper calculation of my wallet with coins at the top of balance page")
    @Description("Total value is number which represents total value of each token balance")
    public void whenProvideOneWallet_thenProperTotalValueData(TestData data) {
        this.pages.getHomePage()
                .open(this.baseURL)
                .inputWalletsClearText()
                .inputWallets(data.getEthereumTestNetWallet().getAddress())
                .enterKeyPress();

        var result = countTotalValueBaseTestData(data, new BigDecimal("1"));

        var expected = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .waitForTotalValueTextChange()
                .getTotalValueText();

        assertThat(expected)
                .containsOnlyOnce(result);
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("As user I want see proper calculation of my wallet without coins at the top of balance page")
    @Description("Total value is number which represents total value of each token balance")
    public void whenProvideEmptyWallet_thenProperTotalValueData(TestData data) {
        this.pages.getHomePage()
                .open(this.baseURL)
                .inputWalletsClearText()
                .inputWallets(data.getEthereumTestNetWalletEmpty().getAddress())
                .enterKeyPress();

        var result = "0.00 USD";

        this.pages.getWalletBalancePage()
                .getEthereumTableAmountPage()
                .getTotalAmountText();

        var expected = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueText();

        assertThat(expected)
                .containsOnlyOnce(result);
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("As user I want see proper calculation of my wallet without and with coins at the top of balance page")
    @Description("Total value is number which represents total value of each token balance")
    public void whenProvideEmptyAndNotEmptyWallets_thenProperTotalValueData(TestData data) {
        var walletData = data.getEthereumTestNetWallet().getAddress() + "," +
                data.getEthereumTestNetWalletEmpty().getAddress();

        this.pages.getHomePage()
                .open(this.baseURL)
                .inputWalletsClearText()
                .inputWallets(walletData)
                .enterKeyPress();

        var result = countTotalValueBaseTestData(data, new BigDecimal("1"));

        this.pages.getWalletBalancePage()
                .getEthereumTableAmountPage()
                .getTotalAmountText();

        var expected = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueText();

        assertThat(expected)
                .containsOnlyOnce(result);
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("As user I want see proper calculation of my multiple wallets with coins at the top of balance page")
    @Description("Total value is number which represents total value of each token balance")
    public void whenProvideMultipleNotEmptyWallets_thenProperTotalValueData(TestData data) {
        var walletData = data.getEthereumTestNetWallet().getAddress() + "," +
                data.getEthereumTestNetWallet().getAddress() + "," +
                data.getEthereumTestNetWallet().getAddress() + "," +
                data.getEthereumTestNetWallet().getAddress() + "," +
                data.getEthereumTestNetWallet().getAddress();

        this.pages.getHomePage()
                .open(this.baseURL)
                .inputWalletsClearText()
                .inputWallets(walletData)
                .enterKeyPress();

        var result = countTotalValueBaseTestData(data, new BigDecimal("5"));

        this.pages.getWalletBalancePage()
                .getEthereumTableAmountPage()
                .getTotalAmountText();

        var expected = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueText();

        assertThat(expected)
                .containsOnlyOnce(result);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("As user when no empty I want to details calculation of past value for 1h, 24h, 7d " +
            "in quote and percentage value")
    @Description("Quote shows diff value in USD")
    public void whenWalletNotEmpty_thenTotalDiffShowsFor1h24h7d(TestData data, SoftAssertions softAssertions) throws InterruptedException {
        var walletData = data.getEthereumTestNetWallet().getAddress();

        //this.pages.getHomePage().getDriver().navigate().refresh();

        this.pages.getHomePage()
                .open(this.baseURL)
                .inputWalletsClearText()
                .inputWallets(walletData)
                .enterKeyPress();

        this.pages.getWalletBalancePage()
                .getEthereumTableAmountPage()
                .getTotalAmountText();

        //Thread.sleep(2000);

        var resultQuote1h = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueDiffQuote()
                .getText();

        var resultPercentage1h = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueDiffPercent()
                .getText();

        this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .clickTotalValue();

        var resultQuote24h = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueDiffQuote()
                .getText();

        var resultPercentage24h = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueDiffPercent()
                .getText();

        this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .clickTotalValue();

        var resultQuote7d = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueDiffQuote()
                .getText();

        var resultPercentage7d = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueDiffPercent()
                .getText();

        softAssertions.assertThat(resultQuote1h)
                .containsOnlyOnce("1h (213.63$)");

        softAssertions.assertThat(resultPercentage1h)
                .containsOnlyOnce("10.00%");

        softAssertions.assertThat(resultQuote24h)
                .containsOnlyOnce("24h (213.63$)");

        softAssertions.assertThat(resultPercentage24h)
                .containsOnlyOnce("10.00%");

        softAssertions.assertThat(resultQuote7d)
                .containsOnlyOnce("7d (213.63$)");

        softAssertions.assertThat(resultPercentage7d)
                .containsOnlyOnce("10.00%");

        softAssertions.assertAll();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("As user when multiple wallets I want to details calculation of past value for 1h, 24h, 7d " +
            "in quote and percentage value")
    @Description("Quote shows diff value in USD")
    public void whenMultipleWalletNotEmpty_thenTotalDiffShowsFor1h24h7d(TestData data, SoftAssertions softAssertions) {
        var walletData = data.getEthereumTestNetWallet().getAddress() + "," +
                data.getEthereumTestNetWallet().getAddress() + "," +
                data.getEthereumTestNetWallet().getAddress() + "," +
                data.getEthereumTestNetWallet().getAddress() + "," +
                data.getEthereumTestNetWallet().getAddress();

        this.pages.getHomePage()
                .open(this.baseURL)
                .inputWalletsClearText()
                .inputWallets(walletData)
                .enterKeyPress();

        this.pages.getWalletBalancePage()
                .getEthereumTableAmountPage()
                .getTotalAmountText();

        var resultQuote1h = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueDiffQuote()
                .getText();

        var resultPercentage1h = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueDiffPercent()
                .getText();

        this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .clickTotalValue();

        var resultQuote24h = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueDiffQuote()
                .getText();

        var resultPercentage24h = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueDiffPercent()
                .getText();

        this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .clickTotalValue();

        var resultQuote7d = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueDiffQuote()
                .getText();

        var resultPercentage7d = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueDiffPercent()
                .getText();

        softAssertions.assertThat(resultQuote1h)
                .containsOnlyOnce("1h (467.83$)");

        softAssertions.assertThat(resultPercentage1h)
                .containsOnlyOnce("10.00%");

        softAssertions.assertThat(resultQuote24h)
                .containsOnlyOnce("24h (467.83$)");

        softAssertions.assertThat(resultPercentage24h)
                .containsOnlyOnce("10.00%");

        softAssertions.assertThat(resultQuote7d)
                .containsOnlyOnce("7d (467.83$)");

        softAssertions.assertThat(resultPercentage7d)
                .containsOnlyOnce("10.00%");

        softAssertions.assertAll();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("As user when empty wallet I want to details calculation of past value for 1h, 24h, 7d " +
            "in quote and percentage value")
    @Description("Quote shows diff value in USD")
    public void whenWalletEmpty_thenTotalDiffShowsFor1h24h7d(TestData data, SoftAssertions softAssertions) {
        var walletData = data.getEthereumTestNetWalletEmpty().getAddress();

        this.pages.getHomePage()
                .open(this.baseURL)
                .inputWalletsClearText()
                .inputWallets(walletData)
                .enterKeyPress();

        this.pages.getWalletBalancePage()
                .getEthereumTableAmountPage()
                .getTotalAmountText();

        var resultQuote1h = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueDiffQuote()
                .getText();

        var resultPercentage1h = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueDiffPercent()
                .getText();

        this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .clickTotalValue();

        var resultQuote24h = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueDiffQuote()
                .getText();

        var resultPercentage24h = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueDiffPercent()
                .getText();

        this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .clickTotalValue();

        var resultQuote7d = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueDiffQuote()
                .getText();

        var resultPercentage7d = this.pages.getWalletBalancePage()
                .getTotalValuePage()
                .getTotalValueDiffPercent()
                .getText();

        softAssertions.assertThat(resultQuote1h)
                .containsOnlyOnce("1h (0.00$)");

        softAssertions.assertThat(resultPercentage1h)
                .containsOnlyOnce("0.00%");

        softAssertions.assertThat(resultQuote24h)
                .containsOnlyOnce("24h (0.00$)");

        softAssertions.assertThat(resultPercentage24h)
                .containsOnlyOnce("0.00%");

        softAssertions.assertThat(resultQuote7d)
                .containsOnlyOnce("7d (0.00$)");

        softAssertions.assertThat(resultPercentage7d)
                .containsOnlyOnce("0.00%");

        softAssertions.assertAll();
    }

    private String countTotalValueBaseTestData(TestData data, BigDecimal multiplyBy) {
        var ethereumPrice = data.getEthereumPrice().getPriceCurrent();
        var ethereumValue = data.getEthereumTestNetWallet().getAmount().multiply(ethereumPrice);

        var polygonPrice = data.getPolygonPrice().getPriceCurrent();
        var polygonValue = data.getPolygonTestNetWallet().getAmount().multiply(polygonPrice);

        var avalanchePrice = data.getAvalanchePrice().getPriceCurrent();
        var avalancheValue = data.getAvalancheTestNetWallet().getAmount().multiply(avalanchePrice);

        var tempEthereumPolygonValue = ethereumValue.add(polygonValue)
                .setScale(2, RoundingMode.FLOOR);
        var ethereumPolygonValue = tempEthereumPolygonValue.multiply(multiplyBy);
        var totalValue = ethereumPolygonValue.add(avalancheValue)
                .setScale(2, RoundingMode.FLOOR);

        return totalValue.toPlainString() + " USD";
    }

}
