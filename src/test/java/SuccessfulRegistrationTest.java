import api.User;
import api.UserClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import page.objects.LoginPage;
import page.objects.MainPage;
import page.objects.RegistrationPage;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class SuccessfulRegistrationTest {

    private String name;
    private String email;
    private String password;
    private WebDriver driver;
    private User testUser;

    @Rule
    public FactoryDriver factoryDriver = new FactoryDriver();

    public SuccessfulRegistrationTest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"BurgerEater", generateUniqueEmail("burgereater"), "StrongPass123"},
                {"LenaBurgerEater", generateUniqueEmail("lena"), "Pass123"}
        });
    }

    private static String generateUniqueEmail(String baseName) {
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return baseName + "_" + uuid + "@test.com";
    }

    @Test
    @DisplayName("Успешная регистрация пользователя")
    public void successfulRegistrationTest() {
        driver = factoryDriver.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        LoginPage loginPage = mainPage.clickOnPersonalAccountButton();

        assertTrue(loginPage.isLoginPageDisplayed());
        RegistrationPage registrationPage = loginPage.clickOnRegistrationLink();

        assertTrue(registrationPage.isRegistrationPageDisplayed());
        registrationPage.clickOnNameField();
        registrationPage.typeInName(name);

        registrationPage.clickOnEmailField();
        registrationPage.typeInEmail(email);

        registrationPage.clickOnPasswordField();
        registrationPage.typeInPassword(password);
        LoginPage loginPageForNewUser = registrationPage.clickOnRegistrateNewUserButton();

        // Сохраняем данные пользователя для удаления в tearDown
        testUser = new User(email, password, name);

        assertTrue(loginPageForNewUser.isLoginPageDisplayed());
    }

    @After
    public void tearDown() {
        // Удаляем тестового пользователя, если он был создан
        if (testUser != null && testUser.getEmail() != null) {
            try {
                UserClient userClient = new UserClient();
                userClient.deleteUserByEmail(testUser.getEmail());
            } catch (Exception e) {
                System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
            }
        }

        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Ошибка при закрытии драйвера: " + e.getMessage());
            }
        }
    }
}