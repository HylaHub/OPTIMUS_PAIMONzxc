package com.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import config.ConfigReader;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BGTUScheduleTest {
    private static WebDriver driver;
    private static WebDriverWait wait;

    // Алфавит в массиве
    private static final String[] alphabet = {"А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "К", "Л", "М", "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ч", "Ш", "Щ", "Ю", "Я"};

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
        // Получаем URL и фамилию преподавателя из конфигурации
        String url = ConfigReader.getProperty("url");
        String teacherName = ConfigReader.getProperty("teacher");

        // Разделяем фамилию и имя для поиска по первой букве фамилии
        String firstLetter = teacherName.split(" ")[0].substring(0, 1); // Получаем первую букву фамилии

        // Находим индекс первой буквы фамилии в алфавите
        int letterIndex = getLetterIndex(firstLetter);

        driver.get(url); // Открываем URL из конфигурации

        // Нажать «Расписание преподавателей»
        WebElement teacherScheduleLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("преподавател")));
        teacherScheduleLink.click();

        // Найти нужную букву для фильтра по индексу
        WebElement filter = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.filter__item[data-index='" + letterIndex + "']")));
        filter.click();

        // Найти преподавателя по фамилии
        WebElement teacher = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(teacherName)));
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

    // Метод для нахождения индекса буквы
    private int getLetterIndex(String letter) {
        // Приводим к верхнему регистру, чтобы учесть только большую букву
        letter = letter.toUpperCase();

        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i].equals(letter)) {
                return i; // Возвращаем индекс буквы в алфавите
            }
        }
        throw new IllegalArgumentException("Неизвестная буква: " + letter);
    }
}
