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

        // Нажать на ярлычок «Личный кабинет»
        WebElement scheduleLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.lp-bottom.pngfix")));
        scheduleLink.click();

        // Нажать «Расписание 1..4 курсов преподов и студентов»
        WebElement teacherScheduleLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("1, 2, 3")));
        teacherScheduleLink.click();

        // Ввести логин и пароль + согласие на обработку и нажать на войти
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.input")));
        emailField.sendKeys("Test@email.com");

        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.input[type='password']")));
        passwordField.sendKeys("pasw123");

        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label.checkbox-item.login_policy .checkbox-item__checkmark")));
        checkbox.click();

        WebElement login = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn_controls.btn_blue.w100")));
        login.click();

        // Зайти в преподаватели
        WebElement teacher = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Преподаватели")));
        teacher.click();

        // Проверить, что загружен нужный сайт 
        WebElement scheduleResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'data-block__label text-label')]")));
        assertTrue(scheduleResult.isDisplayed(), "не то");
        System.out.println("Тест успешно завершен. Список преподавателей найден.");

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
