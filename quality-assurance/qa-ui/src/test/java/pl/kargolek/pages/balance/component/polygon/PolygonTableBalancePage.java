package pl.kargolek.pages.balance.component.polygon;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.kargolek.pages.balance.component.TableBalancePage;

/**
 * @author Karol Kuta-Orlowicz
 */
public class PolygonTableBalancePage extends TableBalancePage {

    @FindBy(css = "app-polygon-balance")
    private WebElement polygonBalanceComponent;

    public PolygonTableBalancePage(WebDriver driver) {
        super(driver);
    }

    @Step("Get polygon balance component")
    @Override
    public WebElement getParent() {
        return polygonBalanceComponent;
    }
}
