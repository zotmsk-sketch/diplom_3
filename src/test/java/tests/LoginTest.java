package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.*;
import config.WebDriverProvider;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private ForgotPasswordPage forgotPasswordPage;
    private String testEmail;
    private final String testPassword = "123456";
    private final String testName = "Тест";

    @BeforeEach
    public void setUp() {
        driver = WebDriverProvider.getDriver();
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        forgotPasswordPage = new ForgotPasswordPage(driver);

        // Создаём пользователя перед тестами
        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
        testEmail = "user_" + System.currentTimeMillis() + "@test.ru";
        registerPage.register(testName, testEmail, testPassword);
    }

    @Test
    @Description("Вход по кнопке «Войти в аккаунт» на главной")
    public void testLoginFromMainButton() {
        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.login(testEmail, testPassword);
        assertTrue(mainPage.isConstructorHeaderDisplayed());
    }

    @Test
    @Description("Вход через «Личный кабинет»")
    public void testLoginFromPersonalAccount() {
        mainPage.open();
        mainPage.clickPersonalAccount();
        loginPage.login(testEmail, testPassword);
        assertTrue(mainPage.isConstructorHeaderDisplayed());
    }

    @Test
    @Description("Вход через кнопку в форме регистрации")
    public void testLoginFromRegistrationForm() {
        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
        driver.findElement(By.xpath("//a[text()='Войти']")).click();
        loginPage.login(testEmail, testPassword);
        assertTrue(mainPage.isConstructorHeaderDisplayed());
    }

    @Test
    @Description("Вход через кнопку в форме восстановления пароля")
    public void testLoginFromForgotPasswordForm() {
        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.clickForgotPasswordLink();
        forgotPasswordPage.clickLoginLink();
        loginPage.login(testEmail, testPassword);
        assertTrue(mainPage.isConstructorHeaderDisplayed());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}