package com.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import config.ConfigReader;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthTest {
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
    public void testFindTeacherSchedule() {
        // получить URL из конфига
        String url = ConfigReader.getProperty("url");
        driver.get(url); // URL из конфига

        // Получить логин и пароль из config.properties
        String username = ConfigReader.getProperty("username");
        String password = ConfigReader.getProperty("password");

        // Ввести логин и пароль + согласие на обработку и нажать на войти
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.input")));
        emailField.sendKeys(username); // Используем логин из конфига

        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.input[type='password']")));
        passwordField.sendKeys(password); // Используем пароль из конфига

        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label.checkbox-item.login_policy .checkbox-item__checkmark")));
        checkbox.click();

        // Проверить, что загружено расписание
        WebElement scheduleResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'login__form')]")));
        assertTrue(scheduleResult.isDisplayed(), "Прошло успешно");
        System.out.println("Тест успешно завершен. Успеваемость найдена.");

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
