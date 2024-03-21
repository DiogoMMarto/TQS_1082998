package tqs.labs;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BlazedemoSteps {

    private WebDriver driver;

    @Given("that I im on {string}")
    public void that_i_im_on(String string) {
        driver = WebDriverManager.chromedriver().create();
        driver.get(string);
        driver.manage().window().setSize(new Dimension(780, 960));
    }

    @When("I select departure and destination cities")
    public void i_select_departure_and_destination_cities() {
        driver.findElement(By.name("fromPort")).click();
        driver.findElement(By.name("toPort")).click();
    }

    @When("I click on Find Flights button")
    public void i_click_on_find_flights_button() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @Then("I go to the {string} page")
    public void i_go_to_the_find_fligths_page(String page) {
        assertTrue(driver.getTitle().equals(page));
    }

    @When("I select a flight")
    public void i_select_a_flight() {
        driver.findElement(By.cssSelector("tr:nth-child(1) .btn")).click();
    }

    @When("I fill in personal information")
    public void i_fill_in_personal_information() {
        driver.findElement(By.id("inputName")).click();
        driver.findElement(By.id("inputName")).sendKeys("Diogo");
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys("123 Main str.");
        driver.findElement(By.id("city")).click();
        driver.findElement(By.id("city")).sendKeys("aveiro");
        driver.findElement(By.id("state")).click();
        driver.findElement(By.id("state")).sendKeys("aveiro");
        driver.findElement(By.id("zipCode")).click();
        driver.findElement(By.id("zipCode")).sendKeys("12345");
        driver.findElement(By.id("cardType")).click();
        driver.findElement(By.id("creditCardNumber")).click();
        driver.findElement(By.id("creditCardNumber")).sendKeys("1234567890");
        driver.findElement(By.id("nameOnCard")).click();
        driver.findElement(By.id("nameOnCard")).sendKeys("Jonh Smith");
    }

    @When("I proceed to purchase")
    public void i_proceed_to_purchase() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @Then("the purchase is confirmed successfull")
    public void the_purchase_is_confirmed_successfull() {
        assertTrue(driver.getTitle().equals("BlazeDemo Confirmation"));
    }

}