package framework.base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /** Click vào element sau khi chờ element có thể click */
    protected void waitAndClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    /** Xóa dữ liệu cũ rồi nhập text mới */
    protected void waitAndType(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    /** Lấy text đã loại bỏ khoảng trắng thừa */
    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText().trim();
    }

    /** Kiểm tra element có hiển thị không, không làm văng exception */
    protected boolean isElementVisible(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /** Cuộn tới element */
    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /** Chờ trang load xong */
    protected void waitForPageLoad() {
        wait.until(driver ->
                ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    /** Lấy giá trị attribute */
    protected String getAttribute(WebElement element, String attr) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getAttribute(attr);
    }
}