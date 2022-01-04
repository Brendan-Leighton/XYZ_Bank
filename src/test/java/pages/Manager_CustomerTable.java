package pages;
// JAVA
import java.io.IOException;
import java.util.List;
import java.util.Map;
// SELENIUM
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
// TEST-NG
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
// MY CUSTOM
import pageFactories.Factory_Manager_AddCustomer;
import pageFactories.Factory_Manager_CustomersTable;
import pageFactories.Factory_Manager_OpenAccount;
import pageFactories._Factories;
import utils.Asserts;
import utils.Drivers;
import utils.Waits;

public class Manager_CustomerTable extends BaseTest {
    Factory_Manager_CustomersTable MANAGER_CUSTOMER_TABLE;
    Factory_Manager_OpenAccount MANAGER_OPEN_ACCOUNT;
    Factory_Manager_AddCustomer MANAGER_ADD_CUSTOMER;

    Map<String, Map<String, String>> data_addCustomer;

    public Manager_CustomerTable() {
        MANAGER_CUSTOMER_TABLE = _Factories.Manager_CustomersTable();
        MANAGER_OPEN_ACCOUNT = _Factories.Manager_OpenAccount();
        MANAGER_ADD_CUSTOMER = _Factories.Manager_AddCustomer();
    }

    @BeforeMethod
    public void setup() throws IOException {
        MANAGER_CUSTOMER_TABLE.navigateTo_simple();
    }

    @Test
    public void testSearchBar() {
        // enter search phrase
        Waits.forElement(MANAGER_CUSTOMER_TABLE.getInputSearchBar());
        MANAGER_CUSTOMER_TABLE.getInputSearchBar().sendKeys("on");

        // check table shows expected result number
        Waits.forElement(MANAGER_CUSTOMER_TABLE.getTable_userAccounts());
        List<List<WebElement>> userAccounts_AFTER = MANAGER_CUSTOMER_TABLE.getTable(MANAGER_CUSTOMER_TABLE.getTable_userAccounts());
        Assert.assertEquals(userAccounts_AFTER.size(), 3); // .size() includes header row + search results (2) "Ron Weasly" and "Neville Longbottom"
        MANAGER_CUSTOMER_TABLE.getInputSearchBar().clear();
    }

    @Test
    public void testTableShowsNewAccounts() throws IOException {
        // wait for and get <table>
        Waits.forElement(MANAGER_CUSTOMER_TABLE.getTable_userAccounts());
        List<List<WebElement>> userAccounts_BEFORE = MANAGER_CUSTOMER_TABLE.getTable(MANAGER_CUSTOMER_TABLE.getTable_userAccounts());

        System.out.println("ASSERT table shows new accounts\nAccounts Before:");
        for (List<WebElement> row : userAccounts_BEFORE) {
            System.out.println(row.get(0).getText());
        }

        // add a new customer
        data_addCustomer = BaseTest.getExcel().getPageData(4);
        String customersName = data_addCustomer.get("Manager-AddCustomer").get("INPUT-1");
        boolean isCustomerAdded = MANAGER_ADD_CUSTOMER.addCustomer(data_addCustomer);
        if (isCustomerAdded) { // open an account for new customer
            MANAGER_OPEN_ACCOUNT.navigateTo_simple();
            MANAGER_OPEN_ACCOUNT.openAccount(customersName);

            // ASSERT table has a new account
            // navigate to and get <table> as a matrix
            MANAGER_CUSTOMER_TABLE.navigateTo_simple();
            Waits.forElement(MANAGER_CUSTOMER_TABLE.getTable_userAccounts());
            List<List<WebElement>> userAccounts_AFTER = MANAGER_CUSTOMER_TABLE.getTable(MANAGER_CUSTOMER_TABLE.getTable_userAccounts());

            System.out.println("Accounts After:");
            for (List<WebElement> row : userAccounts_AFTER) {
                System.out.println(row.get(0).getText());
            }

            Assert.assertEquals(userAccounts_AFTER.size(), userAccounts_BEFORE.size() + 1);
        }

        Waits.forElement_andClick(MANAGER_ADD_CUSTOMER.getButtonCustomers());
        WebElement table_ofCustomers = MANAGER_CUSTOMER_TABLE.getTable_userAccounts();
        Waits.forElement(table_ofCustomers);
        List<List<WebElement>> tableMatrix_ofCustomers = MANAGER_CUSTOMER_TABLE.getTable(table_ofCustomers);

        // ASSERT new customer exists in table
        boolean newCustomerExists = false;
        for (List<WebElement> row : tableMatrix_ofCustomers) {
            if (row.get(0).getText().contains(customersName)) {
                newCustomerExists = true;
            }
        }
        Assert.assertTrue(newCustomerExists);
    }

