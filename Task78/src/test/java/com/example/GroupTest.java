package com.example;

import core.ChromeBase;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import config.ConfigReader;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupTest extends ChromeBase {

    private long startTime;
    private static long totalTestTime; // Статическое поле для хранения времени выполнения тестов
    private static int clickCount;
    private static int inputCount;
    private static boolean testStatus;
    private static List<String> testResults = new ArrayList<>();
    private static List<String> errors = new ArrayList<>();

    private static int totalTests = 0;
    private static int passedTests = 0;

    @BeforeAll
    public static void setupBeforeAll() {
        totalTestTime = 0;
        clickCount = 0;
        inputCount = 0;
    }

    @BeforeEach
    public void setUp() {
        startTime = System.currentTimeMillis(); // Запись времени начала теста
        String url = ConfigReader.getProperty("url");
        driver.get(url); // Открываем страницу

        // Инициализация метрик
        testStatus = true;
        totalTests++;
    }

    private void incrementClickCount() {
        clickCount++;
    }

    private void incrementInputCount() {
        inputCount++;
    }

    private void markTestAsFailed(String errorMessage) {
        testStatus = false;
        errors.add(errorMessage); // Добавляем ошибку в список
    }

    @Test
    public void testFindGroupSchedule() {
        try {
            String url = ConfigReader.getProperty("url");
            String group = ConfigReader.getProperty("group");

            // Открыть сайт по URL
            driver.get(url);

            // Найти поле поиска и ввести название группы
            WebElement searchBox = driver.findElement(By.xpath("//input[contains(@class, 'input search__input _search_input')]"));
            searchBox.sendKeys(group);
            incrementInputCount();  // Увеличиваем счетчик вводов

            // Найти группу в списке и кликнуть
            WebElement groupElement = driver.findElement(By.partialLinkText(group));
            groupElement.click();
            incrementClickCount();  // Увеличиваем счетчик кликов

            // Проверяем, что загружается страница с расписанием
            WebElement schedule = driver.findElement(By.xpath("//h1[contains(text(), '" + group + "')]"));
            assertTrue(schedule.isDisplayed(), "Расписание не найдено!");

        } catch (Exception e) {
            markTestAsFailed("Ошибка при поиске расписания для группы: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        long duration = System.currentTimeMillis() - startTime; // Время выполнения текущего теста

        // Сохраняем результаты теста
        String testResult = String.format("Тест завершен за %dms, %d кликов, %d вводов, Статус: %s.",
                duration, clickCount, inputCount, testStatus ? "Успешно" : "Неудачно");
        testResults.add(testResult);

        if (testStatus) {
            passedTests++;
        }

        totalTestTime += duration;

        if (driver != null) {
            driver.quit(); // Закрытие браузера после теста
        }
    }

    @AfterAll
    public static void printTestResults() {
        // Подсчитываем процент покрытия тестами
        double coverage = (totalTests > 0) ? (double) passedTests / totalTests * 100 : 0;

        // Выводим итоговый отчет
        System.out.println("\nИтоговый отчет:");
        System.out.println("----------------------------------");
        System.out.println("Количество тестов: " + totalTests);
        System.out.println("Пройдено тестов: " + passedTests);
        System.out.println("Покрытие тестами: " + String.format("%.2f", coverage) + "%");
        System.out.println("Время выполнения тестов: " + totalTestTime + " ms");

        if (!errors.isEmpty()) {
            System.out.println("\nОшибки:");
            for (String error : errors) {
                System.out.println(error);
            }
        } else {
            System.out.println("\nОшибок не найдено.");
        }

        System.out.println("\nРезультаты выполнения тестов:");
        for (String result : testResults) {
            System.out.println(result);
        }
    }
}
