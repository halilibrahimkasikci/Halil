package beymen.pages;

import framework.core.Commands;
import framework.core.CommonTest;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.swing.*;
import java.util.List;
import java.util.Locale;

public class MainPage {
    @FindBy(xpath = "//span[contains(text(),'Sepetim')]")
    private WebElement btnBasket;
    @FindBy(xpath = "//input[@placeholder='Ürün, Marka Arayın']")
    private WebElement txtSearchInput;
    @FindBy(xpath = "//div[@id='productList']/div[@data-page='1']")
    private List<WebElement> tblProductList;
    @FindBy(xpath = "//div[@id='sizes']/div/span[not(contains(@class, '-disabled'))]")
    private WebElement btnNotDisableSizes;

    @FindBy(xpath = "//*[@class='m-basket__productInfoCategory']")
    private WebElement lblProductInfoCategory;
    @FindBy(xpath = "//*[@class='m-basket__productInfoName']")
    private WebElement lblProductInfoName;
    @FindBy(xpath = "//*[@class='m-productPrice__salePrice']")
    private WebElement lblSalePrice;

    @FindBy(xpath = "//button[contains(text(),'DAHA FAZLA GÖSTER')]")
    private WebElement btnMore;
    @FindBy(css = "#addBasket")
    private WebElement btnAddToBasket;

    public MainPage(WebDriver driver) { PageFactory.initElements(driver, this); }

    public void search(String keyword){
        operationTypeText(txtSearchInput,keyword);
        Commands.SendEnter(txtSearchInput);
    }
    public void randomSelectandAddToBasket() {
        int randomProductNo = RandomNumber(1,tblProductList.size());
        Commands.WaitUntilClickable(btnMore);
        //JOptionPane.showMessageDialog(null,randomProductNo);
        Commands.WaitUntilVisible(CommonTest.getDriver().findElement(By.xpath("//div[@id='productList']/div[@data-page='1']["+randomProductNo+"]")));
        Commands.moveToElement(CommonTest.getDriver().findElement(By.xpath("//div[@id='productList']/div[@data-page='1']["+randomProductNo+"]")));
        Commands.Click(CommonTest.getDriver().findElement(By.xpath("//div[@id='productList']/div[@data-page='1']["+randomProductNo+"]//div[@class='m-productCard__stock']/button")));

        Commands.WaitUntilClickable(btnNotDisableSizes);
        Commands.Click(btnNotDisableSizes);
        Commands.Click(btnAddToBasket);
        String productBrand = CommonTest.getDriver().findElement(By.xpath("//div[@class='m-productModal__detailInfo']//*[@class='m-productCard__title']")).getText();
        String productName = CommonTest.getDriver().findElement(By.xpath("//div[@class='m-productModal__detailInfo']//*[@class='m-productCard__desc']")).getText();
        String productPrice = CommonTest.getDriver().findElement(By.xpath("//div[@class='m-productModal__detailInfo']//*[@class='m-productCard__newPrice']")).getText();
        //JOptionPane.showMessageDialog(null,productBrand + " " + productName + " - " + productPrice);


        Commands.Click(btnBasket);
        setAttributeValue(lblProductInfoCategory,"style","text-transform: none !important");
        Assert.assertEquals(productBrand,lblProductInfoCategory.getText());
        Assert.assertEquals(productName,lblProductInfoName.getText());
        Assert.assertEquals(productPrice,lblSalePrice.getText());

    }

    public void basketControl() {

    }

    public void operationTypeText(WebElement elementName, String value) {
        Commands.WaitUntilVisible(elementName);
        Commands.moveToElementsAndClick(elementName);
        Commands.ClearArea(elementName);
        Commands.SendKeys(elementName, value);
    }

    public void operationSelectClear(String elementName) {
        Commands.moveToElement(CommonTest.getDriver().findElement(By.xpath("//form[@class='ant-form ant-form-horizontal']//label[text()='" + elementName + "']/..//input")));
        Commands.Click(CommonTest.getDriver().findElement(By.xpath("//form[@class='ant-form ant-form-horizontal']//label[text()='" + elementName + "']/..//span[@class='ant-select-selection__clear']")));
    }
    public static int RandomNumber(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }
    public void setAttributeValue(WebElement elem, String atribute , String value){
        JavascriptExecutor js = (JavascriptExecutor) CommonTest.getDriver();
        js.executeScript("arguments[0].setAttribute(arguments[1],arguments[2])",
                elem, atribute, value
        );
    }
}
