package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

@Log4j2
public class NewItemsPage extends BasePage {

    public NewItemsPage(WebDriver driver) {
        super(driver);
    }

    private final String NEW_ITEMS_URL = BASE_URL + "womens_collection/new/";
    private final By CHECKBOX_FOR_NEW_FILTER = By.xpath("//div[@class='filterItem']/descendant::input[@type='checkbox']");
    private final By NEW_ITEMS_MENU = By.className("menu-b");
    private final By NEW_WOMAN_CLOTHES = By.cssSelector(".subMenu a[href='/womens_collection/new/']");
    private final By NEW_MAN_CLOTHES = By.cssSelector(".subMenu a[href='/mens_collection/new/']");

    @Step("Open New items page")
    public void openNewItemsPage() {
        log.info("Open New items page");
        driver.get(NEW_ITEMS_URL);
    }

    @Step("Check if checkbox-filter 'New item' is on")
    public void checkForNewFilterIsOn() throws NoSuchElementException {
        log.info("Check if checkbox-filter 'New' is on");
        WebElement checkbox = driver.findElement(CHECKBOX_FOR_NEW_FILTER);
        assert checkbox.isSelected();
    }

    @Step("Open 'New items' burger menu")
    public void openBurgerMenuNewItems() {
        log.info("Open New items burger menu");
        WebElement burgerMenu = driver.findElement(NEW_ITEMS_MENU);
        Actions action = new Actions(driver);
        action.moveToElement(burgerMenu).perform();
    }

    @Step("Click on Woman collection new")
    public void clickOnNewWomanCollection() {
        log.info("Click on New Woman Collection");
        driver.findElement(NEW_WOMAN_CLOTHES).click();
    }

    @Step("Click on Man collection new")
    public void clickOnNewManCollection() {
        log.info("Click on New Man Collection");
        driver.findElement(NEW_MAN_CLOTHES).click();
    }

    @Step("Check if we are on current page")
    public String checkIfCurrentPageIsOpen() {
        log.info("Check if we are on current page");
        return driver.getCurrentUrl().toString();
    }
}

