package com.example;

import core.ChromeBase;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import config.ConfigReader;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BGTUScheduleTest extends ChromeBase {
    // Алфавит в массиве
    private static final String[] alphabet = {"А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "К", "Л", "М", "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ч", "Ш", "Щ", "Ю", "Я"};

    @Test
    public void testFindTeacherSchedule() {
        // Получаем URL и фамилию преподавателя из конфигурации
        String url = ConfigReader.getProperty("url");
        String teacherName = ConfigReader.getProperty("teacher");

        // Убираем лишние пробелы и извлекаем первую букву фамилии
        String firstLetter = teacherName.trim().split(" ")[0].substring(0, 1); // Получаем первую букву фамилии

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
