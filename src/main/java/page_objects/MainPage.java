package page_objects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {

    private final By personalAccountButton = By.xpath("//p[contains(text(), 'Личный Кабинет')]");
    private final By enterAccountButton = By.cssSelector("[class='button_button__33qZ0 button_button_type_primary__1O7Bx button_button_size_large__G21Vg']");
    private final By bunsButton = By.cssSelector("[class='text text_type_main-default']");
    private final By sauceButton = By.cssSelector("[class='tab_tab__1SPyG  pt-4 pr-10 pb-4 pl-10 noselect']");
    private final By ingredientButton = By.cssSelector("[class='tab_tab__1SPyG  pt-4 pr-10 pb-4 pl-10 noselect']");
    private final By bunSection = By.cssSelector("[class='BurgerIngredient_ingredient__image__3e-07 ml-4 mr-4'][alt='Флюоресцентная булка R2-D3']");
    private final By sauceSection = By.cssSelector("[class='BurgerIngredient_ingredient__image__3e-07 ml-4 mr-4'][alt='Соус Spicy-X']");
    private final By ingredientSection = By.cssSelector("[class='BurgerIngredient_ingredient__image__3e-07 ml-4 mr-4'][alt='Мясо бессмертных моллюсков Protostomia']");

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открытие главной страницы")
    public void openPage() {
        driver.get("https://stellarburgers.education-services.ru/");
    }

    @Step("Клик по кнопке «Личный Кабинет»")
    public LoginPage clickOnPersonalAccountButton() {
        driver.findElement(personalAccountButton).click();
        return new LoginPage(driver);
    }

    @Step("Клик по кнопке входа в аккаунт")
    public LoginPage clickOnEnterAccountButton() {
        driver.findElement(enterAccountButton).click();
        return new LoginPage(driver);
    }

    @Step("Клик по вкладке «Булки»")
    public void clickOnBunsButton() {
        driver.findElement(bunsButton).click();
    }

    @Step("Клик по вкладке «Соусы»")
    public void clickOnSauceButton() {
        driver.findElement(sauceButton).click();
    }

    @Step("Клик по вкладке «Начинки»")
    public void clickOnIngredientButton() {
        driver.findElement(ingredientButton).click();
    }

    @Step("Проверка отображения раздела «Булки»")
    public boolean isBunsSectionDisplayed() {
        driver.findElement(bunSection).isDisplayed();
        return true;
    }

    @Step("Проверка отображения раздела «Соусы»")
    public boolean isSauceSectionDisplayed() {
        driver.findElement(sauceSection).isDisplayed();
        return true;
    }

    @Step("Проверка отображения раздела «Начинки»")
    public boolean isIngredientSectionDisplayed() {
        driver.findElement(ingredientSection).isDisplayed();
        return true;
    }
}