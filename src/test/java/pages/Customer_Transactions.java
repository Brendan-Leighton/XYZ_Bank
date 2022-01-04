package pages;
// JAVA
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
// SELENIUM
import org.openqa.selenium.WebElement;
// TEST-NG
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
// MY CUSTOM
import pageFactories.Factory_Customer_Dashboard;
import pageFactories.Factory_Customer_Login;
import pageFactories.Factory_Customer_Transaction;
import pageFactories._Factories;
import utils.Drivers;
import utils.Waits;

public class Customer_Transactions extends BaseTest {
    Factory_Customer_Login CUSTOMER_LOGIN;
    Factory_Customer_Dashboard CUSTOMER_DASHBOARD;
    Factory_Customer_Transaction CUSTOMER_TRANSACTIONS;

    public Customer_Transactions() throws IOException {
        CUSTOMER_LOGIN = _Factories.Customer_Login();
        CUSTOMER_DASHBOARD = _Factories.Customer_Dashboard();
        CUSTOMER_TRANSACTIONS = _Factories.Customer_Transactions();
    }

    @BeforeMethod
    public void setup() throws IOException {
        CUSTOMER_LOGIN.loginUser_Hermoine();
        Waits.forElement_andClick(CUSTOMER_DASHBOARD.getButton_Transactions());
    }

    // ELEMENT 1: home_button
    @Test
    public void testHomeButton() throws IOException {
        CUSTOMER_TRANSACTIONS.testNavbar_homeButton_login();
    }

    // ELEMENT 2: logout_button
    @Test
    public void testLogoutButton() throws IOException {
        CUSTOMER_TRANSACTIONS.testNavbar_logoutButton();
    }

    // ELEMENT 3: test correct data appears in the transactions-table and test the table's reset-button
    @Test
    public void testResetButton_andCorrectRecentData() throws IOException, InterruptedException {
        // SETUP - reset transactions table
        Waits.forElement_andClick(CUSTOMER_TRANSACTIONS.getButtonReset());
        Waits.forElement_andClick(CUSTOMER_TRANSACTIONS.getButtonBack());
        // SETUP - make deposits / withdrawals
        int[] setupTransactions = new int[] {1_000,100,800,101,500,102};
            // 1000 (   0 + 1000)  setupTransactions will alternate
            // 900  (1000 - 100)   between Deposit and Withdraw.
            // 1700 ( 900 + 800)   Starts with a balance of zero 0 and
            // 1599 (1700 - 101)   makes an initial Deposit of 1000
            // 2099 (1599 + 500)
            // 1997 (2099 - 102)
        for (int i = 0; i < setupTransactions.length; i+=2) {
            CUSTOMER_DASHBOARD.depositMoney(setupTransactions[i]);
            if (setupTransactions.length != i+1) { // NEEDED if setupTransactions length is an odd number.
                CUSTOMER_DASHBOARD.withdrawMoney(setupTransactions[i + 1]);
            }
        }
        Drivers.getDriver().navigate().refresh();  // refresh page or table won't load
        // NAVIGATE
        Waits.forElement_andClick(CUSTOMER_DASHBOARD.getButton_Transactions());
        // INTERACT
        Drivers.getDriver().navigate().refresh();  // refresh page or table won't load
        Waits.forElement(CUSTOMER_TRANSACTIONS.getTableTransactions());
        List<List<WebElement>> transactionTable = CUSTOMER_TRANSACTIONS.getTable(CUSTOMER_TRANSACTIONS.getTableTransactions());
        // ASSERT
        Assert.assertEquals(transactionTable.size() - 1, setupTransactions.length);  // subtract 1 because transactionTable includes header-row in size and setupTransactions doesn't
        System.out.println("\nAssert-1");
        for (int i = 1; i < transactionTable.size(); i++) {
//            Assert.assertEquals(Integer.parseInt(transactionTable.get(i).get(1).getText().trim()), setupTransactions[i-1]);
            int tableAmount = Integer.parseInt(transactionTable.get(i).get(1).getText().trim());
            int setupAmount = setupTransactions[i-1];
            System.out.println("from table: " + tableAmount + " | our input: " + setupAmount);
            Assert.assertEquals(tableAmount, setupAmount);
        }
    }

