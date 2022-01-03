package pageFactories;
// JAVA
import java.io.IOException;
import java.util.List;
import java.util.Map;
// SELENIUM
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
// MY CUSTOM
import pages.BaseTest;
import utils.Asserts;
import utils.Waits;

public class Factory_Customer_Login extends Factory__Index {

    public Factory_Customer_Login(WebDriver driver) {
        super(
                "https://www.way2automation.com/angularjs-protractor/banking/#/customer"
        );
        PageFactory.initElements(driver, this);
    }

    // SELECTORS

    final String select_user = "//select[@id=\"userSelect\"]";
    final String option_user = "//option";

    // FINDERS

    @FindBy(xpath = select_user)
    private WebElement userDropdown;

    @FindBy(xpath = select_user + option_user)
    private List<WebElement> userOptions;

    @FindBy(xpath = "//button[@class=\"btn btn-default\"]")
    private WebElement button_Login;

    // GETTERS

    public WebElement getUserDropdown() {
        return userDropdown;
    }

    public List<WebElement> getUserOptions() {
        return userOptions;
    }

    public WebElement getButton_Login() {
        return button_Login;
    }

    // DOERS

    public void loginUser(int userIndex) {
        navigateTo_simple();
        Waits.forElement(userDropdown);
        userDropdown.click();
        Waits.forElement(userOptions.get(userIndex)); // 0th should be the "---Your Name---" default option
        userOptions.get(userIndex).click();
    }

    public void loginUser() throws IOException {
        // get input data
        Map<String, Map<String, String>> data_addCustomer = BaseTest.getExcel().getPageData(4);
        String customerFirstName = data_addCustomer.get("Manager-AddCustomer").get("INPUT-1");

        // goto login form
        navigateTo_simple();

        Waits.forElement_andClick(getUserDropdown());
        Waits.forElement(getUserOptions().get(0));
        // if user exists then we select them
        boolean customerAlreadyInSystem = false;
        List<WebElement> userOptions = getUserOptions();
        for (WebElement user : userOptions) {
            if (user.getText().contains(customerFirstName)) {
                customerAlreadyInSystem = true;
                user.click();
            }
        }

        // if user doesn't exist we add the user then create a new account
        if (!customerAlreadyInSystem) {
            // add customer & create account
            _Factories.Manager_AddCustomer().addCustomer(data_addCustomer);
            _Factories.Manager_OpenAccount().openAccount(customerFirstName);
            // navigate to login & wait for elements
            navigateTo_simple();
            Waits.forElement_andClick(getUserDropdown());
            Waits.forElement(getUserOptions().get(0));

            userOptions = getUserOptions();
            for (WebElement user : userOptions) {
                if (user.getText().contains(customerFirstName)) {
                    user.click();
                }
            }
        }

        // wait for login button, click it, wait for url to change
        Waits.forElement_andClick(getButton_Login());
        Asserts.navigation_toUrl(_Factories.Customer_Dashboard().getUrl());
    }

    /**
     * <h1>loginUser_Hermoine</h1>
     * Login as Hermoine
     * @throws IOException
     */
    public void loginUser_Hermoine() throws IOException {
        String customersFirstName = "Hermoine";
        // goto login form
        navigateTo_simple();

        Waits.forElement_andClick(getUserDropdown());
        Waits.forElement(getUserOptions().get(0));

        // select a default user
        boolean customerAlreadyInSystem = false;
        List<WebElement> userOptions = getUserOptions();
        for (WebElement user : userOptions) {
            if (user.getText().contains(customersFirstName)) {
                customerAlreadyInSystem = true;
                user.click();
            }
        }

        // wait for login button, click it, wait for url to change
        Waits.forElement_andClick(getButton_Login());
        Asserts.navigation_toUrl(_Factories.Customer_Dashboard().getUrl());
    }

    @Override
    public void setNavElements() {
        this.addNavigationElement(_Factories.Home().getButton_CustomerLogin());
    }
}
