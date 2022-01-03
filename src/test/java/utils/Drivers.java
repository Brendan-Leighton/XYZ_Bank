package utils;
// JAVA
import java.io.File;
import java.util.HashMap;
import java.util.Map;
// SELENIUM
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
// WEB DRIVER MANAGER
import io.github.bonigarcia.wdm.WebDriverManager;

public class Drivers {
    private static Drivers DRIVERS;
    private static WebDriver driver;
    private static WebDriver chrome;

    private Drivers() {
                String browser = Props.getProp("browser");
//        String browser = "chrome";
        try {
            if (driver == null) {
                if (browser.contains("chrome")) driver = Chrome();
                if (browser.contains("firefox")) driver = Firefox();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WebDriver getDriver() {
        if (DRIVERS == null) DRIVERS = new Drivers();
        return driver;
    }

    public static WebDriver Chrome() {
        /*
            TO SELECT BROWSER VERSION
            WebDriverManager.chromedriver().browserVersion("95").setup();
         */
        // SETUP WEB DRIVER
        WebDriverManager.chromedriver().setup();

        // CHROME DRIVER SETTINGS
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory",
                System.getProperty(
                        "user.dir" + File.separator +
                        "externalFiles" + File.separator +
                        "downloads"));
        ChromeOptions options = new ChromeOptions().setHeadless(true);
        options.setExperimentalOption("prefs", prefs);
        return new ChromeDriver(options);
    }

    private WebDriver Firefox() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }

    public static void quit() {
        driver.quit();
    }
}
