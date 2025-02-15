package com.example;

import core.ChromeBase;
import models.User;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import config.ConfigReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends ChromeBase {
    private WebDriver driver;
    private WebDriverWait wait;

    private long startTime;
    private int clickCount;
    private int inputCount;

    // Статический список для хранения результатов
    private static List<String> testResults = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        String url = ConfigReader.getProperty("url");
        driver = new ChromeDriver(); // Инициализация драйвера
        wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Инициализация WebDriverWait
        driver.get(url); // URL личного кабинета

        // Инициализация метрик
        startTime = System.currentTimeMillis();
        clickCount = 0;
        inputCount = 0;
    }

    private void incrementClickCount() {
        clickCount++;
    }

    private void incrementInputCount() {
        inputCount++;
    }

    @Test
    public void testSuccessfulLogin() {
        User user = new User(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"));

        // Вводим логин и пароль
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.input")));
        emailField.sendKeys(user.getUsername());
        incrementInputCount();  // Считаем ввод данных

        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.input[type='password']")));
        passwordField.sendKeys(user.getPassword());
        incrementInputCount();  // Считаем ввод данных

        // Соглашаемся с обработкой персональных данных
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label.checkbox-item.login_policy .checkbox-item__checkmark")));
        checkbox.click();
        incrementClickCount();  // Считаем клик
        user.giveConsent(); // Даем согласие на обработку данных

        // Нажимаем "Войти"
        WebElement login = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn_controls.btn_blue.w100")));
        login.click();
        incrementClickCount();  // Считаем клик

        // Проверка успешного входа
        WebElement accountPage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.home__events-wrap")));
        assertTrue(accountPage.isDisplayed(), "Не удалось войти в личный кабинет");

        // Проверяем, что пользователь авторизован
        assertTrue(user.isAuthenticated(), "Пользователь не авторизован");

        // Сохраняем результаты
        long duration = System.currentTimeMillis() - startTime;
        testResults.add("Успешная авторизация  - " + duration + "ms, " + clickCount + " кликов, " + inputCount + " вводов");
    }

    @Test
    public void testInvalidLogin() {
        User user = new User(ConfigReader.getProperty("wrongUser"), ConfigReader.getProperty("password"));

        // Вводим логин
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.input")));
        usernameField.sendKeys(user.getUsername());
        incrementInputCount();  // Считаем ввод данных

        // Вводим пароль
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.input[type='password']")));
        passwordField.sendKeys(user.getPassword());
        incrementInputCount();  // Считаем ввод данных

        // Соглашаемся с обработкой персональных данных
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label.checkbox-item.login_policy .checkbox-item__checkmark")));
        checkbox.click();
        incrementClickCount();  // Считаем клик
        user.giveConsent(); // Даем согласие на обработку данных

        // Нажимаем "Войти"
        WebElement login = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn_controls.btn_blue.w100")));
        login.click();
        incrementClickCount();  // Считаем клик

        // Проверка сообщения об ошибке
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.login__error")));
        assertTrue(errorMessage.isDisplayed(), "Сообщение об ошибке не отображается");

        // Сохраняем результаты
        long duration = System.currentTimeMillis() - startTime;
        testResults.add("Неправильный логин    - " + duration + "ms, " + clickCount + " кликов, " + inputCount + " вводов");
    }

    @Test
    public void testInvalidPassword() {
        User user = new User(ConfigReader.getProperty("username"), ConfigReader.getProperty("wrongPassword"));

        // Вводим логин
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.input")));
        usernameField.sendKeys(user.getUsername());
        incrementInputCount();  // Считаем ввод данных

        // Вводим неверный пароль
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.input[type='password']")));
        passwordField.sendKeys(user.getPassword());
        incrementInputCount();  // Считаем ввод данных

        // Соглашаемся с обработкой персональных данных
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label.checkbox-item.login_policy .checkbox-item__checkmark")));
        checkbox.click();
        incrementClickCount();  // Считаем клик
        user.giveConsent(); // Даем согласие на обработку данных

        // Нажимаем "Войти"
        WebElement login = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn_controls.btn_blue.w100")));
        login.click();
        incrementClickCount();  // Считаем клик

        // Проверка сообщения об ошибке
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.login__error")));
        assertTrue(errorMessage.isDisplayed(), "Сообщение об ошибке не отображается");

        // Сохраняем результаты
        long duration = System.currentTimeMillis() - startTime;
        testResults.add("Неправильный пароль   - " + duration + "ms, " + clickCount + " кликов, " + inputCount + " вводов");
    }

    @Test
    public void testEmptyFields() {
        User user = new User("", "");

        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.input")));
        usernameField.clear();
        incrementInputCount();  // Считаем ввод данных

        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.input[type='password']")));
        passwordField.clear();
        incrementInputCount();  // Считаем ввод данных

        // Нажимаем "Войти"
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label.checkbox-item.login_policy .checkbox-item__checkmark")));
        checkbox.click();
        incrementClickCount();  // Считаем клик
        user.giveConsent(); // Даем согласие на обработку данных

        WebElement login = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn_controls.btn_blue.w100")));
        login.click();
        incrementClickCount();  // Считаем клик

        // Проверка ошибки
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.login__error")));
        assertTrue(errorMessage.isDisplayed(), "Сообщение об ошибке не отображается для пустых полей");

        // Сохраняем результаты
        long duration = System.currentTimeMillis() - startTime;
        testResults.add("Пустые поля           - " + duration + "ms, " + clickCount + " кликов, " + inputCount + " вводов");
    }

    @AfterAll
    public static void printTestResults() {
        // Выводим все результаты после всех тестов
        System.out.println("\nTest Results:");
        for (String result : testResults) {
            System.out.println(result);
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Закрытие браузера после теста
        }
    }
}
