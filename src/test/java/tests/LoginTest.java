package tests;

import framework.base.BaseTest;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import framework.utils.CredentialUtils;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;

@Listeners({AllureTestNg.class})
public class LoginTest extends BaseTest {

    @Test(groups = "smoke")
    @Feature("Đăng nhập")
    @Story("Đăng nhập hợp lệ")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra đăng nhập thành công với tài khoản hợp lệ")
    public void testLoginSuccess() {
        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.login(
                CredentialUtils.getUsername(),
                CredentialUtils.getPassword()
        );

        Assert.assertTrue(inventoryPage.isLoaded());
    }

    @Test
    @Feature("Đăng nhập")
    @Story("Từ chối tài khoản bị khóa")
    @Severity(SeverityLevel.NORMAL)
    @Description("Kiểm tra hệ thống hiển thị lỗi khi đăng nhập bằng tài khoản locked_out_user")
    public void testLoginLockedUser() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure("locked_out_user", CredentialUtils.getPassword());

        Assert.assertTrue(loginPage.isErrorDisplayed());
    }

    @Test
    @Feature("Đăng nhập")
    @Story("Sai mật khẩu")
    @Severity(SeverityLevel.NORMAL)
    @Description("Kiểm tra hệ thống hiển thị lỗi khi nhập sai mật khẩu")
    public void testLoginWrongPassword() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure(CredentialUtils.getUsername(), "wrongpass");

        Assert.assertTrue(loginPage.isErrorDisplayed());
    }
}