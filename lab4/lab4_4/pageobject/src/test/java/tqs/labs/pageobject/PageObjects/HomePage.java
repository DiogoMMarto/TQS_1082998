package tqs.labs.pageobject.PageObjects;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;
 
    //Page URL
    private static String PAGE_URL="https://blazedemo.com/";
 
    @FindBy(xpath="//input[@value='Find Flights']")
    private WebElement subtmit;
 
    //Constructor
    public HomePage(WebDriver driver){
        this.driver=driver;
        driver.get(PAGE_URL);
        driver.manage().window().setSize(new Dimension(780, 960));
        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public String title(){
        return driver.getTitle();
    }

    public ReservePage search(){
        subtmit.click();
        return new ReservePage(driver);
    }
 
 }