package framework.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class DriverFactory {

    public static WebDriver createDriver(String browser) {
        String gridUrl = System.getProperty("grid.url");
        boolean isCI = System.getenv("CI") != null;

        if (gridUrl != null && !gridUrl.isBlank()) {
            return createRemoteDriver(browser, gridUrl);
        }

        return switch (browser.toLowerCase()) {
            case "firefox" -> createFirefoxDriver(isCI);
            default -> createChromeDriver(isCI);
        };
    }

    private static WebDriver createRemoteDriver(String browser, String gridUrl) {
        try {
            return switch (browser.toLowerCase()) {
                case "firefox" -> {
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    yield new RemoteWebDriver(new URL(gridUrl), firefoxOptions);
                }
                default -> {
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    yield new RemoteWebDriver(new URL(gridUrl), chromeOptions);
                }
            };
        } catch (Exception e) {
            throw new RuntimeException("Không kết nối được Selenium Grid: " + gridUrl, e);
        }
    }

    private static WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
            System.out.println("[Driver] Chrome headless on CI");
        } else {
            options.addArguments("--start-maximized");
            System.out.println("[Driver] Chrome local mode");
        }

        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();

        if (headless) {
            options.addArguments("-headless");
            System.out.println("[Driver] Firefox headless on CI");
        } else {
            System.out.println("[Driver] Firefox local mode");
        }

        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(options);
    }
}