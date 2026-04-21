package tests;

import api.User;
import api.UserApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import pages.LoginPage;
import pages.RegisterPage;
import config.WebDriverProvider;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private User testUser;
    private UserApiClient userApi;

    @BeforeEach
    @Step("Подготовка окружения перед тестом")
    public void setUp() {
        driver = WebDriverProvider.getDriver();
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        userApi = new UserApiClient();
    }

    @AfterEach
    @Step("Очистка после теста")
    public void tearDown() {
        if (testUser != null && userApi != null) {
            userApi.deleteUserByCredentials(testUser.getEmail(), testUser.getPassword());
        }
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Description("Успешная регистрация — проверяем, что созданным пользователем можно войти")
    @Step("Тест успешной регистрации")
    public void testSuccessfulRegistration() {
        testUser = UserApiClient.generateRandomUser();

        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
        registerPage.register(testUser.getName(), testUser.getEmail(), testUser.getPassword());

        if (!driver.getCurrentUrl().contains("login")) {
            mainPage.open();
            mainPage.clickLoginButton();
        }
        loginPage.login(testUser.getEmail(), testUser.getPassword());
        assertTrue(mainPage.isConstructorHeaderDisplayed(), "Не удалось войти после регистрации");
    }

    @Test
    @Description("Ошибка для пароля меньше 6 символов")
    @Step("Тест ошибки для короткого пароля")
    public void testRegistrationWithShortPassword() {
        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();

        registerPage.register("Тест", "test@test.ru", "123");

        assertTrue(registerPage.isErrorMessageDisplayed());
        assertEquals("Некорректный пароль", registerPage.getErrorMessageText());
    }
}