import api.User;
import api.UserClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import page_objects.LoginPage;
import page_objects.MainPage;
import page_objects.RegistrationPage;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class FailedRegistrationTest {

    private String name;
    private String email;
    private String password;
    private WebDriver driver;
    private User testUser;

    @Rule
    public FactoryDriver factoryDriver = new FactoryDriver();

    public FailedRegistrationTest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"MariaBurgerEater", generateUniqueEmail("maria"), "12345"}
        });
    }

    private static String generateUniqueEmail(String baseName) {
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return baseName + "_" + uuid + "@test.com";
    }

    @Test
    @DisplayName("Неудачная регистрация")
    public void failedRegistrationTest() {
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
        registrationPage.clickOnRegistrateNewUserButton();

        // Сохраняем данные пользователя для возможного удаления в tearDown
        testUser = new User(email, password, name);

        assertTrue(registrationPage.isRegistrationPageDisplayed());
        assertTrue(registrationPage.isWrongPasswordMessageDisplayed());
    }

    @After
    public void tearDown() {
        if (testUser != null && testUser.getEmail() != null) {
            try {
                UserClient userClient = new UserClient();
                userClient.deleteUserByEmail(testUser.getEmail());
                System.out.println("Удален тестовый пользователь: " + testUser.getEmail());
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