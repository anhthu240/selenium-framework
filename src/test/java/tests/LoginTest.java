package tests;

import framework.base.BaseTest;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginSuccess() {
        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(false);
    }

    @Test
    public void testLoginLockedUser() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure("locked_out_user", "secret_sauce");
        Assert.assertTrue(loginPage.isErrorDisplayed());
    }

    @Test
    public void testLoginWrongPassword() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure("standard_user", "wrongpass");
        Assert.assertTrue(loginPage.isErrorDisplayed());
    }
}