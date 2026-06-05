package page_objects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderPage {

    private final By orderButton = By.cssSelector("[class='button_button__33qZ0 button_button_type_primary__1O7Bx button_button_size_large__G21Vg']");

    private WebDriver driver;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Проверка отображения страницы заказа")
    public boolean isOrderPageDisplayed() {
        driver.findElement(orderButton).isDisplayed();
        return true;
    }
}