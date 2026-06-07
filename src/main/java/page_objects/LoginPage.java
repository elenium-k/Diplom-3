package page_objects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private final By loginPage = By.cssSelector("[class='Auth_form__3qKeq mb-20']");
    private final By registrationLink = By.cssSelector("[class='Auth_link__1fOlj']");
    private final By emailLoginField = By.cssSelector("[class='text input__textfield text_type_main-default'][type='text']");
    private final By passwordLoginField = By.cssSelector("[class='text input__textfield text_type_main-default'][type='password']");
    private final By enterButton = By.cssSelector("[class='button_button__33qZ0 button_button_type_primary__1O7Bx button_button_size_medium__3zxIa']");
    private final By recoveryPasswordLink = By.xpath("//*[contains(text(), 'Восстановить пароль')]");

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Проверка отображения страницы входа")
    public boolean isLoginPageDisplayed() {
        driver.findElement(loginPage).isDisplayed();
        return true;
    }

    @Step("Клик по ссылке регистрации")
    public RegistrationPage clickOnRegistrationLink() {
        driver.findElement(registrationLink).click();
        return new RegistrationPage(driver);
    }

    @Step("Клик на поле ввода email для входа")
    public void clickOnEmailLoginField() {
        driver.findElement(emailLoginField).click();
    }

    @Step("Ввод email для входа: {email}")
    public void typeInEmailLogin(String email) {
        driver.findElement(emailLoginField).sendKeys(email);
    }

    @Step("Клик на поле ввода пароля для входа")
    public void clickOnPasswordLoginField() {
        driver.findElement(passwordLoginField).click();
    }

    @Step("Ввод пароля для входа: {password}")
    public void typeInPasswordLogin(String password) {
        driver.findElement(passwordLoginField).sendKeys(password);
    }

    @Step("Клик на кнопку входа")
    public OrderPage clickOnEnterButton() {
        driver.findElement(enterButton).click();
        return new OrderPage(driver);
    }

    @Step("Клик по ссылке восстановления пароля")
    public RecoveryPasswordPage clickOnRecoveryPasswordLink() {
        driver.findElement(recoveryPasswordLink).click();
        return new RecoveryPasswordPage(driver);
    }
}