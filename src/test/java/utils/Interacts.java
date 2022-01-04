package utils;
// SELENIUM
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * <h1>Interacts:</h1>
 * <h2>Methods for interacting with webpages. </h2>
 * <p>
 *     These should be combinations of different ways to perform an action in the event the common/default way doesn't work. For instance, WebElement.click() is a way of clicking on an element and may not always work. Because of that you'll find a method here that first tries to WebElement.click(), then tries to click by performing Actions, then tries to implement the click via Javascript. Such a method with backup-plans should ensure a successful click in any scenario.
 * </p>
 */
public class Interacts {

    private static Interacts INTERACTS;
    private static Actions actions;

    private Interacts() {
        actions = new Actions(Drivers.getDriver());
    }

    public static Interacts initInteracts() {
        if (INTERACTS == null) INTERACTS = new Interacts();
        return INTERACTS;
    }

    /**
     * <h1>click()</h1>
     *
     * <h2>Description</h2>
     * <p>
     *     Try to click an element via multiple methods
     * </p>
     * <hr><br>
     *
     * <h2>Order of Operations</h2>
     * <ol>
     *     <li>WebElement.click(clickableElement)</li>
     *     <li>Actions.click(clickableElement)</li>
     *     <li>JavascriptExecutor.click(clickableElement)</li>
     * </ol>
     *
     * <hr>
     *
     * @param clickableElement a clickable WebElement
     */
    public static void click(WebElement clickableElement) {
        initInteracts();

        boolean isClicked = false;

        Waits.forElement(clickableElement);

        try { // click element via default Selenium method
            clickableElement.click();
            isClicked = true;
        } catch (Error ignored) {}

        if (!isClicked) {
            try { // click element via Actions class
                actions.moveToElement(clickableElement).click(clickableElement);

                isClicked = true;
            } catch (Error ignored) {}
        }

        if (!isClicked) {
            try { // click element via JavaScript Executor
                JavascriptExecutor js = (JavascriptExecutor) Drivers.getDriver();
                js.executeScript("arguments[0].click();", clickableElement);

                isClicked = true;
            } catch (Error ignored) {}
        }

        if (!isClicked) System.out.println("Element couldn't be clicked, reassess life choices.");
    }

}
