package tqs.labs.pageobject.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReservePage {
    private WebDriver driver;


    @FindBy(css = "tr:nth-child(1) .btn")
    private WebElement selectFligthButton;

    public ReservePage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    public String title(){
        return driver.getTitle();
    }

    public PurchasePage purchase(){
        selectFligthButton.click();
        return new PurchasePage(driver);
    }
}
