package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    public final String BASE_URL = "https://www.oodji.com/";
    WebDriverWait wait;
    WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void openMain() {
        driver.get(BASE_URL);
    }
}
