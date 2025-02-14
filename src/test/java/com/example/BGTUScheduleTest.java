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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test
    public void testFindTeacherSchedule() {
        driver.get("https://www.bstu.ru"); // Изначальный URL

        // Нажать на «Расписание»
        WebElement scheduleLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Учебное расписание")));
        scheduleLink.click();

        // Нажать «Расписание преподавателей»
        WebElement teacherScheduleLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("преподавател")));
        teacherScheduleLink.click();

        // Найти "Я"
        WebElement filter = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.filter__item[data-index='25']")));
        filter.click();

        // Найти Ястребова А.В.
        WebElement teacher = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Ястребов А. В.")));
        teacher.click();

        // Проверить, что загружено расписание
        WebElement scheduleResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, '_timetable_page offset')]")));
        assertTrue(scheduleResult.isDisplayed(), "Расписание найдено");
        System.out.println("Тест успешно завершен. Расписание найдено.");

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
