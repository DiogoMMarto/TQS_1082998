package tqs.labs.pageobject.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PurchasePage {
    private WebDriver driver;

    @FindBy(css="p:nth-child(4)")
    private WebElement price;

    @FindBy(id = "nameInput")
    private WebElement nameInput;

    @FindBy(id = "address")
    private WebElement address;

    @FindBy(id = "city")
    private WebElement city;

    @FindBy(id = "state")
    private WebElement state;

    @FindBy(id = "zipCode")
    private WebElement zipCode;

    @FindBy(id = "cardType")
    private WebElement cardType;

    @FindBy(id = "creditCardNumber")
    private WebElement creditCardNumber;

    @FindBy(id = "nameOnCard")
    private WebElement nameOnCard;

    @FindBy(css=".btn-primary")
    private WebElement purchaseButton;

    public PurchasePage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }
}
