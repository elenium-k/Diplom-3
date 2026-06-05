import api.User;
import api.UserClient;
import api.UserGenerator;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import page_objects.*;
import utils.ApiConfig; // Импортируем ApiConfig

import static org.junit.Assert.assertTrue;

public class LoginTest {
    @Rule
    public FactoryDriver factoryDriver = new FactoryDriver();

    private WebDriver driver;
    private MainPage mainPage;
    private User testUser;
    private UserClient userClient;

    @BeforeClass // Добавляем BeforeClass для настройки API
    public static void setUpApi() {
        ApiConfig.setupBaseUrl(); // Вызываем настройку baseURI из ApiConfig
    }

    @Before
    public void setUp() {
        userClient = new UserClient();
        testUser = createRandomUser();
        driver = factoryDriver.getDriver();
        mainPage = new MainPage(driver);
        mainPage.openPage();
    }

    @Step("Создание тестового пользователя через API")
    private User createRandomUser() {
        User randomUser = UserGenerator.testUser();
        Response response = userClient.register(randomUser);

        if (response.statusCode() != 201 && response.statusCode() != 200) {
            throw new RuntimeException(
                    "Не удалось создать тестового пользователя. Статус: " +
                            response.statusCode() + ", ответ: " + response.asString()
            );
        }

        boolean success = response.jsonPath().getBoolean("success");
        if (!success) {
            throw new RuntimeException(
                    "Создание пользователя завершилось ошибкой (success=false). Ответ: " +
                            response.asString()
            );
        }

        System.out.println("Создан тестовый пользователь с email: " + randomUser.getEmail());
        return randomUser;
    }

    @After
    public void tearDown() {
        if (testUser != null && testUser.getEmail() != null) {
            try {
                userClient.deleteUserByEmail(testUser.getEmail());
                System.out.println("Удален тестовый пользователь: " + testUser.getEmail());
            } catch (Exception e) {
                System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
            }
        }

        if (driver != null) {
            driver.quit();
        }
    }

    private void fillLoginForm(LoginPage loginPage) {
        loginPage.clickOnEmailLoginField();
        loginPage.typeInEmailLogin(testUser.getEmail());
        loginPage.clickOnPasswordLoginField();
        loginPage.typeInPasswordLogin(testUser.getPassword());
    }

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
        assertTrue(loginPage.isLoginPageDisplayed());
        fillLoginForm(loginPage);
        assertSuccessfulLogin(loginPage);
    }
}
