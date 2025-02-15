package models;

import java.util.ArrayList;
import java.util.List;

public class SearchQuery {

    private List<String> searchQueries;

    // Конструктор
    public SearchQuery() {
        searchQueries = new ArrayList<>();
    }

    // Метод для добавления поискового запроса
    public void addQuery(String query) {
        searchQueries.add(query);
    }

    // Метод для получения поискового запроса по индексу
    public String getQueryByIndex(int index) {
        if (index >= 0 && index < searchQueries.size()) {
            return searchQueries.get(index);
        }
        return null; // Возвращает null, если индекс выходит за пределы
    }

    // Метод для получения всех поисковых запросов
    public List<String> getAllQueries() {
        return searchQueries;
    }

    // Метод для получения размера списка запросов
    public int size() {
        return searchQueries.size();
    }
}
