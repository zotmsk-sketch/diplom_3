package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverProvider {
    private static final String BROWSER = System.getProperty("browser", "chrome");

    public static WebDriver getDriver() {
        if ("yandex".equalsIgnoreCase(BROWSER)) {
            return getYandexDriver();
        } else {
            return getChromeDriver();
        }
    }

    private static WebDriver getChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }

    private static WebDriver getYandexDriver() {
        System.setProperty("webdriver.chrome.driver", "C:/drivers/yandexdriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:/Users/zotov/AppData/Local/Yandex/YandexBrowser/Application/browser.exe");
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }
}