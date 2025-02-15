package com.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import core.ChromeBase;
import config.ConfigReader;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BGTUScheduleTest extends ChromeBase {

    @Test
    public void testFindAdmissionLevel() {
        // Получаем URL и уровень из конфигурации
        String url = ConfigReader.getProperty("url");
        String level = ConfigReader.getProperty("level");

        // Открыть сайт по URL
        driver.get(url);

        // Нажать на ссылку "Абитуриенту"
        WebElement admissionLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Абитуриенту")));
        admissionLink.click();

        // Найти ссылку по уровню образования и кликнуть
        WebElement levelLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Прием в " + level)));
        levelLink.click();

        // Проверить, что загрузилась страница соответствующего уровня образования
        WebElement scheduleResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'breadcrumbs')]")));
        assertTrue(scheduleResult.isDisplayed(), "Не удалось перейти на страницу уровня: " + level);
        System.out.println("Тест успешно завершен. Перешли на страницу уровня " + level);

        try {
            // Задержка перед закрытием браузера
            Thread.sleep(5000);  // 5 секунд
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

