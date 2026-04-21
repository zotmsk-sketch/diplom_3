package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserApiClient {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru";
    private String accessToken;

    // Генерация случайных данных пользователя
    public static Map<String, String> generateRandomUser() {
        String randomSuffix = UUID.randomUUID().toString().substring(0, 8);
        Map<String, String> user = new HashMap<>();
        user.put("email", "user_" + randomSuffix + "@test.ru");
        user.put("password", "123456");
        user.put("name", "Тест_" + randomSuffix);
        return user;
    }

    // Регистрация пользователя через API
    public Response registerUser(Map<String, String> user) {
        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .body(user)
                .post(BASE_URL + "/api/auth/register");
        if (response.statusCode() == 200) {
            accessToken = response.jsonPath().getString("accessToken");
        }
        return response;
    }

    // Логин пользователя через API (получаем токен)
    public Response loginUser(String email, String password) {
        Map<String, String> creds = new HashMap<>();
        creds.put("email", email);
        creds.put("password", password);
        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .body(creds)
                .post(BASE_URL + "/api/auth/login");
        if (response.statusCode() == 200) {
            accessToken = response.jsonPath().getString("accessToken");
        }
        return response;
    }

    // Удаление пользователя (требуется токен)
    public Response deleteUser() {
        if (accessToken == null) {
            return null;
        }
        return RestAssured.given()
                .header("Authorization", accessToken)
                .delete(BASE_URL + "/api/auth/user");
    }

    // Удобный метод: создать пользователя и сохранить токен
    public Map<String, String> createUniqueUser() {
        Map<String, String> user = generateRandomUser();
        registerUser(user);
        return user;
    }

    // Удалить пользователя по email и паролю (сначала логинимся, потом удаляем)
    public void deleteUserByCredentials(String email, String password) {
        loginUser(email, password);
        deleteUser();
    }
}