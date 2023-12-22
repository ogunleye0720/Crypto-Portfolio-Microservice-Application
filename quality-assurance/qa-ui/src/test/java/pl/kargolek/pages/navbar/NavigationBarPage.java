package pl.kargolek.pages.navbar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.kargolek.pages.BasePage;

import java.time.Duration;

/**
 * @author Karol Kuta-Orlowicz
 */
public class NavigationBarPage extends BasePage {

    @FindBy(css = "mat-toolbar .nav-container")
    private WebElement navBarContainer;

    public NavigationBarPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getNavBarContainer(){
        return this.waitForElementVisibility(navBarContainer, Duration.ofSeconds(10));
    }
}
