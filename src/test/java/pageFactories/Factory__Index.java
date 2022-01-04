package pageFactories;
// JAVA
import java.io.IOException;
import java.util.*;
// SELENIUM
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
// MY CUSTOM
import utils.Asserts;
import utils.Drivers;
import utils.Waits;

/**
 * <h3>WebPageFactory METHODS:</h3>
 * <ul>
 *      <li>getButton_Home(): get "Home" button from navbar</li>
 *      <li>getUrl(): gets this pages url</li>
 *      <li>navigateTo(): given an ArrayList of WebElements,
 *          <ol><li>The driver will go to the site's Home-page</li>
 *          <li>loop and click the WebElements in the list</li>
 *          <li>assert the final URL matches this.url</li></ol></li>
 * </ul>
 */
public abstract class Factory__Index {

    // PROPS

    private final String url;
    private final String url_home = "https://www.way2automation.com/angularjs-protractor/banking/#/login";

    private WebElement table;
    private final List<WebElement> navigationElements = new ArrayList<>();
    private String loggedInUser = "";

    // CONSTRUCTORS

    public Factory__Index(String webPageUrl) {
        url = webPageUrl;
//        spreadsheet_data = pages.BaseTest.getSpreadsheet_Data();
    }

    // FINDERS

    @FindBy(xpath = "//button[@class=\"btn home\"]")
    private WebElement button_Home;

    @FindBy(xpath = "//button[@class=\"btn logout\"]")
    private WebElement button_Logout;

    // GETTERS

    public WebElement getButton_Home() {
        return button_Home;
    }

