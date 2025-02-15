package com.example;

import core.ChromeBase;
import models.SearchQuery;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import config.ConfigReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SearchTest extends ChromeBase {

    private WebDriver driver;
    private WebDriverWait wait;
    private SearchQuery searchQuery;

    private long startTime;
    private int clickCount;
    private int inputCount;

    // Статические метрики
    private static long totalTestTime; // Общее время выполнения всех тестов
    private static int totalTests; // Общее количество тестов
    private static int passedTests; // Количество пройденных тестов
    private static List<String> testResults = new ArrayList<>(); // Результаты выполнения тестов
    private static List<String> errors = new ArrayList<>(); // Список ошибок

    @BeforeEach
    public void setUp() {
        String url = ConfigReader.getProperty("url");
        driver = new ChromeDriver(); // Инициализация драйвера
        wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Инициализация WebDriverWait
        driver.get(url); // Открываем страницу личного кабинета

        // Инициализация метрик
        startTime = System.currentTimeMillis();
        clickCount = 0;
        inputCount = 0;

        // Создаем объект SearchQuery и добавляем запросы из конфига
        searchQuery = new SearchQuery();
        searchQuery.addQuery(ConfigReader.getProperty("search1"));
        searchQuery.addQuery(ConfigReader.getProperty("search2"));
        searchQuery.addQuery(ConfigReader.getProperty("search3"));
        searchQuery.addQuery(ConfigReader.getProperty("search4"));

        totalTests++; // Увеличиваем количество тестов
    }

    private void incrementClickCount() {
        clickCount++;
    }

    private void incrementInputCount() {
        inputCount++;
    }

    @Test
    public void testSearch1() {
        String query = searchQuery.getQueryByIndex(0); // Получаем первый поисковый запрос
        assertNotNull(query, "Запрос не найден!");
        performSearch(query);
    }

    @Test
    public void testSearch2() {
        String query = searchQuery.getQueryByIndex(1); // Получаем второй поисковый запрос
        assertNotNull(query, "Запрос не найден!");
        performSearch(query);
    }

    @Test
    public void testSearch3() {
        String query = searchQuery.getQueryByIndex(2); // Получаем третий поисковый запрос
        assertNotNull(query, "Запрос не найден!");
        performSearch(query);
    }

    @Test
    public void testSearch4() {
        String query = searchQuery.getQueryByIndex(3); // Получаем четвертый поисковый запрос
        assertNotNull(query, "Запрос не найден!");
        performSearch(query);
    }

    private void performSearch(String query) {
        // Находим поле ввода поисковика
        WebElement searchField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.ya-site-form__input-text")));
        searchField.sendKeys(query); // Ввести запрос
        incrementInputCount();  // Считаем ввод данных

        // Найти кнопку поиска
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("td.ya-site-form__search-input-layout-r")));
        searchButton.click();
        incrementClickCount();  // Считаем клик

        // Проверка результата
        WebElement resultArea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.wrap-content")));
        assertTrue(resultArea.isDisplayed(), "Результаты поиска не отображаются");

        // Завершаем тест и сохраняем метрики
        long duration = System.currentTimeMillis() - startTime;
        testResults.add("Поиск с запросом \"" + query + "\" - " + duration + "ms, " + clickCount + " кликов, " + inputCount + " вводов");

        // Увеличиваем счетчик пройденных тестов
        passedTests++;
        totalTestTime += duration;
    }

    @AfterEach
    public void tearDown() {
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
