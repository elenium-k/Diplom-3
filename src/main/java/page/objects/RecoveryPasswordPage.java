package page.objects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RecoveryPasswordPage {

    private final By recoveryPasswordText = By.cssSelector("[class='Auth_login__3hAey']");
    private final By authLink = By.cssSelector("[class='Auth_link__1fOlj']");

    private WebDriver driver;

    public RecoveryPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Проверка отображения страницы восстановления пароля")
    public boolean isRecoveryPasswordPageDisplayed() {
        driver.findElement(recoveryPasswordText).isDisplayed();
        return true;
    }

    @Step("Клик по ссылке авторизации")
    public LoginPage clickOnAuthLink() {
        driver.findElement(authLink).click();
        return new LoginPage(driver);
    }
}
