
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import page_objects.MainPage;
import static org.junit.Assert.assertTrue;


public class BurgerConstructorTest {

    @Rule
    public FactoryDriver factoryDriver = new FactoryDriver();



    @Test
    @DisplayName("Отображение конструктора бургеров")
    public void burgerConstructorTest() {
        WebDriver driver = factoryDriver.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();

        mainPage.clickOnSauceButton();
        assertTrue(mainPage.isSauceSectionDisplayed());

        mainPage.clickOnBunsButton();
        assertTrue(mainPage.isBunsSectionDisplayed());

        mainPage.clickOnIngredientButton();
        assertTrue(mainPage.isIngredientSectionDisplayed());
    }
}
