package page_objects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {

    private final By registrationPage = By.cssSelector("[class='Auth_form__3qKeq mb-20']");
    private final By nameField = By.cssSelector("[class='text input__textfield text_type_main-default']");
    private final By emailField = By.xpath(".//label[contains(@class, 'input__placeholder') and contains(text(), 'Email')]/following::input[1]");
    private final By passwordField = By.cssSelector("[class='text input__textfield text_type_main-default'][type='password']");
    private final By registrateNewUserButton = By.cssSelector("[class='button_button__33qZ0 button_button_type_primary__1O7Bx button_button_size_medium__3zxIa']");
    private final By wrongPasswordMessage = By.cssSelector("[class='input pr-6 pl-6 input_type_password input_size_default input_status_error']");
    private final By authLink = By.cssSelector("[class='Auth_link__1fOlj']");

    private WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Проверка отображения страницы регистрации")
    public boolean isRegistrationPageDisplayed() {
        driver.findElement(registrationPage).isDisplayed();
        return true;
    }

    @Step("Клик на поле ввода имени")
    public void clickOnNameField() {
        driver.findElement(nameField).click();
    }

    @Step("Ввод имени")
    public void typeInName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    @Step("Клик на поле ввода email")
    public void clickOnEmailField() {
        driver.findElement(emailField).click();
    }

    @Step("Ввод email")
    public void typeInEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Клик на поле ввода пароля")
    public void clickOnPasswordField() {
        driver.findElement(passwordField).click();
    }

    @Step("Ввод пароля")
    public void typeInPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Клик на кнопку регистрации нового пользователя")
    public LoginPage clickOnRegistrateNewUserButton() {
        driver.findElement(registrateNewUserButton).click();
        return new LoginPage(driver);
    }

    @Step("Проверка отображения сообщения об ошибке пароля")
    public boolean isWrongPasswordMessageDisplayed() {
        driver.findElement(wrongPasswordMessage).isDisplayed();
        return true;
    }

    @Step("Клик по ссылке авторизации")
    public LoginPage clickOnAuthLink() {
        driver.findElement(authLink).click();
        return new LoginPage(driver);
    }
}