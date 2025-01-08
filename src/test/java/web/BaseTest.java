package web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.BasePage;
import pages.NewItemsPage;
import utils.PropertyReader;

import java.time.Duration;

public class BaseTest {
    WebDriver driver;
    BasePage basePage;
    NewItemsPage newItemsPage;
    PropertyReader propertyReader;

    @BeforeMethod(alwaysRun = true, description = "Browser settings")
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        basePage = new BasePage(driver);
        newItemsPage = new NewItemsPage(driver);

        propertyReader = new PropertyReader();
    }

    @AfterMethod(alwaysRun = true, description = "Close browser")
    public void closeBrowser() {
        driver.quit();
    }
}
