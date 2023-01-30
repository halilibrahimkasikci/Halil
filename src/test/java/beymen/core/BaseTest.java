package beymen.core;

import framework.core.CommonTest;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class BaseTest extends CommonTest {
    @Before()
    public void start() {
        WebDriver driver = getDriver();
        driver.get("https://beymen.com.tr");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
        driver.manage().addCookie(new Cookie("OptanonAlertBoxClosed", "2054-01-29T18:38:09.495Z"));
        driver.manage().addCookie(new Cookie("_dd_s", "rum=2&id=6c5176af-a7f4-4f2a-aa6b-53ad5283bbaf&created=1675017489483&expire=2653326042000"));
    }
    @After()
    public void end() {

    }
}
