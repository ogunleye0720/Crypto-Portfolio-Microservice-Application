package pl.kargolek.pages.landpage;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pl.kargolek.pages.BasePage;
import pl.kargolek.pages.wallets.InputWalletPage;

/**
 * @author Karol Kuta-Orlowicz
 */
public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Open home page")
    public InputWalletPage open(String url){
        this.driver.get(url);
        return new InputWalletPage(driver);
    }
}
