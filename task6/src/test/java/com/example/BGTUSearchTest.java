package com.example;

import core.ChromeBase;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import config.ConfigReader;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BGTUSearchTest extends ChromeBase {

    @Test
    public void testSearch() {
        // Получаем URL и запрос для поиска из конфигурации
        String url = ConfigReader.getProperty("url");
        String search = ConfigReader.getProperty("search");

        driver.get(url); // Открываем URL из конфигурации

        // Найти поле ввода поисковика
        WebElement searchField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.ya-site-form__input-text")));
        searchField.sendKeys(search); // Ввести запрос

        // Найти кнопку поиска
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("td.ya-site-form__search-input-layout-r")));
        searchButton.click(); 

        // Проверка результата
        WebElement resultArea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.wrap-content")));
        assertTrue(resultArea.isDisplayed(), "Результаты поиска не отображаются");

        System.out.println("Тест успешно завершен. Результаты поиска отображаются.");
        try {
            // Задержка перед закрытием браузера
            Thread.sleep(5000);  // 5 секунд
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
}
}
