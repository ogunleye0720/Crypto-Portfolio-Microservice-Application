package pl.kargolek.tests;

import io.qameta.allure.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.Isolated;
import org.openqa.selenium.WebDriver;
import pl.kargolek.data.TestData;
import pl.kargolek.data.script.TestDataSql;
import pl.kargolek.extension.BaseTestConfig;
import pl.kargolek.extension.assertion.SoftAssertion;
import pl.kargolek.extension.data.TestDataProvider;
import pl.kargolek.pages.InitPages;
import pl.kargolek.util.TestProperty;
import pl.kargolek.util.WebDriverUtil;

/**
 * @author Karol Kuta-Orlowicz
 */

@Tags({
 @Tag("InputWalletUI"),
 @Tag("ToastErrorHandler")
})
@Epic("Wallet provider")
@Feature("Error handler")
@BaseTestConfig
@TestDataProvider
@SoftAssertion
@Isolated
public class WalletProviderErrorHandlerTest {
    private static final String TOAST_ERROR_TITLE = "Error";
    private static final String TOAST_ERROR_MESSAGE = "Internal system error. Please try again later.";
    private String appBaseURL;
    private InitPages pages;

    @BeforeAll
    public static void setupAll(TestDataSql testDataSql){
        testDataSql.deleteCryptocurrenciesData();
        testDataSql.insertEthereumData();
        testDataSql.insertPolygonData();
        testDataSql.insertAvalancheData();
    }

    @BeforeEach
    public void setupEach(TestProperty testProperty, InitPages initPages){
        this.appBaseURL = testProperty.getAppBaseURL();
        this.pages = initPages;
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("As user, when send wallet addresses and ETH, MATIC price is not available, then system show error toast")
    @Description("For case of crypto price is not available")
    @Execution(ExecutionMode.SAME_THREAD)
    public void whenNoEthPolygonPrice_thenSystemError(TestData data,
                                                      TestDataSql sqlData,
                                                      WebDriver driver,
                                                      SoftAssertions softAssertions) {
        sqlData.deleteCryptocurrenciesData();
        WebDriverUtil.refreshPage(driver);

        this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWalletsClearText()
                .inputWallets(data.getWalletAddressValid())
                .enterKeyPress();

        var toastTitle = this.pages.getToastPage()
                .getToastTitle();
        var toastMessage = this.pages.getToastPage()
                .getToastMessage();

        softAssertions.assertThat(toastTitle)
                .isEqualTo(TOAST_ERROR_TITLE);
        softAssertions.assertThat(toastMessage)
                .isEqualTo(TOAST_ERROR_MESSAGE);
        softAssertions.assertAll();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("As user, when send wallet addresses and only ETH price is not available, then system show error toast")
    @Description("For case of crypto price is not available")
    @Execution(ExecutionMode.SAME_THREAD)
    public void whenNoEthPrice_thenSystemError(TestData data,
                                               TestDataSql sqlData,
                                               WebDriver driver,
                                               SoftAssertions softAssertions) {
        sqlData.deleteCryptocurrenciesData();
        sqlData.insertEthereumData();
        WebDriverUtil.refreshPage(driver);

        this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWalletsClearText()
                .inputWallets(data.getWalletAddressValid())
                .enterKeyPress();

        var toastTitle = this.pages.getToastPage()
                .getToastTitle();
        var toastMessage = this.pages.getToastPage()
                .getToastMessage();

        softAssertions.assertThat(toastTitle)
                .isEqualTo(TOAST_ERROR_TITLE);
        softAssertions.assertThat(toastMessage)
                .isEqualTo(TOAST_ERROR_MESSAGE);
        softAssertions.assertAll();
    }
}
