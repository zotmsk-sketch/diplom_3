package tests;

import io.qameta.allure.Description;
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

    @BeforeEach
    public void setUp() {
        driver = WebDriverProvider.getDriver();
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
    }

    @Test
    @Description("Успешная регистрация — проверяем, что созданным пользователем можно войти")
    public void testSuccessfulRegistration() {
        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();

        String email = "user_" + System.currentTimeMillis() + "@test.ru";
        String password = "123456";
        registerPage.register("Тест", email, password);


        if (!driver.getCurrentUrl().contains("login")) {
            mainPage.open();
            mainPage.clickLoginButton();
        }
        loginPage.login(email, password);
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
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}