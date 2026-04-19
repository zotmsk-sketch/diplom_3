package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class MainPage extends BasePage {
    private final String URL = "https://stellarburgers.education-services.ru/";

    private final By loginButtonMain = By.xpath("//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath("//p[text()='Личный Кабинет']");
    private final By constructorHeader = By.xpath("//h1[contains(text(),'Соберите бургер')]");
    private final By bunsTab = By.xpath("//span[text()='Булки']/parent::div");
    private final By saucesTab = By.xpath("//span[text()='Соусы']/parent::div");
    private final By fillingsTab = By.xpath("//span[text()='Начинки']/parent::div");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(URL);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButtonMain)).click();
    }

    public void clickPersonalAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(personalAccountButton)).click();
    }

    public void clickBunsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(bunsTab)).click();
    }

    public void clickSaucesTab() {
        wait.until(ExpectedConditions.elementToBeClickable(saucesTab)).click();
    }

    public void clickFillingsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(fillingsTab)).click();
    }

    public boolean isConstructorHeaderDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(constructorHeader));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isActiveTab(String tabName) {
        try {
            By tabLocator = By.xpath("//span[text()='" + tabName + "']/parent::div");
            wait.until(ExpectedConditions.attributeContains(tabLocator, "class", "tab_tab_type_current__2BEPc"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}