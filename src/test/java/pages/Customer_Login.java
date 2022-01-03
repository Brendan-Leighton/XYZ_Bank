package pages;
// JAVA
import java.io.IOException;
import java.util.Map;
// TEST-NG
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
// MY CUSTOM
import pageFactories.*;

public class Customer_Login extends BaseTest {
    Factory_Customer_Login CUSTOMER_LOGIN;
    Map<String, Map<String, String>> data_addCustomer;

    public Customer_Login() {
        CUSTOMER_LOGIN = _Factories.Customer_Login();
    }

    @BeforeMethod
    public void setup() throws IOException {
        CUSTOMER_LOGIN.navigateTo_simple();
        data_addCustomer = BaseTest.getExcel().getPageData(4);
    }

    @Test
    public void testNavigationHome() {
        CUSTOMER_LOGIN.testNavbar_homeButton();
    }

    @Test
    public void testLoginForm() throws IOException {
        CUSTOMER_LOGIN.loginUser();
    }
}
