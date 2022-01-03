package utils;
// SELENIUM
import org.openqa.selenium.WebElement;
// TEST-NG
import org.testng.Assert;

public class Asserts {
    public static Asserts asserts;
    public static Waits waits;

    private Asserts(Waits newWaits) {
        waits = newWaits;
    }

    public static Asserts getAsserts() {
        if (asserts == null) asserts = new Asserts(Waits.getWaits());
        return asserts;
    }

    /**
     * <h1>navigation_toUrl</h1>
     * <ol>
     *     <li>WebDriverWait for URL</li>
     *     <li>Assert currentUrl equals URL</li>
     * </ol>
     *
     * @param url the url you're navigating to
     */
    public static void navigation_toUrl(String url){
        Waits.forUrl(url);
        Assert.assertEquals(Drivers.getDriver().getCurrentUrl(), url);
    }

    /**
     * <h1>navigation_fromElement_toUrl</h1>
     * <ol>
     *     <li>WebDriverWait for clickableElement to be clickable</li>
     *     <li>clickableElement.click()</li>
     *     <li>WebDriverWait for URL</li>
     *     <li>Assert currentUrl equals URL</li>
     * </ol>
     *
     * @param clickableElement an element, that when clicked, navigates to a new url.
     * @param url url you're navigating to.
     */
    public static void navigation_fromElement_toUrl(WebElement clickableElement, String url) {
        Waits.forElement(clickableElement);
        clickableElement.click();
        Asserts.navigation_toUrl(url);
    }
}