    public WebElement getButton_Logout() {
        return button_Logout;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public List<WebElement> getNavElements() {
        return this.navigationElements;
    }

    public String getUrl() {
        return this.url;
    }

    // DOERS

    public void goto_URL(String url) {
        Drivers.getDriver().navigate().to(url);
        Waits.forUrl(url);
    }

    public abstract void setNavElements();

    /**
     * <h1>Navigate to this page</h1>
     * <ol>
     *     <li>Check if we're already on this webpage</li>
     *     <li>IF: Not on this page, go to website's home-page</li>
     *     <li>Iterate a List of WebElements to traverse the site until we get to this page</li>
     * </ol>
     */
    public void navigateTo() {
        // if we're already on this webpage return, else navigate to it
        if (Drivers.getDriver().getCurrentUrl().equals(this.url)) {
            return;
        }

        // Home page
        goto_URL(this.url_home);

        this.setNavElements();

        // sequence of elements to click in order to get to this.url
        System.out.println("NAVIGATION: start");
        if (navigationElements.size() != 0) {
            for (WebElement el : navigationElements) {
                System.out.println("::  searching for... " + el.getText() + "  ::");
                Waits.forElement(el);
                System.out.println("    ... found");
                el.click();
                System.out.println("    ... clicked");
            }
        }

        // assert we arrived at this.url
        Asserts.navigation_toUrl(url);
    }

    /**
     * <h1>Navigate to this page's URL</h1>
     * <ol>
     *     <li>Check if we're already on this webpage</li>
     *     <li>IF: Not on this page, go to this page's URL</li>
     * </ol>
     */
    public void navigateTo_simple() {
        // if we're already on this webpage return, else navigate to it
        if (Drivers.getDriver().getCurrentUrl().equals(this.url)) {
            return;
        }

        // Home page
        goto_URL(this.url);
        // assert we arrived at this.url
        Asserts.navigation_toUrl(url);
    }

    /**
     * <h2>Navigate to this page and handle customer login</h2>
     * <ol>
     *     <li>Check if we're already on this webpage</li>
     *     <li>IF: Not on this page, go to website's home-page</li>
     *     <li>Iterate a List of WebElements to traverse the site until we get to this page</li>
     *     <li>IF: We encounter the customer-login page then login with the given user's credentials</li>
     * </ol>
     *
     * @param userIndexToLogin INT: Login as the customer who has this index number in the customer-dropdown on the customer-login page.
     */
    public void navigateTo(int userIndexToLogin) {
        if (Drivers.getDriver().getCurrentUrl().equals(this.url)) {
            return;
        }
        // Home page
        Drivers.getDriver().navigate().to(this.url_home);
        System.out.println("\nNEW NAVIGATION:\nnavigating to home page");
        Waits.forUrl(this.url_home);
        System.out.println("@ home page");

        // sequence of elements to click in order to get to this.url
        if (navigationElements.size() > 0) {
            System.out.println("New Navigation Sequence:");
            for (WebElement el : navigationElements) {
                Waits.forElement(el);
                System.out.println("element name: " + el.getText() + ", id: " + el.getAttribute("id"));
                // if clicking this element DOESN'T take us to the login page then click,
                // else perform login function
                if (!el.getAttribute("ng-click").equals("customer()")) {
                    el.click();
                    System.out.println("clicked");
                } else {  // login function
                    el.click();
                    Factory_Customer_Login customer_login = _Factories.Customer_Login();
                    Waits.forUrl(customer_login.getUrl());
                    System.out.println("logging in...");
                    // elements
                    WebElement loginButton = customer_login.getButton_Login();
                    WebElement selectUserDropdown = customer_login.getUserDropdown();
                    List<WebElement> userOptions = customer_login.getUserOptions();
                    // interact
                    Waits.forElement(selectUserDropdown);
                    selectUserDropdown.click();
                    Waits.forElement(userOptions.get(0)); // 0th = "---Your Name---" = default option
                    this.loggedInUser = userOptions.get(userIndexToLogin).getText().trim();
                    userOptions.get(userIndexToLogin).click();
                    Waits.forElement(loginButton);
                    loginButton.click();
                    System.out.println("logged in\n");
                }
            }
        }

        // wait for this.url page to load
        Waits.forUrl(this.url);
    }

    public void setNavigationElements(List<WebElement> elements) {
        if (this.navigationElements.size() > 0) return;

        this.navigationElements.addAll(elements);
    }

    public void addNavigationElement(WebElement element) {
        this.navigationElements.add(element);
    }

    public void clearNavigationElements() {
        this.navigationElements.clear();
    }

    /*
     * TABLE METHODS
     */

    /**
     *<h1>Get a < table > as a matrix</h1>
     *
     *
     *@return Gives a matrix of the set < table >, including the header-row
     * List < List < WebElement > >
     * table < table-row < cell-data > >
      */
    public List<List<WebElement>> getTable(WebElement table) {
        this.setTable(table);
        List<List<WebElement>> tableMatrix = new ArrayList<>();
        List<WebElement> tableRows = this.table.findElements(By.xpath(".//tr"));

        for (WebElement row : tableRows) {
            List<WebElement> tds = row.findElements(By.xpath(".//td"));
            tableMatrix.add(tds);
        }
        return tableMatrix;
    }

    private void setTable(WebElement table) {
        this.table = table;
    }

    /**
     * <h1>nextPageButton.click() until at last page</h1>
     *
     * <h2>Order Of Operations:</h2>
     * <ol>
     *     <li>click on the nextPageButton element.</li>
     *     <li>wait for the new nextPageButton to be clickable, if one is to load.</li>
     *     <li>IF: the current URL == the last URL then stop</li>
     *     <li>ELSE: Repeat steps 1-3</li>
     * </ol>
     *
     * @param nextPageButton The element used to get to the table's next page
     * @param currentUrl the current webpage's URL
     */
    public void getTablesLastPage(WebElement nextPageButton, String currentUrl) {
            Waits.forElement_andClick(nextPageButton);
            Waits.forElement(nextPageButton);
            if (Drivers.getDriver().getCurrentUrl().equals(currentUrl)) return;
            getTablesLastPage(nextPageButton, (Drivers.getDriver().getCurrentUrl()));
    }

    /**
     *
     * <h1>sortTableColumn</h1>
     * <p>
     *     Sort a table (passed in as a matrix "tableMatrix"). Sort the given column (passed in via "columnNumber"). Return a matrix where the given column has been sorted.
     * </p>
     * <h2>Order of Operations:</h2>
     * <ol>
     *     <li>Make a copy of tableMatrix w/out the header row</li>
     *     <li>Using a Comparator, sort the column numbered columnNumber</li>
     *     <li>Add the header row to the sorted matrix</li>
     *     <li>return sorted matrix w/header</li>
     * </ol>
     * @param tableMatrix A List< (of table rows) List< WebElement>>
     * @param columnNumber # of the column you want sorted.
     * @return a tableMatrix, List< (of table rows) List< WebElement>>, with the given column sorted in ascending order.
     */
    public List<List<WebElement>> sortTableColumn(List<List<WebElement>> tableMatrix, int columnNumber) {
        // make copy of matrix without header-row
        List<List<WebElement>> tableMatrixHeadless = new ArrayList<>();
        for (int i = 1; i < tableMatrix.size(); i++) {
            tableMatrixHeadless.add(tableMatrix.get(i));
        }

        // sort rows by given col#
        tableMatrixHeadless.sort((row1, row2) -> row1.get(columnNumber).getText().toLowerCase().compareTo(row2.get(columnNumber).getText().toLowerCase()));

        // add the header back in
        tableMatrixHeadless.add(0, tableMatrix.get(0));

        // return that bad-boy
        return tableMatrixHeadless;
    }

    /*
     *  TEST NAVBAR BUTTONS
     */

    /**
     * <h1>Test this pages Home button in the navbar</h1>
     *
     * <ol>
     *     <li>Navigate to this pages URL</li>
     *     <li>WebDriverWait for Home-button to be clickable</li>
     *     <li>Home-button.click()</li>
     *     <li>WebDriverWait for Home-url URL</li>
     *     <li>Assert currentUrl equals Home-url</li>
     *     <li>Navigate back to this pages URL</li>
     * </ol>
     */
    public void testNavbar_homeButton() {
        this.navigateTo_simple();
        Asserts.navigation_fromElement_toUrl(this.getButton_Home(), this.url_home);
        this.navigateTo_simple();
    }

    /**
     * <strong>METHOD NOT WORKING</strong>
     * <h1>Test this pages Home button in the navbar, and perform a Customer_Login</h1>
     *
     * <ol>
     *     <li>Navigate to this pages URL</li>
     *     <li>WebDriverWait for Home-button to be clickable</li>
     *     <li>Home-button.click()</li>
     *     <li>WebDriverWait for Home-url URL</li>
     *     <li>Assert currentUrl equals Home-url</li>
     * </ol>
     */
    public void testNavbar_homeButton_login() {
        // got to this.url
        this.navigateTo_simple();
        // click home button & assert taken to home page
        Asserts.navigation_fromElement_toUrl(this.getButton_Home(), this.url_home);
    }

    /**
     * <h1>Test this pages Logout button in the navbar</h1>
     * <strong>Only use on pages that required a customer login</strong>
     *
     * <ol>
     *     <li>Navigate to this pages URL</li>
     *     <li>WebDriverWait for Logout-button to be clickable</li>
     *     <li>Logout-button.click()</li>
     *     <li>WebDriverWait for Login-url URL</li>
     *     <li>Assert currentUrl equals Login-url</li>
     * </ol>
     */
    public void testNavbar_logoutButton() {
        // got to this.url
        this.navigateTo_simple();
        // click logout button & assert taken to login page
        Asserts.navigation_fromElement_toUrl(this.getButton_Logout(), _Factories.Customer_Login().getUrl());
    }
}
