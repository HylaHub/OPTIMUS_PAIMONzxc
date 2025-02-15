package com.example;

import core.ChromeBase;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import config.ConfigReader;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupTest extends ChromeBase {

    @Test
    public void testFindGroupSchedule() {
        String url = ConfigReader.getProperty("url");
        String group = ConfigReader.getProperty("group");

        driver.get(url);

        // Найти поле поиска и ввести название группы
        WebElement searchBox = driver.findElement(By.xpath("//input[contains(@class, 'input search__input _search_input')]"));
        searchBox.sendKeys(group);

        // Найти группу в списке и кликнуть
        WebElement groupElement = driver.findElement(By.partialLinkText(group));
        groupElement.click();

        // Проверяем, что загрузилась страница с расписанием
        WebElement schedule = driver.findElement(By.xpath("//h1[contains(text(), '" + group + "')]"));
        assertTrue(schedule.isDisplayed(), "Расписание не найдено!");

        try {
            // Задержка перед закрытием браузера
            Thread.sleep(5000);  // 5 секунд
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
