package pl.kargolek.util;

import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Karol Kuta-Orlowicz
 */
@Getter
public class JavascriptDriver {

    private final WebDriver driver;
    private final JavascriptExecutor javascriptExecutor;

    public JavascriptDriver(JavascriptDriverBuilder builder) {
        this.driver = builder.driver;
        this.javascriptExecutor = builder.javascriptExecutor;
    }

    public Object getTopPositionInViewport(WebElement element) {
        return this.javascriptExecutor.executeScript(
                "return arguments[0].getBoundingClientRect().top;",
                element
        );
    }

    public Object scrollToElement(WebElement webElement){
        return this.javascriptExecutor.executeScript("arguments[0].scrollIntoView();", webElement);
    }

    public static JavascriptDriverBuilder builder(WebDriver driver) {
        return new JavascriptDriverBuilder(driver);
    }

    public static class JavascriptDriverBuilder {
        private final WebDriver driver;
        private JavascriptExecutor javascriptExecutor;

        public JavascriptDriverBuilder(WebDriver driver) {
            this.driver = driver;
        }

        public JavascriptDriverBuilder createJavascriptExecutor() {
            this.javascriptExecutor = (JavascriptExecutor) this.driver;
            return this;
        }

        public JavascriptDriver build() {
            return new JavascriptDriver(this);
        }
    }
}
