package com.example;

import core.ChromeBase;
import models.Groups;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import config.ConfigReader;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GroupScheduleTest extends ChromeBase {
    private WebDriver driver;
    private WebDriverWait wait;
    private Groups groups;

    private static long totalTestTime; // Статическое поле для хранения времени выполнения тестов
    private static int clickCount;
    private static int inputCount;
    private static boolean testStatus;
    private static List<String> testResults = new ArrayList<>();
    private static List<String> errors = new ArrayList<>();

    private static int totalTests = 0;
    private static int passedTests = 0;
    private long startTime; // Время начала теста

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
        driver = new ChromeDriver(); // Инициализация драйвера
        wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Инициализация WebDriverWait
        driver.get(url); // Открываем страницу личного кабинета

        // Инициализация метрик
        testStatus = true;

        // Создаем объект Groups и добавляем группы из конфига
        groups = new Groups();
        groups.addGroup(ConfigReader.getProperty("group1"));
        groups.addGroup(ConfigReader.getProperty("group2"));
        groups.addGroup(ConfigReader.getProperty("group3"));
        groups.addGroup(ConfigReader.getProperty("group4"));

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

    // Метод для выполнения поиска расписания
    private void searchGroupSchedule(String groupName) {
        try {
            // Находим поле поиска и вводим название группы
            WebElement searchBox = driver.findElement(By.xpath("//input[contains(@class, 'input search__input _search_input')]"));
            searchBox.sendKeys(groupName);
            incrementInputCount();  // Считаем ввод данных

            // Явное ожидание появления выпадающего списка с результатами
            WebDriverWait waitForList = new WebDriverWait(driver, Duration.ofSeconds(5));
            waitForList.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@class, 'input search__input _search_input')]")));

            // Используем явное ожидание для элементов внутри выпадающего списка
            WebDriverWait waitForGroup = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement groupElement = waitForGroup.until(ExpectedConditions.elementToBeClickable(By.linkText(groupName)));
            groupElement.click();
            incrementClickCount();  // Считаем клик

            // Проверка, что загружается страница с расписанием
            WebElement schedule = driver.findElement(By.xpath("//h1[contains(text(), '" + groupName + "')]"));
            assertTrue(schedule.isDisplayed(), "Расписание для группы " + groupName + " не найдено!");
        } catch (Exception e) {
            markTestAsFailed("Ошибка при поиске расписания для группы " + groupName + ": " + e.getMessage());
        }
    }

    @Test
    public void testFindGroupSchedule1() {
        String groupName = groups.getGroupByIndex(0); // Получаем первую группу из списка
        assertNotNull(groupName, "Группа не найдена!");
        searchGroupSchedule(groupName);
    }

    @Test
    public void testFindGroupSchedule2() {
        String groupName = groups.getGroupByIndex(1); // Получаем вторую группу из списка
        assertNotNull(groupName, "Группа не найдена!");
        searchGroupSchedule(groupName);
    }

    @Test
    public void testFindGroupSchedule3() {
        String groupName = groups.getGroupByIndex(2); // Получаем третью группу из списка
        assertNotNull(groupName, "Группа не найдена!");
        searchGroupSchedule(groupName);
    }

    @Test
    public void testFindGroupSchedule4() {
        String groupName = groups.getGroupByIndex(3); // Получаем четвертую группу из списка
        assertNotNull(groupName, "Группа не найдена!");
        searchGroupSchedule(groupName);
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
