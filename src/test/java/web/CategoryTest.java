package web;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import static pages.constants.URLConstants.MAN_NEW_CLOTHES;
import static pages.constants.URLConstants.WOMAN_NEW_CLOTHES;

public class CategoryTest extends BaseTest {

    @Description("Test if checkbox-filter for New clothes is checked")
    @Test
    public void checkIfFilterNewIsWorkingInNewClothesPage() {
        newItemsPage.openNewItemsPage();
        newItemsPage.checkForNewFilterIsOn();
    }

    @Description("Test if click on 'New woman collection' redirects on 'womens_collection/new/'")
    @Test
    public void checkNewWomanClothesRedirection() {
        basePage.openMain();
        newItemsPage.openBurgerMenuNewItems();
        newItemsPage.clickOnNewWomanCollection();
        Assert.assertEquals(newItemsPage.checkIfCurrentPageIsOpen(), WOMAN_NEW_CLOTHES, "Wrong page is open");
    }

    @Description("Test if click on 'New man collection' redirects on 'mens_collection/new/'")
    @Test
    public void checkNewManClothesRedirection() {
        basePage.openMain();
        newItemsPage.openBurgerMenuNewItems();
        newItemsPage.clickOnNewManCollection();
        Assert.assertEquals(newItemsPage.checkIfCurrentPageIsOpen(), MAN_NEW_CLOTHES, "Wrong page is open");
    }
}
