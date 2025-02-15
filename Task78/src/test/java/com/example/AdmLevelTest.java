package com.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import core.ChromeBase;
import config.ConfigReader;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdmLevelTest extends ChromeBase {

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
        driver.get(url); // Открываем страницу личного кабинета

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
    public void testFindAdmissionLevel() {
        try {
            // Получаем URL и уровень из конфигурации
            String url = ConfigReader.getProperty("url");
            String level = ConfigReader.getProperty("level");

            // Открыть сайт по URL
            driver.get(url);

            // Нажать на ссылку "Абитуриенту"
            WebElement admissionLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Абитуриенту")));
            admissionLink.click();
            incrementClickCount();  // Увеличиваем счетчик кликов

            // Найти ссылку по уровню образования и кликнуть
            WebElement levelLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Прием в " + level)));
            levelLink.click();
            incrementClickCount();  // Увеличиваем счетчик кликов

            // Проверить, что загрузилась страница соответствующего уровня образования
            WebElement scheduleResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'breadcrumbs')]")));
            assertTrue(scheduleResult.isDisplayed(), "Не удалось перейти на страницу уровня: " + level);
            System.out.println("Тест успешно завершен. Перешли на страницу уровня " + level);

        } catch (Exception e) {
            markTestAsFailed("Ошибка при поиске уровня: " + e.getMessage());
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
