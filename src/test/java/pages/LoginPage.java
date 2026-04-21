package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    private final By emailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By registerLink = By.xpath("//a[text()='Зарегистрироваться']");
    private final By forgotPasswordLink = By.xpath("//a[text()='Восстановить пароль']");
    private final By loginLinkFromRegister = By.xpath("//a[text()='Войти']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Ввод email: {email}")
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
    }

    @Step("Ввод пароля")
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Нажать кнопку «Войти»")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Логин пользователя {email}")
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    @Step("Нажать ссылку «Зарегистрироваться»")
    public void clickRegisterLink() {
        driver.findElement(registerLink).click();
    }

    @Step("Нажать ссылку «Восстановить пароль»")
    public void clickForgotPasswordLink() {
        driver.findElement(forgotPasswordLink).click();
    }

    @Step("Нажать ссылку «Войти» на странице регистрации")
    public void clickLoginLinkFromRegister() {
        driver.findElement(loginLinkFromRegister).click();
    }
}