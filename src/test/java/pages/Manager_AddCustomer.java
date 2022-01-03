package pages;
// JAVA
import java.io.IOException;
import java.util.Map;
// TEST-NG
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
// MY CUSTOM
import pageFactories.Factory_Manager_AddCustomer;
import pageFactories._Factories;
import utils.Asserts;

public class Manager_AddCustomer extends BaseTest {
    Factory_Manager_AddCustomer MANAGER_ADD_CUSTOMER;
    Map<String, Map<String, String>> data;

    public Manager_AddCustomer() {
        MANAGER_ADD_CUSTOMER = _Factories.Manager_AddCustomer();
    }

    @BeforeMethod
    public void setup() throws IOException {
        MANAGER_ADD_CUSTOMER.navigateTo_simple();
        System.out.println(BaseTest.getExcel());

    }

    // ELEMENT 1: HOME button navigation
    @Test
    public void testHomeButton() {
        MANAGER_ADD_CUSTOMER.testNavbar_homeButton();
    }

    // ELEMENT 2: form, add customer
    @Test
    public void testAddCustomerForm() throws IOException {
        data = BaseTest.getExcel().getPageData(4);
        for (Map.Entry<String, Map<String, String>> row : data.entrySet()) {
            System.out.println("row: " + row);
            System.out.println(
                    "Key: " + row.getKey() + ", Values: " + row.getValue()
            );
        }
        MANAGER_ADD_CUSTOMER.addCustomer(data);
    }

    // ELEMENT 3: openAccount_button -> navigates to openAccountFactory
    @Test
    public void testNavigationToOpenAccount() {
        // NAVIGATE
        Asserts.navigation_fromElement_toUrl(MANAGER_ADD_CUSTOMER.getButtonOpenAccount(), _Factories.Manager_OpenAccount().getUrl());
    }

    // ELEMENT 4: customers_button -> navigates to pages.BaseTest.factories.getManager_CustomersTableFactory
    @Test
    public void testNavigationToCustomersTable() {
        // NAVIGATE
        Asserts.navigation_fromElement_toUrl(MANAGER_ADD_CUSTOMER.getButtonCustomers(), _Factories.Manager_CustomersTable().getUrl());
    }
}
