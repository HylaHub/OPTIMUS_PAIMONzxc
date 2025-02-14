package com.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BGTUScheduleTest {
    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:/selenium/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(7));
    }

    @Test
    public void testFindTeacherSchedule() {
        driver.get("https://www.bstu.ru"); // Изначальный URL

        // переход на страницу схемы кампуса
        WebElement abit = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Схема кампуса")));
        abit.click(); 
    
        // Проверить схему
        WebElement scheduleResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'outer-wrap')]")));
        assertTrue(scheduleResult.isDisplayed(), "не то");
        System.out.println("Тест успешно завершен. Схема кампуса найдена.");

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