    // ELEMENT 4: table sorting
    @Test
    public void testTableSorting() throws IOException, InterruptedException {
        this.testResetButton_andCorrectRecentData();
        // get pre-sort table
        Waits.forElement(CUSTOMER_TRANSACTIONS.getTableTransactions());
        List<List<WebElement>> transactionTable = CUSTOMER_TRANSACTIONS.getTable(CUSTOMER_TRANSACTIONS.getTableTransactions());
        List<List<WebElement>> tableSorted = CUSTOMER_TRANSACTIONS.sortTableColumn(transactionTable, 0);
        String[] tableSorted_expected = new String[tableSorted.size() - 1]; // -1 to eliminate the header-row
        for (int rowNumber = 1; rowNumber < tableSorted.size(); rowNumber++) {
            tableSorted_expected[tableSorted.size() - 1 - rowNumber] = tableSorted.get(rowNumber).get(1).getText();
        }
        // sort <table>
        Waits.forElement_andClick(CUSTOMER_TRANSACTIONS.getTableSortDates()); // click Date-Time table-header

        List<List<WebElement>> tableSorted_actual = CUSTOMER_TRANSACTIONS.getTable(CUSTOMER_TRANSACTIONS.getTableTransactions());
        // ASSERT
        System.out.println("\nAssert-2");
        for (int i = 1; i < tableSorted_actual.size(); i++) {
            String rowValue_actual = tableSorted_actual.get(i).get(1).getText().trim();
            String rowValue_expected = tableSorted_expected[i-1].trim();
            System.out.println("actual: " + rowValue_actual + " | expected: " + rowValue_expected);
            Assert.assertEquals(rowValue_actual, rowValue_expected);
        }
    }


    // ELEMENT 5: verify date-pickers
    @Test
    public void testDatePicker() throws IOException, ParseException {
        // INTERACT
        Waits.forElement(CUSTOMER_TRANSACTIONS.getTableTransactions());
        List<List<WebElement>> transactionTable = CUSTOMER_TRANSACTIONS.getTable(CUSTOMER_TRANSACTIONS.getTableTransactions());
        System.out.println("date picker value: " + CUSTOMER_TRANSACTIONS.getInputDateStart().getAttribute("value"));

        // table date format: Jan 1, 2015 12:00:00 AM
        // date-picker: yyyy-MM-ddTHH:mm:ss
        Waits.forElement(CUSTOMER_TRANSACTIONS.getInputDateStart());
        Date input_startDate = CUSTOMER_TRANSACTIONS.parseDateString(CUSTOMER_TRANSACTIONS.getInputDateStart().getAttribute("value"), true);
//        System.out.println("input: " + inputDate_String);
        Date input_endDate = CUSTOMER_TRANSACTIONS.parseDateString(CUSTOMER_TRANSACTIONS.getInputDateEnd().getAttribute("value"), true);
        Date table_earliestDate = CUSTOMER_TRANSACTIONS.parseDateString(transactionTable.get(1).get(0).getText(), false);

        // input date data
        Waits.forElement_andClick(CUSTOMER_TRANSACTIONS.getInputDateEnd());

        // clear stupid red popup - maybe fix later but don't worry about this test failing
//        WebElement stupidRedPopup = Drivers.getDriver().findElement(By.xpath("//div[@class=\"meshim_widget_widgets\"]"));

        // get to last page in table
        CUSTOMER_TRANSACTIONS.getTablesLastPage(CUSTOMER_TRANSACTIONS.getTableButtonNextPage(), Drivers.getDriver().getCurrentUrl());
        transactionTable = CUSTOMER_TRANSACTIONS.getTable(CUSTOMER_TRANSACTIONS.getTableTransactions());
        Date table_latestDate = CUSTOMER_TRANSACTIONS.parseDateString(transactionTable.get(transactionTable.size() - 1).get(0).getText(), false);
        // ASSERT
        Assert.assertEquals(input_startDate, table_earliestDate);
        Assert.assertEquals(input_endDate, table_latestDate);
    }
}
