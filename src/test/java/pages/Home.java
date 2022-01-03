package pages;
// TEST-NG
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
// MY CUSTOM
import pageFactories.Factory_Customer_Login;
import pageFactories.Factory_Manager_Dashboard;
import pageFactories._Factories;
import pageFactories.Factory_Home;
import utils.Asserts;

// CLASS
public class Home extends BaseTest {

    Factory_Home HOME;
    Factory_Customer_Login customer_login;
    Factory_Manager_Dashboard manager_dashboard;

    public Home() {
        // PAGE FACTORIES
        HOME = _Factories.Home();
        customer_login = _Factories.Customer_Login();
        manager_dashboard = _Factories.Manager_Dashboard();
    }

    @BeforeClass
    public void beforeClass() {

    }

    @AfterClass
    public void afterClass() {

    }

    @BeforeMethod
    public void toHome() {
        HOME.navigateTo();
    }

    // ELEMENT 1: home_button
    @Test
    public void test_navbar_HomeButton() {
        Asserts.navigation_fromElement_toUrl(HOME.getButton_Home(), HOME.getUrl());
    }

    // ELEMENT 2: customerLogin_button
    @Test
    public void test_mainContent_CustomerLoginButton() {
        Asserts.navigation_fromElement_toUrl(HOME.getButton_CustomerLogin(), customer_login.getUrl());
    }

    // ELEMENT 3: managerLogin_button
    @Test
    public void test_mainContent_ManagerLoginButton() {
        Asserts.navigation_fromElement_toUrl(HOME.getButton_ManagerLogin(), manager_dashboard.getUrl());
    }
}
