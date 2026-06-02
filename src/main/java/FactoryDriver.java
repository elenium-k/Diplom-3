import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class FactoryDriver extends ExternalResource {

    private WebDriver driver;
    public WebDriver getDriver(){
        return driver;
    }

    public void initDriver() {
        if("yandex".equals(System.getProperty("browser"))) {
startYandex();
        } else startChrome(); }

    public WebDriver startYandex() {
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", "/Applications/Yandex.app/Contents/MacOS/Yandex");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        return driver;
    }

    public WebDriver startChrome() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        return driver;
    }


    @Override
    protected void before() {
        initDriver();
    }

    @Override
    protected void after() {
        driver.quit();
    }
}
