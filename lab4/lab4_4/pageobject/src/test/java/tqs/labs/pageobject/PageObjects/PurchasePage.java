package tqs.labs.pageobject.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PurchasePage {
    private WebDriver driver;

    @FindBy(css="p:nth-child(4)")
    private WebElement price;

    @FindBy(name = "inputName")
    private WebElement inputName;

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

    public String title(){
        return driver.getTitle();
    }

    public String priceText(){
        return price.getText();
    }

    public void setName(String name){
        inputName.click();
        inputName.sendKeys(name);
    }

    public void setAddress(String address){
        this.address.click();
        this.address.sendKeys(address);
    }

    public void setCity(String city){
        this.city.click();
        this.city.sendKeys(city);
    }

    public void setState(String state){
        this.state.click();
        this.state.sendKeys(state);
    }

    public void setZipCode(String zipCode){
        this.zipCode.click();
        this.zipCode.sendKeys(zipCode);
    }

    public void setCreditCardNumber(String creditCardNumber){
        this.creditCardNumber.click();
        this.creditCardNumber.sendKeys(creditCardNumber);
    }
    
    public void setNameOnCard(String nameOnCard){
        this.nameOnCard.click();
        this.nameOnCard.sendKeys(nameOnCard);
    }

    public ConfirmationPage purchase(){
        purchaseButton.click();
        return new ConfirmationPage(driver);
    }
}

