
import io.qameta.allure.junit4.DisplayName;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import page.objects.MainPage;
import static org.junit.Assert.assertTrue;


public class BurgerConstructorTest {

    @Rule
    public FactoryDriver factoryDriver = new FactoryDriver();


    @Test
    @DisplayName("Отображение секции соусов")
    public void sauceSectionTest() {
        WebDriver driver = factoryDriver.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();

        mainPage.clickOnSauceButton();
        assertTrue(mainPage.isSauceSectionDisplayed());
    }

@Test
@DisplayName("Отображение секции булочек")
public void bunsSectionTest() {
    WebDriver driver = factoryDriver.getDriver();
    MainPage mainPage = new MainPage(driver);
    mainPage.openPage();

    mainPage.clickOnSauceButton();
    mainPage.clickOnBunsButton();
    assertTrue(mainPage.isBunsSectionDisplayed());
}

    @Test
    @DisplayName("Отображение секции ингредиентов")
    public void ingredientSectionTest() {
        WebDriver driver = factoryDriver.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();

        mainPage.clickOnIngredientButton();
        assertTrue(mainPage.isIngredientSectionDisplayed());
    }
}