package pl.kargolek.tests;

import io.qameta.allure.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import pl.kargolek.data.TestData;
import pl.kargolek.data.script.TestDataSql;
import pl.kargolek.extension.BaseTestConfig;
import pl.kargolek.extension.assertion.SoftAssertion;
import pl.kargolek.extension.data.TestDataProvider;
import pl.kargolek.extension.javascript.JavascriptDriverExecutor;
import pl.kargolek.pages.InitPages;
import pl.kargolek.util.JavascriptDriver;
import pl.kargolek.util.TestProperty;
import pl.kargolek.util.WebDriverUtil;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

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
public class BalanceUIAsyncTest {

    private String appBaseURL;
    private InitPages pages;

    @BeforeAll
    public static void setupAll(TestDataSql testDataSql) {
        testDataSql.deleteCryptocurrenciesData();
        testDataSql.insertEthereumData();
        testDataSql.insertPolygonData();
        testDataSql.insertAvalancheData();
    }

    @BeforeEach
    public void setup(TestProperty property, InitPages pages) {
        this.appBaseURL = property.getAppBaseURL();
        this.pages = pages;
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("As user I want to see balance component for ETH")
    @Description("ETH balance component for valid wallet")
    public void whenWalletValid_thenEthBalanceComponent(TestData data, WebDriver driver) {
        var component = this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWallets(data.getWalletAddressValid())
                .enterKeyPress()
                .getEthBalanceAmountContainer();

        assertThat(component.isDisplayed()).isTrue();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("As user I want to see balance component for MATIC")
    @Description("MATIC balance component for valid wallet")
    public void whenWalletValid_thenMaticBalanceComponent(TestData data) {
        var component = this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWallets(data.getWalletAddressValid())
                .enterKeyPress()
                .getPolygonBalanceAmountContainer();

        assertThat(component.isDisplayed()).isTrue();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("As user I want to see balance component for AVAX")
    @Description("AVAX balance component for valid wallet")
    public void whenWalletValid_thenAvaxBalanceComponent(TestData data) {
        var component = this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWallets(data.getWalletAddressValid())
                .enterKeyPress()
                .getAvalancheAmountContainer();

        assertThat(component.isDisplayed()).isTrue();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("As user I can see total amount value for token balance")
    @Description("Total amount count amounts from all of the wallets provided by user for each token")
    public void whenWalletValid_thenTotalAmount(TestData data, SoftAssertions softAssertions) {
        var balancePage = this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWalletsClearText()
                .inputWallets(data.getWalletAddressValid())
                .enterKeyPress();

        var ethTotalAmount = balancePage.getEthereumTableAmountPage()
                .getTotalAmountText();
        var polygonTotalAmount = balancePage.getPolygonTableAmountPage()
                .getTotalAmountText();
        var avalancheTotalAmount = balancePage.getAvalancheTableAmountPage()
                .getTotalAmountText();

        softAssertions.assertThat(ethTotalAmount)
                .isNotBlank();
        softAssertions.assertThat(polygonTotalAmount)
                .isNotBlank();
        softAssertions.assertThat(avalancheTotalAmount)
                .isNotBlank();
        softAssertions.assertAll();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("As user I can see symbol name value for token balance")
    public void whenWalletValid_thenSymbolName(TestData data, SoftAssertions softAssertions) {
        var balancePage = this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWallets(data.getWalletAddressValid())
                .enterKeyPress();

        var ethSymbolName = balancePage.getEthereumTableAmountPage()
                .getSymbolText();
        var polygonSymbolName = balancePage.getPolygonTableAmountPage()
                .getSymbolText();
        var avalancheSymbolName = balancePage.getAvalancheTableAmountPage()
                .getSymbolText();

        softAssertions.assertThat(ethSymbolName)
                .isNotBlank();
        softAssertions.assertThat(polygonSymbolName)
                .isNotBlank();
        softAssertions.assertThat(avalancheSymbolName)
                .isNotBlank();
        softAssertions.assertAll();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("As user I can see total asset value for token balance")
    @Description("Total asset value based on the value of each wallet for each token")
    public void whenWalletValid_thenTotalValue(TestData data, SoftAssertions softAssertions, WebDriver driver) {
        var remoteWebDriver = (RemoteWebDriver) driver;
        System.out.println("Test Session ID: " + remoteWebDriver.getSessionId() + " Thread: " + Thread.currentThread().getName());
        var balancePage = this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWallets(data.getWalletAddressValid())
                .enterKeyPress();

        var ethTotalValue = balancePage.getEthereumTableAmountPage()
                .getTotalValueText();
        var polygonTotalValue = balancePage.getPolygonTableAmountPage()
                .getTotalValueText();
        var avalancheTotalValue = balancePage.getAvalancheTableAmountPage()
                .getTotalValueText();

        softAssertions.assertThat(ethTotalValue)
                .isNotBlank();
        softAssertions.assertThat(polygonTotalValue)
                .isNotBlank();
        softAssertions.assertThat(avalancheTotalValue)
                .isNotBlank();
        softAssertions.assertAll();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("As user I can see proper headers for symbol, total value and amount")
    @Link(url = "https://trello.com/c/dRgxzOK3/" +
            "62-cpa-62-as-user-i-want-see-info-about-which-value-is-amount-and-value-in-" +
            "balance-page-total-balances-for-each-token-component", name = "Story Link")
    public void whenWalletValid_thenHeadersForSymbolTotalAmountTotalValueHeader(TestData data, SoftAssertions softAssertions) {
        var balancePage = this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWallets(data.getWalletAddressValid())
                .enterKeyPress();

        var ethSymbolHeader = balancePage.getEthereumTableAmountPage()
                .getSymbolHeader();
        var ethTotalAmountHeader = balancePage.getEthereumTableAmountPage()
                .getTotalAmountHeader();
        var ethTotalValueHeader = balancePage.getEthereumTableAmountPage()
                .getTotalTokenValueHeader();

        var polygonSymbolHeader = balancePage.getPolygonTableAmountPage()
                .getSymbolHeader();
        var polygonTotalAmountHeader = balancePage.getPolygonTableAmountPage()
                .getTotalAmountHeader();
        var polygonTotalValueHeader = balancePage.getPolygonTableAmountPage()
                .getTotalTokenValueHeader();

        var avalancheSymbolHeader = balancePage.getAvalancheTableAmountPage()
                .getSymbolHeader();
        var avalancheTotalAmountHeader = balancePage.getAvalancheTableAmountPage()
                .getTotalAmountHeader();
        var avalancheTotalValueHeader = balancePage.getAvalancheTableAmountPage()
                .getTotalTokenValueHeader();

        softAssertions.assertThat(ethSymbolHeader.isDisplayed())
                .isTrue();
        softAssertions.assertThat(ethTotalAmountHeader.isDisplayed())
                .isTrue();
        softAssertions.assertThat(ethTotalValueHeader.isDisplayed())
                .isTrue();

        softAssertions.assertThat(polygonSymbolHeader.isDisplayed())
                .isTrue();
        softAssertions.assertThat(polygonTotalAmountHeader.isDisplayed())
                .isTrue();
        softAssertions.assertThat(polygonTotalValueHeader.isDisplayed())
                .isTrue();

        softAssertions.assertThat(avalancheSymbolHeader.isDisplayed())
                .isTrue();
        softAssertions.assertThat(avalancheTotalAmountHeader.isDisplayed())
                .isTrue();
        softAssertions.assertThat(avalancheTotalValueHeader.isDisplayed())
                .isTrue();
        softAssertions.assertAll();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("As user I can switch view of wallet balance details")
    @Description("View can be switch 1. [Address, Amount, Value] 2. [1H, 24h, 7D] 3. [30D, 60D, 90D]")
    public void whenPressSwitchWalletBalanceDetailView_thenViewIsChanged(TestData data, SoftAssertions softAssertions) {
        var balancePage = this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWallets(data.getEthereumTestNetWallet().getAddress())
                .enterKeyPress();

        //Address
        softAssertions.assertThat(balancePage.getEthereumTableBalancePage()
                        .getHeaderAddress()
                        .isDisplayed())
                .isTrue();

        softAssertions.assertThat(balancePage.getEthereumTableBalancePage()
                        .getFirstCellAddress()
                        .isDisplayed())
                .isTrue();

        //Amount
        softAssertions.assertThat(balancePage.getEthereumTableBalancePage()
                        .getHeaderAmount()
                        .isDisplayed())
                .isTrue();

        softAssertions.assertThat(balancePage.getEthereumTableBalancePage()
                        .getFirstCellAmount()
                        .isDisplayed())
                .isTrue();

        //Token value
        softAssertions.assertThat(balancePage.getEthereumTableBalancePage()
                        .getHeaderTokenValue()
                        .isDisplayed())
                .isTrue();

        softAssertions.assertThat(balancePage.getEthereumTableBalancePage()
                        .getFirstCellTokenValue()
                        .isDisplayed())
                .isTrue();

        balancePage.getEthereumTableActionBarPage()
                .clickSwitchViewButton();

        //1h
        softAssertions.assertThat(balancePage.getEthereumTableBalancePage()
                        .getHeader1h()
                        .isDisplayed())
                .isTrue();

        softAssertions.assertThat(balancePage.getEthereumTableBalancePage()
                        .getFirstCell1hValue()
                        .isDisplayed())
                .isTrue();

        //24h
        softAssertions.assertThat(balancePage.getEthereumTableBalancePage()
                        .getHeader24h()
                        .isDisplayed())
                .isTrue();

        softAssertions.assertThat(balancePage.getEthereumTableBalancePage()
                        .getFirstCell24hValue()
                        .isDisplayed())
                .isTrue();

        //7d
        softAssertions.assertThat(balancePage.getEthereumTableBalancePage()
                        .getHeader7d()
                        .isDisplayed())
                .isTrue();

        softAssertions.assertThat(balancePage.getEthereumTableBalancePage()
                        .getFirstCell7dValue()
                        .isDisplayed())
                .isTrue();

        balancePage.getEthereumTableActionBarPage()
                .clickSwitchViewButton();

        //30d
        softAssertions.assertThat(balancePage.getEthereumTableBalancePage()
                        .getHeader30d()
                        .isDisplayed())
                .isTrue();

        softAssertions.assertThat(balancePage.getEthereumTableBalancePage()
                        .getFirstCell30dValue()
                        .isDisplayed())
                .isTrue();

        //60d
        softAssertions.assertThat(balancePage.getEthereumTableBalancePage()
                        .getHeader60d()
                        .isDisplayed())
                .isTrue();

        softAssertions.assertThat(balancePage.getEthereumTableBalancePage()
                        .getFirstCell60dValue()
                        .isDisplayed())
                .isTrue();

        //90d
        softAssertions.assertThat(balancePage.getEthereumTableBalancePage()
                        .getHeader90d()
                        .isDisplayed())
                .isTrue();

        softAssertions.assertThat(balancePage.getEthereumTableBalancePage()
                        .getFirstCell90dValue()
                        .isDisplayed())
                .isTrue();

        softAssertions.assertAll();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("As user I can see wallets counter")
    public void whenMultipleWallets_thenCounterShowsTheNumber(TestData data) {
        var walletsData = data.getWalletAddressValid() + ","
                + data.getWalletAddressValid() + ","
                + data.getWalletAddressValid() + ","
                + data.getWalletAddressValid();

        var balancePage = this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWallets(walletsData)
                .enterKeyPress();

        var numberWalletsText = balancePage.getEthereumTableActionBarPage()
                .getNumberWalletsText();

        assertThat(numberWalletsText).isEqualTo("#Address: 4");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("As user I can see addresses that I provided on the /wallet page")
    @Description("Address is reduce to 11 char.")
    public void whenMultipleWallets_thenAddressesAreInTheProperColumn(TestData data, SoftAssertions softAssertions) {
        var walletData = data.getEthereumTestNetWallet().getAddress() + "," +
                data.getWalletAddressValid();

        var balancePage = this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWallets(walletData)
                .enterKeyPress();

        var addressFirst = balancePage.getEthereumTableBalancePage()
                .getFirstCellAddress()
                .getText();

        var addressSecond = balancePage.getEthereumTableBalancePage()
                .getLastCellAddress()
                .getText();

        softAssertions.assertThat(addressFirst)
                .isEqualTo(data.getEthereumTestNetWallet()
                        .getAddress()
                        .substring(0, 10));

        softAssertions.assertThat(addressSecond)
                .isEqualTo(data.getWalletAddressValid()
                        .substring(0, 10));

        softAssertions.assertAll();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("As user in system I am able to open explorer page for ETH wallet address that has been provided")
    @Description("Wallet explorers for test: ETH: https://goerli.etherscan.io/address/. " +
            "All addresses belong to the testnet for each token.")
    public void whenClickOnEthereumWalletAddress_thenOpenExplorerPage(TestData data, WebDriver driver) {
        var balancePage = this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWallets(data.getWalletAddressValid())
                .enterKeyPress();

        var currentWindow = driver.getWindowHandle();

        var addressCell = balancePage.getEthereumTableBalancePage()
                .getFirstCellAddress();
        balancePage.clickElement(addressCell, Duration.ofSeconds(20));

        WebDriverUtil.switchToNextTab(driver, currentWindow);

        assertThat(balancePage.waitForContainsURL(data.getExplorerAddressTestnetEthereum(), Duration.ofSeconds(5)))
                .isTrue();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("As user in system I am able to open explorer page for MATIC wallet address that has been provided")
    @Description("Wallet explorer for test: MATIC: https://mumbai.polygonscan.com/address/" +
            "All addresses belong to the testnet.")
    public void whenClickOnOpenExplorerForPolygonWalletAddress_thenOpenExplorerPage(TestData data, WebDriver driver) {
        var balancePage = this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWallets(data.getWalletAddressValid())
                .enterKeyPress();

        var currentWindow = driver.getWindowHandle();

        var addressCell = balancePage.getPolygonTableBalancePage()
                .getFirstCellAddress();
        balancePage.clickElement(addressCell, Duration.ofSeconds(20));

        WebDriverUtil.switchToNextTab(driver, currentWindow);

        assertThat(balancePage.waitForContainsURL(data.getExplorerAddressTestnetPolygon(), Duration.ofSeconds(5)))
                .isTrue();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("As user in system I am able to open explorer page for AVAX wallet address that has been provided")
    @Description("Wallet explorer for test: AVAX: https://testnet.snowtrace.io/address/. " +
            "All addresses belong to the testnet.")
    public void whenClickOnOpenExplorerForAvalancheWalletAddress_thenOpenExplorerPage(TestData data, WebDriver driver) {
        var balancePage = this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWallets(data.getWalletAddressValid())
                .enterKeyPress();

        var currentWindow = driver.getWindowHandle();

        var addressCell = balancePage.getAvalancheTableBalancePage()
                .getFirstCellAddress();
        balancePage.clickElement(addressCell, Duration.ofSeconds(20));

        WebDriverUtil.switchToNextTab(driver, currentWindow);

        assertThat(balancePage.waitForContainsURL(data.getExplorerAddressTestnetAvalanche(), Duration.ofSeconds(5)))
                .isTrue();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("As user when I refresh the balance page, data should be maintained")
    public void whenRefreshThePage_thenDataMaintain(TestData data, WebDriver driver) {
        this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWallets(data.getAvalancheTestNetWallet().getAddress())
                .enterKeyPress();

        var totalAmountTextBefore = this.pages.getWalletBalancePage()
                .getEthereumTableAmountPage()
                .getTotalAmountText();

        WebDriverUtil.refreshPage(driver);

        var totalAmountTextAfter = this.pages.getWalletBalancePage()
                .getEthereumTableAmountPage()
                .getTotalAmountText();

        assertThat(totalAmountTextBefore).isEqualTo(totalAmountTextAfter);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("As user when I navigate back and forward, data should be maintained")
    public void whenNavigateBackAndForwardPage_thenDataMaintain(TestData data, WebDriver driver) {
        this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWallets(data.getAvalancheTestNetWallet().getAddress())
                .enterKeyPress();

        var totalAmountTextBefore = this.pages.getWalletBalancePage()
                .getEthereumTableAmountPage()
                .getTotalAmountText();

        WebDriverUtil.navigateBack(driver);
        WebDriverUtil.navigateForward(driver);

        var totalAmountTextAfter = this.pages.getWalletBalancePage()
                .getEthereumTableAmountPage()
                .getTotalAmountText();

        assertThat(totalAmountTextBefore).isEqualTo(totalAmountTextAfter);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("As user I can scroll down page when I provide multiple wallets")
    public void whenMultipleAddress_thenCanScrollToLastElementOnPage
            (TestData data, WebDriver driver, JavascriptDriver jsDriver, SoftAssertions softAssertions) {
        var wallets = data.getAvalancheTestNetWallet().getAddress() + "," +
                data.getAvalancheTestNetWallet().getAddress() + "," +
                data.getAvalancheTestNetWallet().getAddress() + "," +
                data.getAvalancheTestNetWallet().getAddress() + "," +
                data.getAvalancheTestNetWallet().getAddress() + "," +
                data.getAvalancheTestNetWallet().getAddress() + "," +
                data.getAvalancheTestNetWallet().getAddress() + "," +
                data.getAvalancheTestNetWallet().getAddress() + "," +
                data.getAvalancheTestNetWallet().getAddress() + "," +
                data.getAvalancheTestNetWallet().getAddress();

        this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWallets(wallets)
                .enterKeyPress();

        var browserHeight = driver.manage().window().getSize().getHeight();

        var element = this.pages.getWalletBalancePage()
                .getAvalancheTableBalancePage()
                .getLastCellAddress();

        var viewportTop = Double.valueOf(jsDriver.getTopPositionInViewport(element).toString());

        softAssertions.assertThat(viewportTop)
                .isGreaterThan(browserHeight);

        jsDriver.scrollToElement(element);

        viewportTop = Double.valueOf(jsDriver.getTopPositionInViewport(element).toString());
        softAssertions.assertThat(viewportTop)
                .isLessThan(browserHeight);

        softAssertions.assertAll();
    }
}