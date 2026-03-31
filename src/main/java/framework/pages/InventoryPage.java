package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(css = ".inventory_list")
    private WebElement inventoryList;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = ".inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartLink;

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isElementVisible(By.cssSelector(".inventory_list"));
    }

    public InventoryPage addFirstItemToCart() {
        WebElement firstButton = inventoryItems.get(0).findElement(By.tagName("button"));
        waitAndClick(firstButton);
        return this;
    }

    public InventoryPage addItemByName(String name) {
        for (WebElement item : inventoryItems) {
            String itemName = item.findElement(By.className("inventory_item_name")).getText().trim();
            if (itemName.equalsIgnoreCase(name)) {
                WebElement btn = item.findElement(By.tagName("button"));
                waitAndClick(btn);
                break;
            }
        }
        return this;
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(cartBadge.getText().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public CartPage goToCart() {
        waitAndClick(cartLink);
        return new CartPage(driver);
    }
}