package api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.UUID;

public class UserApiClient {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru";
    private String accessToken;

    @Step("Генерация случайного пользователя")
    public static User generateRandomUser() {
        String randomSuffix = UUID.randomUUID().toString().substring(0, 8);
        return new User(
                "user_" + randomSuffix + "@test.ru",
                "123456",
                "Тест_" + randomSuffix
        );
    }

    @Step("Регистрация пользователя через API")
    public Response registerUser(User user) {
        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .body(user)
                .post(BASE_URL + "/api/auth/register");
        if (response.statusCode() == 200) {
            accessToken = response.jsonPath().getString("accessToken");
        }
        return response;
    }

    @Step("Логин пользователя через API")
    public Response loginUser(User user) {
        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .body(user)
                .post(BASE_URL + "/api/auth/login");
        if (response.statusCode() == 200) {
            accessToken = response.jsonPath().getString("accessToken");
        }
        return response;
    }

    @Step("Удаление пользователя")
    public Response deleteUser() {
        if (accessToken == null) return null;
        return RestAssured.given()
                .header("Authorization", accessToken)
                .delete(BASE_URL + "/api/auth/user");
    }

    @Step("Удаление пользователя по email и паролю")
    public void deleteUserByCredentials(String email, String password) {
        User tempUser = new User(email, password, "");
        loginUser(tempUser);
        deleteUser();
    }
}