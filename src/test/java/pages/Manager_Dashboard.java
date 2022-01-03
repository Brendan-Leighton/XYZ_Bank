package pages;
// TEST-NG
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
// MY CUSTOM
import pageFactories.Factory_Manager_Dashboard;
import pageFactories._Factories;
import utils.Asserts;

public class Manager_Dashboard extends BaseTest {

    Factory_Manager_Dashboard MANAGER_DASHBOARD;

    public Manager_Dashboard() {
        MANAGER_DASHBOARD = _Factories.Manager_Dashboard();
    }

    @BeforeMethod
    public void setup() {
        MANAGER_DASHBOARD.navigateTo_simple();
    }

    // ELEMENT 1: HOME button navigation
    @Test
    public void testHomeButton() {
        MANAGER_DASHBOARD.testNavbar_homeButton();
    }

    // ELEMENT 2: ADD_CUSTOMER button navigation
    @Test
    public void testAddCusterButton() {
        Asserts.navigation_fromElement_toUrl(MANAGER_DASHBOARD.getButtonAddCustomer(), _Factories.Manager_AddCustomer().getUrl());
    }

    // ELEMENT 3: OPEN_ACCOUNT button navigation
    @Test
    public void testOpenAccountButton() {
        Asserts.navigation_fromElement_toUrl(MANAGER_DASHBOARD.getButtonOpenAccount(), _Factories.Manager_OpenAccount().getUrl());
    }

    // ELEMENT 4: OPEN_ACCOUNT button navigation
    @Test
    public void testCustomerTable() {
        Asserts.navigation_fromElement_toUrl(MANAGER_DASHBOARD.getButtonCustomers(), _Factories.Manager_CustomersTable().getUrl());
    }

}
