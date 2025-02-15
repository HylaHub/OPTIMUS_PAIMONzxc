package models;

public class User {
    private String username;
    private String password;

    // Constructor to initialize username and password
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Method to simulate consent (checkbox interaction)
    public void giveConsent() {
        // Simulate checkbox interaction or action
    }

    // Method to check if the user is authenticated
    public boolean isAuthenticated() {
        // Placeholder logic for checking if user is authenticated
        return !username.isEmpty() && !password.isEmpty(); // Simple check (can be expanded)
    }
}
