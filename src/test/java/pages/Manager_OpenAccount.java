package pages;
// JAVA
import java.io.IOException;
import java.util.Map;
// TEST-NG
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
// MY CUSTOM
import pageFactories.Factory_Manager_AddCustomer;
import pageFactories.Factory_Manager_OpenAccount;
import pageFactories._Factories;
import utils.Asserts;

public class Manager_OpenAccount extends BaseTest {
    Factory_Manager_OpenAccount MANAGER_OPEN_ACCOUNT;
    Factory_Manager_AddCustomer MANAGER_ADD_CUSTOMER;

    Map<String, Map<String, String>> data_addCustomer;

    public Manager_OpenAccount() {
        MANAGER_OPEN_ACCOUNT = _Factories.Manager_OpenAccount();
        MANAGER_ADD_CUSTOMER = _Factories.Manager_AddCustomer();
    }

    @BeforeMethod
    public void setup() throws IOException {
        MANAGER_OPEN_ACCOUNT.navigateTo_simple();
    }

    // ELEMENT 1: navbar's Home button
    @Test
    public void testHomeButton() {
        MANAGER_OPEN_ACCOUNT.testNavbar_homeButton();
    }

    // ELEMENT 2: addCustomer_button -> navigates to addCustomerFactory
    @Test
    public void testNavigationToAddCustomer() {
        Asserts.navigation_fromElement_toUrl(MANAGER_OPEN_ACCOUNT.getButtonAddCustomer(), _Factories.Manager_AddCustomer().getUrl());
    }

    // ELEMENT 3: customers_button -> navigates to CustomersTableFactory
    @Test
    public void testNavigationToCustomersTable() {
        Asserts.navigation_fromElement_toUrl(MANAGER_OPEN_ACCOUNT.getButtonCustomers(), _Factories.Manager_CustomersTable().getUrl());
    }

    // ELEMENT 4: open-account form
    @Test
    public void testFormOpenAccount() throws IOException {
        // add a customer to open an account for
        data_addCustomer = BaseTest.getExcel().getPageData(4);
        MANAGER_ADD_CUSTOMER.addCustomer(data_addCustomer);

        MANAGER_OPEN_ACCOUNT.openAccount(data_addCustomer.get("Manager-AddCustomer").get("INPUT-1"));
    }
}
