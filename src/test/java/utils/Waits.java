package utils;
// SELENIUM
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waits {
    private static Waits WAITS;
    private static WebDriverWait forElement;
    private static WebDriverWait forUrl;
    private static WebDriverWait forAlert;

    private Waits() {
        forElement = new WebDriverWait(Drivers.getDriver(), 20);
        forUrl = new WebDriverWait(Drivers.getDriver(), 15);
        forAlert = new WebDriverWait(Drivers.getDriver(), 5);
    }

    public static Waits getWaits() {
        if (WAITS == null) WAITS = new Waits();
        return WAITS;
    }

    public static void forElement(WebElement element) {
        getWaits();
        forElement.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void forElement_andClick(WebElement el) {
        forElement(el);
        el.click();
    }

    public static void forUrl(String url) {
        getWaits();
        forUrl.until(ExpectedConditions.urlToBe(url));
    }

    public static void forAlert() {
        getWaits();
        forAlert.until(ExpectedConditions.alertIsPresent());
    }
}
