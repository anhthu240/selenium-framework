package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    // Locator động (KHÔNG dùng list cache)
    private final By cartItemsLocator = By.cssSelector(".cart_item");
    private final By removeButtonsLocator = By.cssSelector(".cart_button");

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Lấy số lượng item hiện tại trong cart (luôn đọc lại từ DOM)
     */
    public int getItemCount() {
        return driver.findElements(cartItemsLocator).size();
    }

    /**
     * Xóa item đầu tiên và CHỜ DOM update xong
     */
    public CartPage removeFirstItem() {
        List<WebElement> removeButtons = driver.findElements(removeButtonsLocator);

        if (!removeButtons.isEmpty()) {
            int beforeCount = getItemCount();

            waitAndClick(removeButtons.get(0));

            // Chờ cho số item giảm xuống
            wait.until(d -> d.findElements(cartItemsLocator).size() == beforeCount - 1);
        }

        return this;
    }

    /**
     * Chuyển sang trang checkout
     */
    public CheckoutPage goToCheckout() {
        waitAndClick(checkoutButton);
        return new CheckoutPage(driver);
    }

    /**
     * Lấy danh sách tên sản phẩm trong cart
     */
    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();

        List<WebElement> cartItems = driver.findElements(cartItemsLocator);

        for (WebElement item : cartItems) {
            String name = item.findElement(By.className("inventory_item_name"))
                              .getText()
                              .trim();
            names.add(name);
        }

        return names;
    }
}