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

    /**
     * <h1>forElement()</h1>
     *
     * <h2>Order of Operations</h2>
     * <ol>
     *     <li>Initialize Waits class if not done so</li>
     *     <li>WebDriverWait for WebElement param to be clickable</li>
     * </ol>
     * @param element WebElement to wait for
     */
    public static void forElement(WebElement element) {
        getWaits();
        forElement.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * <h1>forElement(clickableElement)</h1>
     *
     * <h2>Description</h2>
     * <p>
     *     Wait for a WebElement to be clickable then click it.
     * </p>
     *
     * <hr><br>
     *
     * <h2>Order of Operations</h2>
     * <ol>
     *     <li>Initialize Waits class if not done so</li>
     *     <li>WebDriverWait for WebElement param to be clickable</li>
     *     <li>Interacts.click(param: clickableElement)
     *          <ol>
     *              <li>Try WebElement.click()</li>
     *              <li>Try Actions.click()</li>
     *              <li>Try JavascriptExecutor.click()</li>
     *          </ol></li>
 *          <li>THE END</li>
     * </ol>
     *
     * <hr>
     *
     * @param clickableElement a clickable WebElement to wait for
     */
    public static void forElement_andClick(WebElement clickableElement) {
        forElement(clickableElement);
        Interacts.click(clickableElement);
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
