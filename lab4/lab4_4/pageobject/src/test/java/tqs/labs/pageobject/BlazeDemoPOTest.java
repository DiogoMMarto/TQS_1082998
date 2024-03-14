package tqs.labs.pageobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.hamcrest.MatcherAssert.assertThat;

import org.openqa.selenium.WebDriver;
import static org.hamcrest.CoreMatchers.is;

import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import tqs.labs.pageobject.PageObjects.ConfirmationPage;
import tqs.labs.pageobject.PageObjects.HomePage;
import tqs.labs.pageobject.PageObjects.PurchasePage;
import tqs.labs.pageobject.PageObjects.ReservePage;

import static io.github.bonigarcia.seljup.BrowserType.CHROME;

@ExtendWith(SeleniumJupiter.class)
class BlazeDemoPOTest{

    @Test
    void test(@DockerBrowser(type = CHROME) WebDriver driver) {
        
    HomePage home_page = new HomePage(driver);
    assertThat(home_page.title(), is("BlazeDemo"));

    ReservePage reserve_page = home_page.search();
    assertThat(reserve_page.title(), is("BlazeDemo - reserve"));

    PurchasePage purchase_page = reserve_page.purchase();
    assertThat(purchase_page.title(), is("BlazeDemo Purchase"));
    assertThat(purchase_page.priceText(), is("Price: 400"));
    
    purchase_page.setName("Diogo");
    purchase_page.setAddress("123 Main .");
    purchase_page.setCity("Aveiro");
    purchase_page.setState("Aveiro");
    purchase_page.setZipCode("12345");
    purchase_page.setCreditCardNumber("1234567890");
    purchase_page.setNameOnCard("Diogo Marto");;

    ConfirmationPage confirmation_page = purchase_page.purchase();
    assertThat(confirmation_page.title(), is("BlazeDemo Confirmation"));

    }

}