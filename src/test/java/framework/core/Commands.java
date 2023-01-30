package framework.core;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;

public class Commands {

    public static void Click(WebElement webElement) {
        WaitUntilVisible(webElement);
        WaitUntilClickable(webElement);
        try {
            webElement.click();
        } catch (Exception e) {
            JavascriptExecutor executor = (JavascriptExecutor) CommonTest.getDriver();
            executor.executeScript("arguments[0].click();", webElement);
        }
    }


    public static void clearLocalStorage() {
        JavascriptExecutor js = (JavascriptExecutor) CommonTest.getDriver();
        js.executeScript(String.format("window.localStorage.clear();"));
    }

    public static void scrollToElement(WebElement element) {
        Actions actions = new Actions(CommonTest.getDriver());
        actions.scrollToElement(element).perform();
    }
    public static void moveToElement(WebElement element) {
        //scrollToElement(element);
        Actions actions = new Actions(CommonTest.getDriver());
        Action action = actions.moveToElement(element).build();
        action.perform();
    }

    public static void moveToElementsAndClick(WebElement element) {
        Actions actions = new Actions(CommonTest.getDriver());
        Action action = actions.moveToElement(element).click().build();
        action.perform();
    }

    public static void SendKeys(WebElement webElement, String string) {
        WaitUntilVisible(webElement);
        ClearArea(webElement);
        try {
            webElement.sendKeys(string);
        } catch (Exception e) {
            JavascriptExecutor ex = (JavascriptExecutor) CommonTest.getDriver();
            ex.executeScript("arguments[0].value='" + string + "';", webElement);
        }
    }
    public static void CtrlADeleteAndSendKeys(WebElement webElement, String keys) { // Bu ve alttaki 2 method incelenmeli.
        webElement.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        for(char c:keys.toCharArray()){
            webElement.sendKeys(Character.toString(c));
        }
    }
    public static void ClearArea(WebElement webElement) {
        webElement.clear();
        webElement.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    }

    public static void ClearAndSendKeysWithAction(WebElement webElement, String keys) {
        WaitUntilVisible(webElement);
        WaitUntilClickable(webElement);
        Actions actions = new Actions(CommonTest.getDriver());
        Action action = actions.moveToElement(webElement)
                .doubleClick()
                .sendKeys(Keys.chord(Keys.CONTROL,"a"))
                .sendKeys(Keys.BACK_SPACE)
                .sendKeys(keys)
                .build();
        action.perform();
    }
    public static void SendEnter(WebElement webElement) {
        WaitUntilVisible(webElement);
        WaitUntilClickable(webElement);
        Actions actions = new Actions(CommonTest.getDriver());
        Action action = actions.moveToElement(webElement)
                .sendKeys(Keys.ENTER)
                .build();
        action.perform();
    }


    public static String GetText(WebElement webElement) {
        WaitUntilVisible(webElement);
        return webElement.getText();
    }

    public static void WaitUntilClickable(WebElement element) {
        int attempts = 0;
        while(attempts < 30) {
            try {
                new WebDriverWait(CommonTest.getDriver(), Duration.ofSeconds(1)).until(ExpectedConditions.elementToBeClickable(element));
                break;
            }
            catch(Exception e) {
            }
            attempts++;
        }
    }
    public static void WaitUntilVisible(WebElement element) {
        CommonTest.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        int attempts = 0;
        while(attempts < 30) {
            try {
                WebDriverWait wait = new WebDriverWait(CommonTest.getDriver(), Duration.ofSeconds(1));
                wait.until(ExpectedConditions.visibilityOf(element));
                break;
            }
            catch(Exception e) {
            }
            attempts++;
        }
        CommonTest.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

}
