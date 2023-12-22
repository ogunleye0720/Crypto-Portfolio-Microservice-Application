package pl.kargolek.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;
import pl.kargolek.data.TestData;
import pl.kargolek.data.script.TestDataSql;
import pl.kargolek.extension.BaseTestConfig;
import pl.kargolek.extension.assertion.SoftAssertion;
import pl.kargolek.extension.data.TestDataProvider;
import pl.kargolek.extension.visual.VisualCompare;
import pl.kargolek.pages.InitPages;
import pl.kargolek.util.TestProperty;
import pl.kargolek.util.VisualCompareService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Karol Kuta-Orlowicz
 */
@Tag("TrendLineChartUI")
@Epic("Token balance")
@Feature("Trend line chart UI")
@BaseTestConfig
@TestDataProvider
@SoftAssertion
@VisualCompare
@Isolated
public class TrendLineChartTest {

    private String baseURL;
    private InitPages pages;

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
    @Severity(SeverityLevel.CRITICAL)
    @Story("As user when wallet balance not empty and decrease 10% each past data, trend shows correct line")
    public void whenWalletBalanceMinus10PercentEach_thenShowsCorrectTrendLine(TestData data,
                                                                              TestDataSql dataSql,
                                                                              VisualCompareService service) {
        dataSql.deleteCryptocurrenciesData();
        dataSql.insertEthereumDataPricePastNegativeMinus10Each();
        dataSql.insertAvalancheDataPricePastNegativeMinus10Each();
        dataSql.insertPolygonDataPricePastNegativeMinus10Each();

        var page = this.pages.getHomePage()
                .open(this.baseURL)
                .inputWalletsClearText()
                .inputWallets(data.getEthereumTestNetWallet().getAddress())
                .enterKeyPress()
                .getTotalValuePage();

        var trendLineChartContainer = page.waitForStopAnimation()
                .getTrendLineChartContainer();

        var result = service.compareElement(trendLineChartContainer,
                "trend_decrease_line_chart_container");

        assertThat(result).isEqualTo(0.0);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("As user when wallet balance not empty I want to see trend line for 7d ")
    public void whenWalletBalanceNotEmpty_thenChartShowsData(TestData data, VisualCompareService service) {
        var page = this.pages.getHomePage()
                .open(this.baseURL)
                .inputWalletsClearText()
                .inputWallets(data.getEthereumTestNetWallet().getAddress())
                .enterKeyPress()
                .getTotalValuePage();

        var trendLineChartContainer = page.waitForTotalValueTextChange()
                .waitForStopAnimation()
                .getTrendLineChartContainer();

        var result = service.compareElement(trendLineChartContainer, "trend_line_chart_container");

        assertThat(result).isEqualTo(0.0);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("As user when wallet balance empty, then I want to see flat trend line for 7d ")
    @Description("When balance empty, chart shows flat line")
    public void whenWalletBalanceEmpty_thenChartShowsData(TestData data, VisualCompareService service) {
        var page = this.pages.getHomePage()
                .open(this.baseURL)
                .inputWalletsClearText()
                .inputWallets(data.getEthereumTestNetWalletEmpty().getAddress())
                .enterKeyPress()
                .getTotalValuePage();

        var trendLineChartContainer = page.waitForStopAnimation()
                .getTrendLineChartContainer();

        var result = service.compareElement(trendLineChartContainer, "trend_flat_line_chart_container");

        assertThat(result).isEqualTo(0.0);
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Story("As user when open balance page, trend line chart label is visible")
    public void whenOpenBalancePage_thenTrendChartLabelVisible(TestData data) {
        var page = this.pages.getHomePage()
                .open(this.baseURL)
                .inputWalletsClearText()
                .inputWallets(data.getEthereumTestNetWalletEmpty().getAddress())
                .enterKeyPress()
                .getTotalValuePage();

        var labelText = page.getTrendChartLabel()
                .getText();

        assertThat(labelText).isEqualTo("Trend Line 7d");
    }
}
