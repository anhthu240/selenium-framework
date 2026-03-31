package framework.base;

import framework.config.ConfigReader;
import framework.driver.DriverFactory;
import framework.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;

public abstract class BaseTest {
    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    protected WebDriver getDriver() {
        return tlDriver.get();
    }

    @Parameters({"browser", "env"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser,
                      @Optional("dev") String env) {
        System.setProperty("env", env);

        WebDriver driver = DriverFactory.createDriver(browser);
        tlDriver.set(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(ConfigReader.getInstance().getBaseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (getDriver() != null && result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtil.capture(getDriver(), result.getName());
        }

        if (getDriver() != null) {
            getDriver().quit();
            tlDriver.remove();
        }
    }
}