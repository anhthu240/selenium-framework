package tests;

import framework.base.BaseTest;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import framework.utils.ExcelReader;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginDDTTest extends BaseTest implements ITest {

    private final ThreadLocal<String> testName = new ThreadLocal<>();

    @Override
    public String getTestName() {
        return testName.get();
    }

    @DataProvider(name = "smokeExcelData")
    public Object[][] smokeExcelData() {
        return ExcelReader.getData("src/test/resources/testdata/login_data.xlsx", "SmokeCases");
    }

    @DataProvider(name = "negativeExcelData")
    public Object[][] negativeExcelData() {
        return ExcelReader.getData("src/test/resources/testdata/login_data.xlsx", "NegativeCases");
    }

    @DataProvider(name = "boundaryExcelData")
    public Object[][] boundaryExcelData() {
        return ExcelReader.getData("src/test/resources/testdata/login_data.xlsx", "BoundaryCases");
    }

    @Test(dataProvider = "smokeExcelData", groups = "smoke")
    public void testLoginSmoke(String username, String password, String expectedUrl, String description) {
        testName.set(description);

        InventoryPage inventoryPage = new LoginPage(getDriver()).login(username, password);
        Assert.assertTrue(getDriver().getCurrentUrl().contains(expectedUrl));
        Assert.assertTrue(inventoryPage.isLoaded());
    }

    @Test(dataProvider = "negativeExcelData", groups = "regression")
    public void testLoginNegative(String username, String password, String expectedError, String description) {
        testName.set(description);

        LoginPage loginPage = new LoginPage(getDriver()).loginExpectingFailure(username, password);
        Assert.assertTrue(loginPage.isErrorDisplayed());
        Assert.assertEquals(loginPage.getErrorMessage(), expectedError);
    }

    @Test(dataProvider = "boundaryExcelData", groups = "regression")
    public void testLoginBoundary(String username, String password, String expectedError, String description) {
        testName.set(description);

        LoginPage loginPage = new LoginPage(getDriver()).loginExpectingFailure(username, password);
        Assert.assertTrue(loginPage.isErrorDisplayed());
        Assert.assertEquals(loginPage.getErrorMessage(), expectedError);
    }
}