package core;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class ChromeBase {

    protected static WebDriver driver; 
    protected static WebDriverWait wait; 

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:/selenium/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // Для явных ожиданий
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit(); // Закрытие браузера после каждого теста
        }
    }
}
