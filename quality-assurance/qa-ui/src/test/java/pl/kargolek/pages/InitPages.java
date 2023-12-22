package pl.kargolek.pages;

import org.openqa.selenium.WebDriver;
import pl.kargolek.pages.balance.WalletBalancePage;
import pl.kargolek.pages.landpage.HomePage;
import pl.kargolek.pages.navbar.NavigationBarPage;
import pl.kargolek.pages.pricebanner.PriceBannerPage;
import pl.kargolek.pages.toast.ToastPage;
import pl.kargolek.pages.wallets.InputWalletPage;

/**
 * @author Karol Kuta-Orlowicz
 */
public class InitPages {

    private final WebDriver driver;

    public InitPages(WebDriver driver) {
        this.driver = driver;
    }

    public HomePage getHomePage() {
        return new HomePage(this.driver);
    }

    public InputWalletPage getInputWalletPage() {
        return new InputWalletPage(this.driver);
    }

    public ToastPage getToastPage() {
        return new ToastPage(this.driver);
    }

    public NavigationBarPage getNavigationBarPage() {
        return new NavigationBarPage(this.driver);
    }

    public PriceBannerPage getPriceBannerPage() {
        return new PriceBannerPage(this.driver);
    }

    public WalletBalancePage getWalletBalancePage() {
        return new WalletBalancePage(this.driver);
    }
}
