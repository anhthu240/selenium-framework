package tests;

import framework.base.BaseTest;
import framework.models.UserData;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import framework.utils.JsonReader;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class UserLoginTest extends BaseTest implements ITest {

    private final ThreadLocal<String> testName = new ThreadLocal<>();

    @Override
    public String getTestName() {
        return testName.get();
    }

    @DataProvider(name = "jsonUsers")
    public Object[][] getUsersFromJson() throws IOException {
        List<UserData> users = JsonReader.readUsers("src/test/resources/testdata/users.json");
        return users.stream()
                .map(u -> new Object[]{u.username, u.password, u.expectSuccess, u.description})
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "jsonUsers")
    public void testLoginFromJson(String username, String password, boolean expectSuccess, String description) {
        testName.set(description);

        LoginPage loginPage = new LoginPage(getDriver());

        if (expectSuccess) {
            InventoryPage inventoryPage = loginPage.login(username, password);
            Assert.assertTrue(inventoryPage.isLoaded());
        } else {
            loginPage.loginExpectingFailure(username, password);
            Assert.assertTrue(loginPage.isErrorDisplayed());
        }
    }
}