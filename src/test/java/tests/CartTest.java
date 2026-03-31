package tests;

import framework.base.BaseTest;
import framework.pages.CartPage;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    @Test
    public void testAddFirstItemToCart() {
        InventoryPage inventoryPage = new LoginPage(getDriver())
                .login("standard_user", "secret_sauce")
                .addFirstItemToCart();

        Assert.assertEquals(inventoryPage.getCartItemCount(), 1);
    }

    @Test
    public void testGoToCartAfterAddItem() {
        CartPage cartPage = new LoginPage(getDriver())
                .login("standard_user", "secret_sauce")
                .addFirstItemToCart()
                .goToCart();

        Assert.assertEquals(cartPage.getItemCount(), 1);
    }

    @Test
    public void testRemoveFirstItem() {
        CartPage cartPage = new LoginPage(getDriver())
                .login("standard_user", "secret_sauce")
                .addFirstItemToCart()
                .goToCart()
                .removeFirstItem();

        Assert.assertEquals(cartPage.getItemCount(), 0);
    }
}