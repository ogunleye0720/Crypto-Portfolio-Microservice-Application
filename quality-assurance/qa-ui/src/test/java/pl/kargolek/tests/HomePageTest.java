package pl.kargolek.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;
import org.openqa.selenium.WebDriver;
import pl.kargolek.extension.BaseTestConfig;
import pl.kargolek.pages.InitPages;
import pl.kargolek.util.TestProperty;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Karol Kuta-Orlowicz
 */
@Tags({
        @Tag("HomePageUI"),
        @Tag("RoutingApp")
})
@Epic("Home page")
@BaseTestConfig
public class HomePageTest {

    private String appBaseURL;

    @BeforeEach
    public void setup(TestProperty property) {
        this.appBaseURL = property.getAppBaseURL();
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Routing app")
    @Story("As normal user, when I open base app URL, then app redirect to URL path '/wallet'")
    @Description("Opening main URL app runs routing to /wallet path")
    public void whenOpenAppBaseURL_thenRedirectToWalletPath(WebDriver driver, InitPages pages) {
        pages.getHomePage()
                .open(this.appBaseURL);
        assertThat(driver.getCurrentUrl()).contains("/wallet");
    }
}
