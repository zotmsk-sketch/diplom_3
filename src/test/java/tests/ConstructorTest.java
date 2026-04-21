package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import config.WebDriverProvider;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstructorTest {
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    public void setUp() {
        driver = WebDriverProvider.getDriver();
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @Test
    @Description("Переход к разделу «Булки»")
    public void testSwitchToBuns() {
        mainPage.clickSaucesTab();
        mainPage.clickBunsTab();
        assertTrue(mainPage.isActiveTab("Булки"));
    }

    @Test
    @Description("Переход к разделу «Соусы»")
    public void testSwitchToSauces() {
        mainPage.clickSaucesTab();
        assertTrue(mainPage.isActiveTab("Соусы"));
    }

    @Test
    @Description("Переход к разделу «Начинки»")
    public void testSwitchToFillings() {
        mainPage.clickFillingsTab();
        assertTrue(mainPage.isActiveTab("Начинки"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}