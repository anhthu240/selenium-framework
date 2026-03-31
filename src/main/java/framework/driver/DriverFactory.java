package framework.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    public static WebDriver createDriver(String browser) {
        boolean isCI = System.getenv("CI") != null;

        return switch (browser.toLowerCase()) {
            case "firefox" -> createFirefoxDriver(isCI);
            default -> createChromeDriver(isCI);
        };
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