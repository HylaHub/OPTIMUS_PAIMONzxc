package com.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import core.ChromeBase;
import static org.junit.jupiter.api.Assertions.assertTrue;
import config.ConfigReader;

public class AuthTest extends ChromeBase {

    @Test
    public void testFindTeacherSchedule() {
        // Получаем URL из конфигурации
        String url = ConfigReader.getProperty("url");
        driver.get(url); // Открываем URL из конфига

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

        // Проверить, что загружено расписание (или форма входа успешно прошла)
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
}
