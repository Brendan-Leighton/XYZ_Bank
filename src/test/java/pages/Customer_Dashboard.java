package pages;
// JAVA
import java.io.IOException;
import java.util.List;
import java.util.Map;
// SELENIUM
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
// MY CUSTOM
import pageFactories.Factory_Customer_Dashboard;
import pageFactories.Factory_Customer_Login;
import pageFactories.Factory_Manager_CustomersTable;
import pageFactories._Factories;
import utils.Asserts;
import utils.Waits;

public class Customer_Dashboard extends BaseTest {
    Factory_Customer_Login CUSTOMER_LOGIN;
    Factory_Customer_Dashboard CUSTOMER_DASHBOARD;
    Map<String, Map<String, String>> data_addCustomer;
    Map<String, Map<String, String>> data_customerDashboard;
    String customerFirstName;
    String customerLastName;
    String amount_toDeposit_string;
    String amount_toWithdraw_string;
    Integer amount_toDeposit_int;
    Integer amount_toWithdraw_int;

    public Customer_Dashboard() throws IOException {
        CUSTOMER_LOGIN = _Factories.Customer_Login();
        CUSTOMER_DASHBOARD = _Factories.Customer_Dashboard();
        // DATA - manager_addCustomer
        data_addCustomer = BaseTest.getExcel().getPageData(4);
        customerFirstName = data_addCustomer.get("Manager-AddCustomer").get("INPUT-1").trim();
        customerLastName = data_addCustomer.get("Manager-AddCustomer").get("INPUT-2").trim();
        // DATA - customer_dashboard
        //        deposit amount
        data_customerDashboard = BaseTest.getExcel().getPageData(3);
        amount_toDeposit_string = data_customerDashboard.get("Customer-Deposit").get("INPUT-1");
        //        withdraw amount
        data_customerDashboard = BaseTest.getExcel().getPageData(2);
        amount_toWithdraw_string = data_customerDashboard.get("Customer-Withdraw").get("INPUT-1");
    }

    @BeforeMethod
    public void setup() throws IOException {
        CUSTOMER_LOGIN.loginUser();
    }

    // NAVBAR home
    @Test
    public void testHomeButton() throws IOException {
        CUSTOMER_DASHBOARD.testNavbar_homeButton_login();
    }

    // NAVBAR logout
    @Test
    public void testLogoutButton() throws IOException {
        CUSTOMER_DASHBOARD.testNavbar_logoutButton();
    }

    // ELEMENT 3: user_greeting_div
    @Test
    public void testUserGreeting() {

        Waits.forElement(CUSTOMER_DASHBOARD.getUserNameGreeting());
        // ASSERT
        Assert.assertEquals(CUSTOMER_DASHBOARD.getUserNameGreeting().getText(), customerFirstName + " " + customerLastName);
    }

    // ELEMENT 4: transaction_button -> navigates to transaction page
    @Test
    public void testNavigationTransactions() {
        // NAVIGATE & ASSERT
        Asserts.navigation_fromElement_toUrl(CUSTOMER_DASHBOARD.getButton_Transactions(), _Factories.Customer_Transactions().getUrl());
    }

    // ELEMENT 5: account_number_select
    @Test
    public void testSelectingAccountNumber() {
        // wait for elements
        Waits.forElement_andClick(CUSTOMER_DASHBOARD.getSelectAccountNumber());
        Waits.forElement(CUSTOMER_DASHBOARD.getOptionsAccountNumber().get(0));


        String accountNumbers_fromCustomer = "";
        for (WebElement option : CUSTOMER_DASHBOARD.getOptionsAccountNumber()) {
            accountNumbers_fromCustomer += (option.getText() + " ");
        }

        // navigate to and get the table of all Customers
        Factory_Manager_CustomersTable MANAGER_CUSTOMERS_TABLE = _Factories.Manager_CustomersTable();
        MANAGER_CUSTOMERS_TABLE.navigateTo_simple();
        // get table as matrix
        Waits.forElement(MANAGER_CUSTOMERS_TABLE.getTable_userAccounts());
        List<List<WebElement>> table_ofCustomers = MANAGER_CUSTOMERS_TABLE.getTable(MANAGER_CUSTOMERS_TABLE.getTable_userAccounts());
        // search rows for the firstName matching our customer in question
        for (List<WebElement> row : table_ofCustomers) {
            if (row.get(0).getText().contains(customerFirstName)) {
                // ASSERT accounts in MANAGER_CUSTOMER_TABLE match the account numbers from our customers account dropdown
                Assert.assertEquals(row.get(3).getText().trim(), accountNumbers_fromCustomer.trim());
            }
        }
    }

    // ELEMENT 6: Account_Info: does account# display/update properly?
    @Test
    public void testAccountInfoDisplay() {
        // INTERACT
        Waits.forElement_andClick(CUSTOMER_DASHBOARD.getSelectAccountNumber());
        Waits.forElement(CUSTOMER_DASHBOARD.getOptionsAccountNumber().get(0));
        for (WebElement option : CUSTOMER_DASHBOARD.getOptionsAccountNumber()) {
            String optionText = option.getText();
            option.click();
            // ASSERT selected account # matches the one displayed in the account-info component
            Assert.assertEquals(optionText, CUSTOMER_DASHBOARD.getAccountInfo().get(0).getText());
        }
    }

    // ELEMENT 7: Withdraw, Deposit, Account-Info
    @Test
    public void test() {
        try {
            amount_toDeposit_int = Integer.parseInt(amount_toDeposit_string.split("\\.")[0]);
            amount_toWithdraw_int = Integer.parseInt(amount_toWithdraw_string.split("\\.")[0]);
        } catch (Error e) {
            System.out.println("couldn't convert deposit and/or withdraw amounts from String to Integers.\n ERROR: " + e);
        }
        // INTERACT - deposit
        Waits.forElement(CUSTOMER_DASHBOARD.getButton_Deposit());
        CUSTOMER_DASHBOARD.getButton_Deposit().click();
        WebElement depositInput = CUSTOMER_DASHBOARD.getDepositComponentInput();
        WebElement depositSubmission = CUSTOMER_DASHBOARD.getDepositComponentButton();
        Waits.forElement(depositInput);
        depositInput.sendKeys(amount_toDeposit_int.toString());
        depositSubmission.click();
        List<WebElement> customerInfo = CUSTOMER_DASHBOARD.getAccountInfo();
        // ASSERT - deposit
        Assert.assertEquals(customerInfo.get(1).getText(), amount_toDeposit_int.toString());
        // INTERACT - withdraw
        String accountAmount_beforeWithdraw = CUSTOMER_DASHBOARD.getAccountInfo().get(1).getText();
        int amountBeforeWithdraw = 0;
        try {
            amountBeforeWithdraw = Integer.parseInt(accountAmount_beforeWithdraw);
        } catch (Error e) {
            System.out.println("couldn't convert account amount");
        }
        CUSTOMER_DASHBOARD.getButton_Withdraw().click();
        Waits.forElement(CUSTOMER_DASHBOARD.getWithdrawComponentInput());
        CUSTOMER_DASHBOARD.getWithdrawComponentInput().sendKeys(amount_toWithdraw_int.toString());
        CUSTOMER_DASHBOARD.getWithdrawComponentButton().click();
        customerInfo = CUSTOMER_DASHBOARD.getAccountInfo();
        int expectedAmount = amountBeforeWithdraw - amount_toWithdraw_int;
        // ASSERT - withdraw
        Assert.assertEquals(customerInfo.get(1).getText(), Integer.toString(expectedAmount));
    }
}
