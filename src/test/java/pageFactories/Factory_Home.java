package pageFactories;
// JAVA
import java.util.ArrayList;
// SELENIUM
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// CLASS
public class Factory_Home extends Factory__Index {

    // CONSTRUCTOR
    public Factory_Home(WebDriver driver) {
        super(
                "https://www.way2automation.com/angularjs-protractor/banking/#/login"
        );
        PageFactory.initElements(driver, this);
    }

    // FINDERS
    @FindBy (xpath = "//button[@class=\"btn home\"]")
    private WebElement button_Home;

    @FindBy (xpath = "//button[@ng-click='customer()']")
    private WebElement button_CustomerLogin;

    @FindBy (xpath = "//button[@ng-click='manager()']")
    private WebElement button_ManagerLogin;

    // GETTERS
    public WebElement getButton_CustomerLogin() {
        return button_CustomerLogin;
    }

    public WebElement getButton_ManagerLogin() {
        return button_ManagerLogin;
    }

    @Override
    // no additional elements are needed for Home page as the navigateTo() accesses home by default
    public void setNavElements() {
        this.setNavigationElements(new ArrayList<>(0));
    }

    // DOERS
    public void navigateTo() {
        super.navigateTo();
    }

    // TESTS
//    public void test_navigationTo_Customer_Login() {
//
//        // GET PAGES
//        Factory_Home home = factories.Home();
//        Factory_Customer_Login customer_login = factories.Customer_Login();
//
//        // NAVIGATE
//        home.navigateTo();
//
//        // INTERACT
//        waits.forElement(button_CustomerLogin);
//        button_CustomerLogin.click();
//
//        // ASSERT
//        asserts.navigation_toUrl(customer_login.getUrl());
//    }
//
//    public void test_navigationTo_Manager_Dashboard () {
//        // GET PAGES
//        Factory_Home home = this;
//        Factory_Manager_Dashboard manager_dashboard = factories.Manager_Dashboard();
//
//        // NAVIGATE
//        home.navigateTo();
//
//        // INTERACT
//        waits.forElement(home.getButton_ManagerLogin());
//        home.getButton_ManagerLogin().click();
//
//        // ASSERT
//        asserts.navigation_toUrl(manager_dashboard.getUrl());
//    }
}
