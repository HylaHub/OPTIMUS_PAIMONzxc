package com.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import config.ConfigReader;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BGTUSearchTest {
    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:/selenium/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test
    public void testSearch() {
        // Получаем URL и запрос для поиска из конфигурации
        String url = ConfigReader.getProperty("url");
        String search = ConfigReader.getProperty("search");

        driver.get(url); // Открываем URL из конфигурации

        // Найти поле ввода поисковика
        WebElement searchField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.ya-site-form__input-text")));
        searchField.sendKeys(search); // Ввести запрос

        // Найти кнопку поиска
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("td.ya-site-form__search-input-layout-r")));
        searchButton.click(); 

        // Проверить, что поиск дал результаты 
        WebElement resultArea = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".ya-site-suggest-items")));
        assertTrue(resultArea.isDisplayed(), "Результаты поиска не отображаются");

        System.out.println("Тест успешно завершен. Результаты поиска отображаются.");

        try {
            // Задержка перед закрытием браузера
            Thread.sleep(5000);  // 5 секунд
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
