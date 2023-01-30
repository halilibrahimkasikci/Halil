package beymen.test;

import beymen.core.BaseTest;
import beymen.pages.MainPage;
import org.junit.Test;

import javax.swing.*;

public class Assigment extends BaseTest {
    MainPage mainPage;

    @Test
    public void assigment() {
        mainPage = new MainPage(getDriver());
        mainPage.search("şort");
        mainPage.search("gömlek");
        mainPage.randomSelectandAddToBasket();
        mainPage.basketControl();
    }

}
