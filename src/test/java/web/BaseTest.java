package web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.BasePage;
import utils.PropertyReader;

import java.io.IOException;
import java.time.Duration;

public class BaseTest {
    BasePage basePage;
    WebDriver driver;

    protected static PropertyReader propertyReader;
    private static String propertiesPath = "/config.properties";

    @BeforeMethod(alwaysRun = true, description = "Browser settings")
    public void setup() throws IOException {
        driver = new ChromeDriver();
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        propertyReader = new PropertyReader(propertiesPath);
    }

    @AfterMethod(alwaysRun = true, description = "Close browser")
    public void tearDown() {
        driver.quit();
    }
}