    // ELEMENT 5: delete a customer
    @Test
    public void testDeleteCustomer() {
        By Albus = By.xpath("//td[contains(text(), \"Albus\")]/following-sibling::td//button");

        Waits.forElement_andClick(MANAGER_CUSTOMER_TABLE.getInputSearchBar());
        MANAGER_CUSTOMER_TABLE.getInputSearchBar().sendKeys("Albus");

        // wait for and get <table>
        Waits.forElement(MANAGER_CUSTOMER_TABLE.getTable_userAccounts());
        List<List<WebElement>> userAccounts_BEFORE = MANAGER_CUSTOMER_TABLE.getTable(MANAGER_CUSTOMER_TABLE.getTable_userAccounts());

        // Dumbledore died so we closed his accounts
        Waits.forElement_andClick(Drivers.getDriver().findElement(Albus));

        // assert just 1 row was deleted
        Waits.forElement(MANAGER_CUSTOMER_TABLE.getTable_userAccounts());
        List<List<WebElement>> userAccounts_AFTER = MANAGER_CUSTOMER_TABLE.getTable(MANAGER_CUSTOMER_TABLE.getTable_userAccounts());
        Assert.assertEquals(userAccounts_AFTER.size(), userAccounts_BEFORE.size() - 1);

        // assert the given customer was deleted
        boolean albusExists = true;
        int numberOfAlbus = Drivers.getDriver().findElements(By.xpath("//td[contains(text(), \"Albus\")]")).size();
        if (numberOfAlbus == 0) albusExists = false;
        Assert.assertFalse(albusExists);
    }

    // Table sorting (all columns - firstName, lastName, postCode)
    @Test
    public void testTableSort() {
        Waits.forElement(MANAGER_CUSTOMER_TABLE.getTable_userAccounts());
        // SETUP - get WebElements of the sortable headers
        List<WebElement> sortableColumnHeaders = MANAGER_CUSTOMER_TABLE.getTable_userAccounts().findElements(By.xpath(".//a[contains(@ng-click, \"sortType\")]"));

        // INTERACT && ASSERT
        for (int i = 0; i < 3; i++) {
            // get expected assert
            List<List<WebElement>> tableSorted_expect = MANAGER_CUSTOMER_TABLE.sortTableColumn(
                    MANAGER_CUSTOMER_TABLE.getTable(
                            MANAGER_CUSTOMER_TABLE.getTable_userAccounts()
                    ), i);

            // sort table element - 1st click sorts descending, 2nd sorts ascending
            sortableColumnHeaders.get(i).click();
            sortableColumnHeaders.get(i).click();

            // get actual assert
            List<List<WebElement>> tableSorted_actual = MANAGER_CUSTOMER_TABLE.getTable(MANAGER_CUSTOMER_TABLE.getTable_userAccounts());

            // VIEW COMPARING MATRICES
            System.out.println("***\nCOMPARE MATRICES");
            for (int j = 0; j < tableSorted_actual.size() ; j++) {
                System.out.println(
                        "actual: " + tableSorted_actual.get(j).get(i).getText() +
                        "\nexpected: " + tableSorted_expect.get(j).get(i).getText() +
                        "\n"
                );
            }

            // ASSERT
            for (int j = 0; j < tableSorted_actual.size(); j++) {
                Assert.assertEquals(tableSorted_actual.get(j).get(i).getText(), tableSorted_expect.get(j).get(i).getText());
            }
        }
    }

    @Test
    public void testNavigationHomeButton() {
        MANAGER_CUSTOMER_TABLE.testNavbar_homeButton();
    }

    @Test
    public void testNavigationAddCustomer() {
        Asserts.navigation_fromElement_toUrl(MANAGER_CUSTOMER_TABLE.getButtonAddCustomer(), MANAGER_ADD_CUSTOMER.getUrl());
    }

    @Test
    public void testNavigationOpenAccount() {
        Asserts.navigation_fromElement_toUrl(MANAGER_CUSTOMER_TABLE.getButtonOpenAccount(), MANAGER_OPEN_ACCOUNT.getUrl());
    }
}
