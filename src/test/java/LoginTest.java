import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import page_objects.*;

import static org.junit.Assert.assertTrue;

public class LoginTest {

    private static final String EMAIL = "bubenchik3@yandex.ru";
    private static final String PASSWORD = "1111122222";

    @Rule
    public FactoryDriver factoryDriver = new FactoryDriver();

    private WebDriver driver;
    private MainPage mainPage;

    // Общий запуск драйвера для всех тестов
    @org.junit.Before
    public void setUp() {
        driver = factoryDriver.getDriver();
        mainPage = new MainPage(driver);
        mainPage.openPage();
    }

    // Общий метод для заполнения формы входа
    private void fillLoginForm(LoginPage loginPage) {
        loginPage.clickOnEmailLoginField();
        loginPage.typeInEmailLogin(EMAIL);
        loginPage.clickOnPasswordLoginField();
        loginPage.typeInPasswordLogin(PASSWORD);
    }

    // Общий метод для проверки успешного логина
    private void assertSuccessfulLogin(LoginPage loginPage) {
        OrderPage orderPage = loginPage.clickOnEnterButton();
        assertTrue(orderPage.isOrderPageDisplayed());
    }


    @Test
    @DisplayName("Вход в аккаунт по кнопке Войти")
    public void loginTestFromEnterButton() {
        LoginPage loginPage = mainPage.clickOnEnterAccountButton();
        assertTrue(loginPage.isLoginPageDisplayed());

        fillLoginForm(loginPage);
        assertSuccessfulLogin(loginPage);
    }

    @Test
    @DisplayName("Вход в аккаунт через кнопку Личный кабинет")
    public void loginTestFromPersonalAccountButton() {
        LoginPage loginPage = mainPage.clickOnPersonalAccountButton();
        assertTrue(loginPage.isLoginPageDisplayed());

        fillLoginForm(loginPage);
        assertSuccessfulLogin(loginPage);
    }

    @Test
    @DisplayName("Вход в аккаунт через Страницу регистрации")
    public void loginTestFromRegistrationPage() {
        LoginPage loginPageBefore = mainPage.clickOnPersonalAccountButton();
        assertTrue(loginPageBefore.isLoginPageDisplayed());

        RegistrationPage registrationPage = loginPageBefore.clickOnRegistrationLink();
        assertTrue(registrationPage.isRegistrationPageDisplayed());

        LoginPage loginPage = registrationPage.clickOnAuthLink();
        assertTrue(loginPage.isLoginPageDisplayed());

        fillLoginForm(loginPage);
        assertSuccessfulLogin(loginPage);
    }

    @Test
    @DisplayName("Вход в аккаунт через страницу восстановления пароля")
    public void loginTestFromRecoveryPasswordPage() {
        LoginPage loginPageBefore = mainPage.clickOnPersonalAccountButton();
        assertTrue(loginPageBefore.isLoginPageDisplayed());

        RecoveryPasswordPage recoveryPasswordPage = loginPageBefore.clickOnRecoveryPasswordLink();
        assertTrue(recoveryPasswordPage.isRecoveryPasswordPageDisplayed());

        LoginPage loginPage = recoveryPasswordPage.clickOnAuthLink();
        assertTrue(loginPage.isLoginPageDisplayed()); // Исправлено: было loginPageBefore

        fillLoginForm(loginPage);
        assertSuccessfulLogin(loginPage);
    }
}
