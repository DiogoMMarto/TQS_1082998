package tqs.labs.webdriverhello;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SeleniumJupiter.class)
public class BlazeDemoSeleniumTest {

  @Test
  public void blazedemo(ChromeDriver driver) {
    driver.get("https://blazedemo.com/");
    driver.manage().window().setSize(new Dimension(780, 960));
    assertThat(driver.getTitle(), is("BlazeDemo"));
    driver.findElement(By.name("fromPort")).click();
    driver.findElement(By.name("toPort")).click();
    driver.findElement(By.cssSelector(".btn-primary")).click();
    assertThat(driver.getTitle(), is("BlazeDemo - reserve"));
    driver.findElement(By.cssSelector("tr:nth-child(1) .btn")).click();
    assertThat(driver.getTitle(), is("BlazeDemo Purchase"));
    assertThat(driver.findElement(By.cssSelector("p:nth-child(4)")).getText(), is("Price: 400"));
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
    driver.findElement(By.cssSelector(".btn-primary")).click();
    assertThat(driver.getTitle(), is("BlazeDemo Confirmation"));
  }
}
