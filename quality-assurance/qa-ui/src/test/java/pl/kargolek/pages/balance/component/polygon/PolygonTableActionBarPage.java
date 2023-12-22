package pl.kargolek.pages.balance.component.polygon;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.kargolek.pages.balance.component.TableActionBarPage;

/**
 * @author Karol Kuta-Orlowicz
 */
public class PolygonTableActionBarPage extends TableActionBarPage {
    @FindBy(css = "app-polygon-balance")
    private WebElement polygonBalanceComponent;

    public PolygonTableActionBarPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get polygon balance component")
    @Override
    protected WebElement getParent() {
        return this.polygonBalanceComponent;
    }
}
