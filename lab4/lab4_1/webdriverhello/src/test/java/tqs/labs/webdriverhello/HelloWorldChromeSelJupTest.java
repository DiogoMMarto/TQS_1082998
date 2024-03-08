package tqs.labs.webdriverhello;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SeleniumJupiter.class)
class HelloWorldChromeSelJupTest {

    @Test
    void test(ChromeDriver driver) {
         // Exercise
         String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
         driver.get(sutUrl); 
         String title = driver.getTitle(); 
 
         // Verify
         assertThat(title).isEqualTo("Hands-On Selenium WebDriver with Java"); 
    }

}