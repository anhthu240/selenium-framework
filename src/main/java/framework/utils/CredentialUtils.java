package framework.utils;

public class CredentialUtils {

    public static String getUsername() {
        String username = System.getenv("APP_USERNAME");
        if (username == null || username.isBlank()) {
            username = "standard_user";
        }
        return username;
    }

    public static String getPassword() {
        String password = System.getenv("APP_PASSWORD");
        if (password == null || password.isBlank()) {
            password = "secret_sauce";
        }
        return password;
    }
}