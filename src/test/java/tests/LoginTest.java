package tests;

import framework.base.BaseTest;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import framework.utils.CredentialUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginSuccess() {
        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.login(
                CredentialUtils.getUsername(),
                CredentialUtils.getPassword()
        );

        Assert.assertTrue(inventoryPage.isLoaded());
    }

    @Test
    public void testLoginLockedUser() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure(
                "locked_out_user",
                CredentialUtils.getPassword()
        );

        Assert.assertTrue(loginPage.isErrorDisplayed());
    }

    @Test
    public void testLoginWrongPassword() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure(
                CredentialUtils.getUsername(),
                "wrongpass"
        );

        Assert.assertTrue(loginPage.isErrorDisplayed());
    }
}