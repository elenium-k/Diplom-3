import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
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
public class RegistrationTest {

    private String name;
    private String email;
    private String password;
    private boolean isSuccessful;

    @Rule
    public FactoryDriver factoryDriver = new FactoryDriver();

    public RegistrationTest(String name, String email, String password, boolean isSuccessful) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isSuccessful = isSuccessful;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                // Успешные регистрации
                {"BurgerEater", generateUniqueEmail("burgereater"), "StrongPass123", true},
                {"LenaBurgerEater", generateUniqueEmail("lena"), "Pass123", true},
                // Неуспешные регистрация
                {"MariaBurgerEater", generateUniqueEmail("maria"), "12345", false}
        });
    }

    // Вспомогательный метод для генерации уникального email
    private static String generateUniqueEmail(String baseName) {
        String uuid = UUID.randomUUID().toString().substring(0, 8); // Берём первые 8 символов UUID
        return baseName + "_" + uuid + "@test.com";
    }



    @Test
    @DisplayName("Регистрация пользователя")
    public void registrationTest() {
        WebDriver driver = factoryDriver.getDriver();
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

        if (isSuccessful) {
            //  проверяем переход на страницу входа
            assertTrue(loginPageForNewUser.isLoginPageDisplayed());
        } else {
            // проверяем, что остались на странице регистрации
            // и видим сообщение об ошибке
            assertTrue(registrationPage.isRegistrationPageDisplayed());
            assertTrue(registrationPage.isWrongPasswordMessageDisplayed());
        }
    }
}
