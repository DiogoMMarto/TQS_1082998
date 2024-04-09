package tqs.homework.busbook.bdd;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BookSteps {
    
    private WebDriver driver;

    @Given("that I im on {string}")
    public void that_i_im_on(String string) {
        driver = WebDriverManager.chromedriver().create();
        driver.get(string);
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(780, 960));
    }

    @When("I select departure and destination cities")
    public void i_select_departure_and_destination_cities() {
        driver.findElement(By.id("departureCity")).click();
        {
        WebElement dropdown = driver.findElement(By.id("departureCity"));
        dropdown.findElement(By.xpath("//option[. = 'New York']")).click();
        }
        driver.findElement(By.id("destinationCity")).click();
        {
        WebElement dropdown = driver.findElement(By.id("destinationCity"));
        dropdown.findElement(By.xpath("//option[. = 'Los Angeles']")).click();
        }
    }

    @When("I select the dates")
    public void i_select_the_dates() {
        driver.findElement(By.id("dateStart")).click();
        driver.findElement(By.id("dateStart")).click();
        driver.findElement(By.id("dateStart")).sendKeys("2024-04-01");
        driver.findElement(By.id("dateEnd")).click();
        driver.findElement(By.id("dateEnd")).sendKeys("2024-04-17");
    }

    @When("I select return trip")
    public void i_select_return_trip() {
        driver.findElement(By.id("returnTrip")).click();
    }

    @When("I click on Find Trips button")
    public void i_click_on_find_trips_button() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }
    
    @Then("I go to the {string} page")
    public void i_go_to_page(String page) {
        System.out.println(driver.getTitle());
        assertTrue(driver.getTitle().equals(page));
    }

    @When("I select a seat")
    public void i_select_a_seat() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
        {
          WebElement element = driver.findElement(By.cssSelector(".btn-primary"));
          Actions builder = new Actions(driver);
          builder.moveToElement(element).perform();
        }
        {
          WebElement element = driver.findElement(By.tagName("body"));
          Actions builder = new Actions(driver);
          builder.moveToElement(element, 0, 0).perform();
        }
        driver.findElement(By.cssSelector("div:nth-child(7) > input")).click();
        driver.findElement(By.cssSelector(".close > span")).click();
    }

    @When("I click on the 'Reserve' button")
    public void i_click_on_the_reserve_button() {
        driver.findElement(By.cssSelector(".btn-success")).click();
    }

    @When("I fill in personal information")
    public void i_fill_in_personal_information() {
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).sendKeys("Diogo");
    }

    @When("I proceed to purchase")
    public void i_proceed_to_purchase() {
        driver.findElement(By.cssSelector(".btn")).click();
    }

    @When("I click on the 'Confirm' button")
    public void i_click_on_the_confirm_button() {
        driver.findElement(By.cssSelector(".btn")).click();
    }

}
