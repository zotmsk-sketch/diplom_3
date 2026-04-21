package tests;

import api.UserApiClient;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import pages.LoginPage;
import pages.RegisterPage;
import config.WebDriverProvider;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private Map<String, String> testUser;
    private UserApiClient userApi;

    @BeforeEach
    public void setUp() {
        driver = WebDriverProvider.getDriver();
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        userApi = new UserApiClient();
    }

    @AfterEach
    public void tearDown() {
        // УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЯ (если был создан)
        if (testUser != null && userApi != null) {
            userApi.deleteUserByCredentials(testUser.get("email"), testUser.get("password"));
        }
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Description("Успешная регистрация — проверяем, что созданным пользователем можно войти")
    public void testSuccessfulRegistration() {
        testUser = UserApiClient.generateRandomUser();

        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
        registerPage.register(testUser.get("name"), testUser.get("email"), testUser.get("password"));

        // После регистрации пробуем войти
        if (!driver.getCurrentUrl().contains("login")) {
            mainPage.open();
            mainPage.clickLoginButton();
        }
        loginPage.login(testUser.get("email"), testUser.get("password"));
        assertTrue(mainPage.isConstructorHeaderDisplayed(), "Не удалось войти после регистрации");
    }

    @Test
    @Description("Ошибка для пароля меньше 6 символов")
    public void testRegistrationWithShortPassword() {
        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();

        registerPage.register("Тест", "test@test.ru", "123");

        assertTrue(registerPage.isErrorMessageDisplayed());
        assertEquals("Некорректный пароль", registerPage.getErrorMessageText());
        // пользователь не создаётся, удалять нечего
    }
}