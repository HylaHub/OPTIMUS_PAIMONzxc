package models;

public class User {
    private String username;
    private String password;

    // Инициализация логина и пароля
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Получить имя
    public String getUsername() {
        return username;
    }

    // Получить пароль
    public String getPassword() {
        return password;
    }

    // Метод для галочки на обработку персональных данных
    public void giveConsent() {
        // Simulate checkbox interaction or action
    }

    // Метод чекать успешность авторизовации
    public boolean isAuthenticated() {
        // Логика проверки авторизации
        return !username.isEmpty() && !password.isEmpty(); // Простая проверка
    }
}
