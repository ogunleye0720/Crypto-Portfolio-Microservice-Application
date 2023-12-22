package pl.kargolek.pages.balance.component.avalanche;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.kargolek.pages.balance.component.TableAmountPage;

/**
 * @author Karol Kuta-Orlowicz
 */
public class AvalancheTableAmountPage extends TableAmountPage {

    @FindBy(css = "app-avalanche-balance")
    private WebElement avalancheBalanceComponent;

    public AvalancheTableAmountPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get avalanche balance component")
    @Override
    protected WebElement getParent() {
        return this.avalancheBalanceComponent;
    }
}
