package pl.kargolek.pages.pricebanner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.kargolek.pages.BasePage;

import java.time.Duration;

/**
 * @author Karol Kuta-Orlowicz
 */
public class PriceBannerPage extends BasePage {

    @FindBy(css = "app-price-bar .bar-container")
    private WebElement priceBarContainer;

    public PriceBannerPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getPriceBarContainer(){
        return this.waitForElementVisibility(
                this.priceBarContainer,
                Duration.ofSeconds(5),
                Duration.ofMillis(100));
    }

}
