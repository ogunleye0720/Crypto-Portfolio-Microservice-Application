package pl.kargolek.tests;

import io.qameta.allure.*;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;
import pl.kargolek.data.TestData;
import pl.kargolek.data.script.TestDataSql;
import pl.kargolek.extension.BaseTestConfigBeforeAll;
import pl.kargolek.extension.assertion.SoftAssertion;
import pl.kargolek.extension.data.TestDataProvider;
import pl.kargolek.pages.InitPages;
import pl.kargolek.util.TestProperty;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

/**
 * @author Karol Kuta-Orlowicz
 */


@Tag("TokenTreeMapChartUI")
@Epic("Token balance")
@Feature("Token tree map chart")
@BaseTestConfigBeforeAll
@TestDataProvider
@SoftAssertion
@Isolated
public class TokenTreeMapChartTest {

    private String baseURL;
    private InitPages pages;

    private static final Double TOTAL_WALLET_BALANCE = 2136.35;
    private static final Double ETH_BALANCE = 235.32;
    private static final Double MATIC_BALANCE = 400.22;
    private static final Double AVAX_BALANCE = 1500.81;

    @BeforeEach
    public void setup(TestProperty property, InitPages pages, TestDataSql dataSql) {
        this.baseURL = property.getAppBaseURL();
        this.pages = pages;

        dataSql.deleteCryptocurrenciesData();
        dataSql.insertEthereumData();
        dataSql.insertAvalancheData();
        dataSql.insertPolygonData();
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("As user when check balance, the charts cell's size are properly set depends to percentage of total wallet")
    @Description("Box size in chart is percent value of each token in entire wallet balance")
    public void whenWalletBalanceNotEmpty_thenChartShowsProperBoxSize(TestData data, SoftAssertions softAssertions) {
        var tokenTreeMapPage = this.pages.getHomePage()
                .open(this.baseURL)
                .inputWalletsClearText()
                .inputWallets(data.getEthereumTestNetWallet().getAddress())
                .enterKeyPress()
                .getTokenTreeMapChartPage();

        var chartComponentWidth = tokenTreeMapPage.getChartContainer().getSize().getWidth();
        var ethCellWidth = tokenTreeMapPage.getEthereumCell().getSize().getWidth();
        var maticCellWidth = tokenTreeMapPage.getPolygonCell().getSize().getWidth();
        var avaxCellWidth = tokenTreeMapPage.getAvalancheCell().getSize().getWidth();

        var expectedEthCellWidth = calculateCellWidth(ETH_BALANCE, chartComponentWidth);
        var expectedMaticCellWidth = calculateCellWidth(MATIC_BALANCE, chartComponentWidth);
        var expectedAvaxCellWidth = calculateCellWidth(AVAX_BALANCE, chartComponentWidth);

        softAssertions.assertThat(ethCellWidth)
                .isCloseTo(expectedEthCellWidth, Percentage.withPercentage(1));

        softAssertions.assertThat(maticCellWidth)
                .isCloseTo(expectedMaticCellWidth, Percentage.withPercentage(1));

        softAssertions.assertThat(avaxCellWidth)
                .isCloseTo(expectedAvaxCellWidth, Percentage.withPercentage(1));

        softAssertions.assertAll();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("As user I can chart labels with token symbol and current token balance")
    public void whenWalletBalanceNotEmpty_thenLabelWithSymbolAndBalance(TestData data, SoftAssertions softAssertions) throws InterruptedException {
        var tokenTreeMapPage = this.pages.getHomePage()
                .open(this.baseURL)
                .inputWalletsClearText()
                .inputWallets(data.getEthereumTestNetWallet().getAddress())
                .enterKeyPress()
                .getTokenTreeMapChartPage();

        await().atMost(Duration.ofSeconds(20)).until(() ->
                tokenTreeMapPage.getEthereumBalanceLabelText().equals("235"));

        var ethSymbolLabel = tokenTreeMapPage.getEthereumSymbolLabelText();
        var avaxSymbolLabel = tokenTreeMapPage.getAvalancheSymbolLabelText();
        var maticSymbolLabel = tokenTreeMapPage.getPolygonSymbolLabelText();

        var ethBalanceLabel = tokenTreeMapPage.getEthereumBalanceLabelText();
        var avaxBalanceLabel = tokenTreeMapPage.getAvalancheBalanceLabelText();
        var maticBalanceLabel = tokenTreeMapPage.getPolygonBalanceLabelText();

        softAssertions.assertThat(ethSymbolLabel)
                .isEqualTo("ETH");
        softAssertions.assertThat(avaxSymbolLabel)
                .isEqualTo("AVAX");
        softAssertions.assertThat(maticSymbolLabel)
                .isEqualTo("MATIC");
        softAssertions.assertThat(ethBalanceLabel)
                .isEqualTo("235");
        softAssertions.assertThat(avaxBalanceLabel)
                .isEqualTo("1,501");
        softAssertions.assertThat(maticBalanceLabel)
                .isEqualTo("400");

        softAssertions.assertAll();
    }


    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("As user when provided wallet balance empty, then no data label is display")
    @Description("Label shows when wallet balances are empty")
    public void whenEmptyWallet_thenNoBalanceLabel(TestData data) {
        var tokenTreeMapPage = this.pages.getHomePage()
                .open(this.baseURL)
                .inputWalletsClearText()
                .inputWalletsClearText()
                .inputWallets(data.getEthereumTestNetWalletEmpty().getAddress())
                .enterKeyPress()
                .getTokenTreeMapChartPage();

        var noDataLabel = tokenTreeMapPage.getNoDataLabel().getText();

        assertThat(noDataLabel)
                .isEqualTo("Empty balance");
    }

    private int calculateCellWidth(double cellBalance, Integer chartWidth) {
        return (int) Math.round((cellBalance / TOTAL_WALLET_BALANCE) * chartWidth);
    }

}
