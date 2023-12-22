package pl.kargolek.tests;

import io.qameta.allure.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.WebDriver;
import pl.kargolek.data.script.TestDataSql;
import pl.kargolek.extension.BaseTestConfig;
import pl.kargolek.extension.assertion.SoftAssertion;
import pl.kargolek.pages.InitPages;
import pl.kargolek.util.TestProperty;
import pl.kargolek.util.WebDriverUtil;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Karol Kuta-Orlowicz
 */
@Tag("InputWalletUI")
@Epic("Wallet provider")
@Feature("Input wallet UI")
@BaseTestConfig
@SoftAssertion
public class InputWalletsUIAsyncTest {

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
    public void setup(TestProperty property, InitPages pages) {
        this.appBaseURL = property.getAppBaseURL();
        this.pages = pages;
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("As a normal user, I want to see app title")
    @Description("When user open wallet's provider page, app title should be display")
    void whenOpenWalletPage_thenTitleShouldBeDisplay(TestProperty property) {
        var appTitleText = this.pages.getHomePage()
                .open(this.appBaseURL)
                .getAppTitleText();

        assertThat(appTitleText).isEqualTo(property.getAppTitle());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("As a normal user, I want to see description which tokens are handle")
    @Description("Handled tokens names should be provided below input wallet addresses field")
    void whenOpenWalletPage_thenTokenHandleDescriptionShouldDisplayed(SoftAssertions softAssertions) {
        var tokenDescriptionText = this.pages.getHomePage()
                .open(this.appBaseURL)
                .getTokenDescriptionText();

        softAssertions.assertThat(tokenDescriptionText)
                .containsOnlyOnce("Ethereum");
        softAssertions.assertThat(tokenDescriptionText)
                .containsOnlyOnce("Polygon");
        softAssertions.assertAll();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("As a normal user, I can see placeholder in input field")
    @Description("User can see placeholder in input field that inform what data should be typed")
    void whenOpenWalletPage_thenInputPlaceholderShouldBeVisible() {
        var placeholder = this.pages.getHomePage()
                .open(this.appBaseURL)
                .getInputWalletPlaceholder();

        assertThat(placeholder).isEqualTo("Enter your wallets...");
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("As a normal user, when I type ETH wallet address and press send button " +
            "I want to open balance page")
    @Description("When user hit send button with valid ETH wallet address, balance page should be open")
    void whenWalletAddressCorrectAndPressSend_thenBalancePageShouldBeOpen() {
        var isBalancePageOpened = this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWalletsClearText()
                .inputWallets("0x6cc28D37607024098F4104228E1388953875309B")
                .sendWalletsButtonClick()
                .getEthBalanceAmountContainer();

        assertThat(isBalancePageOpened).isNotNull();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("As a normal user, when I type ETH wallet address and press enter key " +
            "I want to open balance page")
    @Description("When user hit send key with valid ETH wallet address, balance page should be open")
    void whenWalletAddressCorrectAndPressEnterKey_thenBalancePageShouldBeOpen() {
        var isBalancePageOpened = this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWalletsClearText()
                .inputWallets("0x6cc28D37607024098F4104228E1388953875309B")
                .enterKeyPress()
                .getEthBalanceAmountContainer();

        assertThat(isBalancePageOpened).isNotNull();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("As a normal user, when I type ETH wallet address refresh page or back from other page " +
            "then wallet address should be maintained in the input wallets")
    @Description("Wallet address should be saved in browser session storage")
    void whenSendWalletAddress_thenShouldBeSavedInTheSessionStorage(WebDriver driver, SoftAssertions softAssertions) {
        var inputWalletPage = this.pages.getInputWalletPage();
        var walletAddress = "0x6cc28D37607024098F4104228E1388953875309B";

        this.pages.getHomePage()
                .open(this.appBaseURL)
                .inputWalletsClearText()
                .inputWallets(walletAddress)
                .enterKeyPress()
                .getEthBalanceAmountContainer();

        WebDriverUtil.navigateBack(driver);
        var inputTextAfterBack = inputWalletPage.getInputWalletText();

        WebDriverUtil.refreshPage(driver);
        var inputTextAfterRefresh = inputWalletPage.getInputWalletText();

        softAssertions.assertThat(inputTextAfterBack)
                .isEqualTo(walletAddress);
        softAssertions.assertThat(inputTextAfterRefresh)
                .isEqualTo(walletAddress);
        softAssertions.assertAll();
    }

    @Tag("NavBar")
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("As a normal user, I can use navbar in wallets page")
    @Description("Navbar is enable in the wallets page")
    void whenOpenWalletPage_thenNavbarShouldBeDisplay() {
        this.pages.getHomePage()
                .open(this.appBaseURL);
        var navBarContainer = this.pages.getNavigationBarPage()
                .getNavBarContainer();

        assertThat(navBarContainer).isNotNull();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("As a normal user when open wallet input page, I can use price banner")
    @Description("Price banner component should be attached to /wallet page")
    void whenOpenWalletPage_thenPriceBannerShouldBeAvailable() {
        this.pages.getHomePage()
                .open(this.appBaseURL);

        var priceBanner = this.pages
                .getPriceBannerPage()
                .getPriceBarContainer();

        assertThat(priceBanner).isNotNull();
    }
}
