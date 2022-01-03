//package test;
//// JAVA
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//// SELENIUM
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//// TEST NG
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import pageFactories.BaseTest.factories;
//
//public class _Test extends BaseTest {
//
//    private void assert_Navigation(String url) {
//        wait_forNavigation.until(ExpectedConditions.urlToBe(url));
//        Assert.assertEquals(driver.getCurrentUrl(), url);
//    }
//
//    private void goto_URL(String url) {
//        driver.navigate().to(url);
//        this.assert_Navigation(url);
//    }
//
//    @Test
//    public void test_A0_Home() {
//
//        // get elements
//
//        WebElement button_CustomerLogin = BaseTest.factories.getHome().getButton_CustomerLogin();
//        WebElement button_ManagerLogin = BaseTest.factories.getHome().getButton_ManagerLogin();
//
//        // ELEMENT 1: home_button
//        // NAVIGATE INTERACT ASSERT
//        BaseTest.factories.getHome().testNavbar_homeButton();
//
//        // element 2: CUSTOMER LOGIN button -> navigates to a new page
//        // navigate to element's page
//        BaseTest.factories.getHome().navigateTo();
//        // interact
//        waitForElement(button_CustomerLogin);
//        button_CustomerLogin.click();
//        // assert
//        this.assert_Navigation(BaseTest.factories.getCustomer_Login().getUrl());
//
//        // element 3: MANAGER LOGIN button -> navigates to a new page
//        // navigate to element's page
//        BaseTest.factories.getHome().navigateTo();
//        // interact
//        waitForElement(button_ManagerLogin);
//        button_ManagerLogin.click();
//        // assert
//
//        this.assert_Navigation(BaseTest.factories.getManager_Dashboard().getUrl());
//    }
//
//    @Test
//    public void test_A1_CustomerLogin() {
//
//        // get elements
//        WebElement button_HomePage_CustomerLogin = BaseTest.factories.getHome().getButton_CustomerLogin();
//
//        WebElement userDropdown = BaseTest.factories.getCustomer_Login().getUserDropdown();
//        List<WebElement> userOptions = BaseTest.factories.getCustomer_Login().getUserOptions();
//        WebElement button_Login = BaseTest.factories.getCustomer_Login().getButton_Login();
//
//        BaseTest.factories.getCustomer_Login().addNavigationElement(button_HomePage_CustomerLogin);
//
//        // ELEMENT 1: home_button
//        // NAVIGATE INTERACT ASSERT
//        BaseTest.factories.getCustomer_Login().testNavbar_homeButton();
//
//        // element 2: USER ACCOUNT input -> select user to login as
//        // navigate to element's page
//        BaseTest.factories.getCustomer_Login().navigateTo();
//        // interact
//        waitForElement(userDropdown);
//        userDropdown.click();
//        waitForElement(userOptions.get(0));  // 0th should be the "---Your Name---" default option
//        userOptions.get(5).click();  // Neville Longbottom
//        // assert
//        Assert.assertEquals(userOptions.size(), 6);
//        Assert.assertEquals(userDropdown.getAttribute("value"), "5");  // value == user's index #
//
//        // element 3: LOGIN button -> navigates to a new page
//        // NAVIGATE: no need, continue from prev code
//        // INTERACT
//        waitForElement(button_Login);
//        button_Login.click();
//        // ASSERT
//        this.assert_Navigation(customerAccountFactory.getUrl());
//    }
//
//    @Test
//    public void test_A2_CustomerAccount() {
//
//        // SETUP NAVIGATE METHOD
//        customerAccountFactory.addNavigationElement(BaseTest.factories.getHome().getButton_CustomerLogin());
//
//
//        // ELEMENT 1: home_button
//        // NAVIGATE INTERACT ASSERT
//        customerAccountFactory.testNavbar_homeButton(driver,1, BaseTest.factories.getCustomer_Login());
//
//        // ELEMENT 2: logout_button
//        // NAVIGATE INTERACT ASSERT
//        customerAccountFactory.testNavbar_logoutButton(driver, 1, BaseTest.factories.getCustomer_Login());
//
//        // ELEMENT 3: user_greeting_div
//        // NAVIGATE
//        customerAccountFactory.navigateTo(driver, 1, BaseTest.factories.getCustomer_Login());
//        // INTERACT
//        WebElement greeting = customerAccountFactory.getUserNameGreeting();
//        waitForElement(greeting);
//        // ASSERT
//        Assert.assertEquals(greeting.getText(), customerAccountFactory.getLoggedInUser());
//
//        // ELEMENT 4: transaction_button -> navigates to transaction page
//        // NAVIGATE
//        customerAccountFactory.navigateTo(driver, 1, BaseTest.factories.getCustomer_Login());
//        // INTERACT
//        WebElement button_transactionPage = customerAccountFactory.getButton_Transactions();
//        String url_transactionPage = customerTransactionFactory.getUrl();
//        waitForElement(button_transactionPage);
//        button_transactionPage.click();
//        // ASSERT
//        wait_forNavigation.until(ExpectedConditions.urlToBe(url_transactionPage));
//        Assert.assertEquals(driver.getCurrentUrl(), url_transactionPage);
//
//        // ELEMENT 5: account_number_select
//        // NAVIGATE
//        customerAccountFactory.navigateTo(driver, 1, BaseTest.factories.getCustomer_Login());
//        // INTERACT
//        WebElement select_accountNumber = customerAccountFactory.getSelectAccountNumber();
//        List<WebElement> options_accountNumber = customerAccountFactory.getOptionsAccountNumber();
//        waitForElement(select_accountNumber);
//        select_accountNumber.click();
//        waitForElement(options_accountNumber.get(0));
//        String accountNumbers_fromCustomer = "";
//        for (WebElement option : options_accountNumber) {
//            accountNumbers_fromCustomer += option.getText() + " ";
//        }
//        // ASSERT
//        // ASSERT - navigate to and get the table of all Customers
//
//        BaseTest.factories.getManager_CustomersTable.addNavigationElement(BaseTest.factories.getHome().getButton_ManagerLogin());
//        BaseTest.factories.getManager_CustomersTable.addNavigationElement(BaseTest.factories.getManager_Dashboard().getButtonCustomers());
//        BaseTest.factories.getManager_CustomersTable.testNavbar_homeButton(driver, 1, BaseTest.factories.getCustomer_Login());
//        String accountNumbers_fromManager = BaseTest.factories.getManager_CustomersTable.getTable(BaseTest.factories.getManager_CustomersTable.getTable_userAccounts()).get(1).get(3).getText().trim();
//        //ASSERT - verify the displayed account numbers actually belong to the customer
//        Assert.assertEquals(accountNumbers_fromManager, accountNumbers_fromCustomer.trim());
//
//        // ELEMENT 6.1: account_info: does account# display/update properly?
//        // NAVIGATE
//        customerAccountFactory.navigateTo(driver, 1, BaseTest.factories.getCustomer_Login());
//        // INTERACT
//        customerAccountFactory.getSelectAccountNumber().click();
//        for (WebElement option : options_accountNumber) {
//            waitForElement(option);
//            String optionText = option.getText();
//            option.click();
//            List<WebElement> customerInfo = customerAccountFactory.getAccountInfo();
//            // ASSERT
//            Assert.assertEquals(optionText, customerInfo.get(0).getText());
//        }
//
//        // ELEMENT 6.2: account_info: does account balance display/update properly?
//        // NAVIGATE
//        customerAccountFactory.navigateTo(driver, 1, BaseTest.factories.getCustomer_Login());
//        // INTERACT - deposit
//        waitForElement(customerAccountFactory.getButton_Deposit());
//        customerAccountFactory.getButton_Deposit().click();
//        WebElement depositInput = customerAccountFactory.getDepositComponentInput();
//        WebElement depositSubmission = customerAccountFactory.getDepositComponentButton();
//        waitForElement(depositInput);
//        depositInput.sendKeys("1985");
//        depositSubmission.click();
//        List<WebElement> customerInfo = customerAccountFactory.getAccountInfo();
//        // ASSERT - deposit
//        Assert.assertEquals(customerInfo.get(1).getText(), "1985");
//        // INTERACT - withdraw
//        customerAccountFactory.getButton_Withdraw().click();
//        waitForElement(customerAccountFactory.getWithdrawComponentInput());
//        customerAccountFactory.getWithdrawComponentInput().sendKeys("751");
//        customerAccountFactory.getWithdrawComponentButton().click();
//        customerInfo = customerAccountFactory.getAccountInfo();
//        // ASSERT - withdraw
//        Assert.assertEquals(customerInfo.get(1).getText(), "1234");
//    }
//
//    public Date parseDateString(String date, boolean datePickerDate) throws ParseException {
//        System.out.println("date: " + date + "... is datePicker: " +datePickerDate);
//        if (datePickerDate) {
//            String[] temp = date.split("T");
//            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(temp[0] + " " + temp[1]);
//        }
//        else {
//            int end = date.indexOf(" AM");
//            String[] temp = date.substring(0, end).split(",");
//            //  Jan 1, 2015 12:00:00 AM  -> //  Jan 1 2015 12:00:00
//            return new SimpleDateFormat("MMM d yyyy hh:mm:ss").parse(temp[0] + temp[1]);
//        }
//    }
//
//    public void getTablesLastPage(WebElement nextPageButton, String currentUrl) {
//            nextPageButton.click();
//            waitForElement(nextPageButton);
//            if (driver.getCurrentUrl().equals(currentUrl)) return;
//            getTablesLastPage(nextPageButton, (driver.getCurrentUrl()));
//    }
//
//    @Test
//    public void test_A3_CustomerTransactionsPage() throws ParseException {
//        // SETUP NAVIGATION
//        customerTransactionFactory.addNavigationElement(BaseTest.factories.getHome().getButton_CustomerLogin());
//        customerTransactionFactory.addNavigationElement(customerAccountFactory.getButton_Transactions());
//
//        // ELEMENT 1: home_button
//        // NAVIGATE INTERACT ASSERT
//        customerTransactionFactory.testNavbar_homeButton(driver, 1, BaseTest.factories.getCustomer_Login());
//
//        // ELEMENT 2: logout_button
//        // NAVIGATE INTERACT ASSERT
//        customerTransactionFactory.testNavbar_logoutButton(driver, 1, BaseTest.factories.getCustomer_Login());
//
//        // ELEMENT 5: verify date-pickers
//        // NAVIGATE
//        customerTransactionFactory.navigateTo(driver, 1, BaseTest.factories.getCustomer_Login());
//        // INTERACT
//        List<List<WebElement>> transactionTable = customerTransactionFactory.getTable(customerTransactionFactory.getTableTransactions());
//        System.out.println("date picker value: "+customerTransactionFactory.getInputDateStart().getAttribute("value"));
//        Date input_startDate = parseDateString(customerTransactionFactory.getInputDateStart().getAttribute("value"), true);
//        Date input_endDate = parseDateString(customerTransactionFactory.getInputDateEnd().getAttribute("value"), true);
//        Date table_earliestDate = parseDateString(transactionTable.get(1).get(0).getText(), false);
//        // get to last page in table
//        getTablesLastPage(customerTransactionFactory.getTableButtonNextPage(), driver.getCurrentUrl());
//        transactionTable = customerTransactionFactory.getTable(customerTransactionFactory.getTableTransactions());
//        Date table_latestDate = parseDateString(transactionTable.get(transactionTable.size()-1).get(0).getText(), false);
//        // ASSERT
//        Assert.assertEquals(input_startDate, table_earliestDate);
//        Assert.assertEquals(input_endDate, table_latestDate);
//
//
//        // ELEMENT 3: verify recent transaction data
//        // SETUP - reset transactions table
//        customerTransactionFactory.navigateTo(driver, 1, BaseTest.factories.getCustomer_Login());
//        customerTransactionFactory.getButtonReset().click();
//        // SETUP - make deposits / withdrawals
//        int[] setupTransactions = new int[] {1_000,100,800,101,500,102};
//            // 1000 (   0 + 1000)  setupTransactions will alternate
//            // 900  (1000 - 100)   between Deposit and Withdraw.
//            // 1700 ( 900 + 800)   Starts with a balance of zero 0 and
//            // 1599 (1700 - 101)   makes an initial Deposit of 1000
//            // 2100 (1600 + 500)
//            // 1997 (2100 - 102)
//        for (int i = 0; i < setupTransactions.length; i+=2) {
//            customerAccountFactory.depositMoney(driver, setupTransactions[i]);
//            if (setupTransactions.length != i+1) {
//                customerAccountFactory.withdrawMoney(driver, setupTransactions[i + 1]);
//            }
//        }
//        // NAVIGATE
//        customerTransactionFactory.navigateTo(driver, 1, BaseTest.factories.getCustomer_Login());
//        // INTERACT
//        transactionTable = customerTransactionFactory.getTable(customerTransactionFactory.getTableTransactions());
//        // ASSERT
//        System.out.println("\nAssert2");
//        for (int i = 1; i < transactionTable.size(); i++) {
////            Assert.assertEquals(Integer.parseInt(transactionTable.get(i).get(1).getText().trim()), setupTransactions[i-1]);
//            int tableAmount = Integer.parseInt(transactionTable.get(i).get(1).getText().trim());
//            int setupAmount = setupTransactions[i-1];
//            System.out.println("table: " + tableAmount + "\nreversed: " + setupAmount);
//            Assert.assertEquals(tableAmount, setupAmount);
//        }
//
//        // ELEMENT 4: verify proper sorting
//        // SETUP NAVIGATION - can skip and continue from previus code
//        // INTERACT
//       customerTransactionFactory.getTableSortDates().click(); // click Date-Time table-header
//        int[] reversedAmounts = new int[setupTransactions.length];
//        for (int i = setupTransactions.length - 1; i >= 0; i--) {
//            reversedAmounts[reversedAmounts.length - (i + 1)] = setupTransactions[i];
//        }
//        transactionTable = customerTransactionFactory.getTable(customerTransactionFactory.getTableTransactions());
//        // ASSERT
//        System.out.println("\nAssert2");
//        for (int i = 1; i < transactionTable.size(); i++) {
//            int tableSort = Integer.parseInt(transactionTable.get(i).get(1).getText().trim());
//            int reversedSort = reversedAmounts[i-1];
//            System.out.println("table: " + tableSort + "\nreversed: " + reversedSort);
//            Assert.assertEquals(tableSort, reversedSort);
//        }
//
//    }
//
//    @Test
//    public void test_Z1_ManagerDashboard() {
//        // SETUP NAVIGATOR
//        BaseTest.factories.getManager_Dashboard().addNavigationElement(BaseTest.factories.getHome().getButton_ManagerLogin());
//
//        // ELEMENT 1: add_customer button -> navigates to addCustomerFactory
//        // NAVIGATE
//        BaseTest.factories.getManager_Dashboard().navigateTo();
//        // INTERACT
//        waitForElement(BaseTest.factories.getManager_Dashboard().getButtonAddCustomer());
//        BaseTest.factories.getManager_Dashboard().getButtonAddCustomer().click();
//        // ASSERT
//        assert_Navigation(addCustomerFactory.getUrl());
//
//        // ELEMENT 2: open_account button -> navigates to openAccountFactory
//        // NAVIGATE
//        BaseTest.factories.getManager_Dashboard().navigateTo();
//        // INTERACT
//        BaseTest.factories.getManager_Dashboard().getButtonOpenAccount().click();
//        // ASSERT
//        assert_Navigation(openAccountFactory.getUrl());
//
//        // ELEMENT 3: customers button -> navigates to BaseTest.factories.getManager_CustomersTableFactory
//        // NAVIGATE
//        BaseTest.factories.getManager_Dashboard().navigateTo();
//        // INTERACT
//        BaseTest.factories.getManager_Dashboard().getButtonCustomers().click();
//        // ASSERT
//        assert_Navigation(BaseTest.factories.getManager_CustomersTable.getUrl());
//    }
//
//    @Test
//    public void test_Z2_AddCustomer() {
//        //SETUP NAVIGATOR
//        addCustomerFactory.addNavigationElement(BaseTest.factories.getHome().getButton_ManagerLogin());
//        addCustomerFactory.addNavigationElement(BaseTest.factories.getManager_Dashboard().getButtonAddCustomer());
//
//        // SETUP FORM DATA
//        String input_firstName = "Magic";
//        String input_lastName = "Mike";
//        String input_postCode = "123456";
//
//        // ELEMENT 1: entire form
//        // NAVIGATE
//        addCustomerFactory.navigateTo();
//        // INTERACT 1: input fields
//        addCustomerFactory.getInputFirstName().sendKeys(input_firstName);
//        addCustomerFactory.getInputLastName().sendKeys(input_lastName);
//        addCustomerFactory.getInputPostCode().sendKeys(input_postCode);
//        // ASSERT 1: input fields
//        Assert.assertEquals(addCustomerFactory.getInputFirstName().getAttribute("value"), input_firstName);
//        Assert.assertEquals(addCustomerFactory.getInputLastName().getAttribute("value"), input_lastName);
//        Assert.assertEquals(addCustomerFactory.getInputPostCode().getAttribute("value"), input_postCode);
//        // INTERACT 2: addCustomer_button
//        addCustomerFactory.getButtonAddCustomer().click();
//        String alertText = driver.switchTo().alert().getText();
//        driver.switchTo().alert().accept();
//        driver.switchTo().defaultContent();
//        // ASSERT 2: addCustomer_button
//        Assert.assertTrue(alertText.contains("Customer added successfully with customer id :"));
//
//        // ELEMENT 2: openAccount_button -> navigates to openAccountFactory
//        // NAVIGATE
//        addCustomerFactory.navigateTo();
//        // INTERACT
//        addCustomerFactory.getButtonOpenAccount().click();
//        // ASSERT
//        assert_Navigation(openAccountFactory.getUrl());
//
//        // ELEMENT 3: customers_button -> navigates to BaseTest.factories.getManager_CustomersTableFactory
//        // NAVIGATE
//        addCustomerFactory.navigateTo();
//        // INTERACT
//        addCustomerFactory.getButtonCustomers().click();
//        // ASSERT
//        assert_Navigation(BaseTest.factories.getManager_CustomersTable.getUrl());
//    }
//
//    @Test
//    public void test_Z3_OpenAccountFactory() {
//        // SETUP NAVIGATOR
//        openAccountFactory.addNavigationElement(BaseTest.factories.getHome().getButton_ManagerLogin());
//        openAccountFactory.addNavigationElement(BaseTest.factories.getManager_Dashboard().getButtonOpenAccount());
//
//        // SETUP FORM DATA
//        String addedCustomer = "Harry Potter";
//
//        // SETUP ALERT-WAIT
//        WebDriverWait alertWait = (WebDriverWait) new WebDriverWait(driver, 5);
//
//        // ELEMENT 1: entire form
//        // NAVIGATE
//        openAccountFactory.navigateTo();
//        // INTERACT 1: input fields
//        openAccountFactory.getInputCustomerName().click();
//        for (WebElement el : openAccountFactory.getInputCustomerNameOptions()) {
//            if (el.getText().equals(addedCustomer)) {
//                el.click();
//            }
//        }
//        openAccountFactory.getInputCurrencyType().click();
//        for (WebElement el : openAccountFactory.getInputCurrencyTypeOptions()) {
//            if (el.getText().equals("Dollar")) {
//                el.click();
//            }
//        }
//        // ASSERT 1: input fields
//        Assert.assertEquals(openAccountFactory.getInputCustomerName().getAttribute("value"), "2"); // "Harry Potter" has a value of 2
//        Assert.assertEquals(openAccountFactory.getInputCurrencyType().getAttribute("value"), "Dollar");
//        // INTERACT 2: "process" button
//        openAccountFactory.getButtonProcess().click();
//        alertWait.until(ExpectedConditions.alertIsPresent());
//        String alertText = driver.switchTo().alert().getText();
//        driver.switchTo().alert().accept();
//        // ASSERT 2: "process" button
//        Assert.assertTrue(alertText.contains("Account created successfully with account Number :"));
//
//        // ELEMENT 2: addCustomer_button -> navigates to addCustomerFactory
//        // NAVIGATE
//        openAccountFactory.navigateTo();
//        // INTERACT
//        openAccountFactory.getButtonAddCustomer().click();
//        // ASSERT
//        assert_Navigation(addCustomerFactory.getUrl());
//
//        // ELEMENT 2: customers_button -> navigates to BaseTest.factories.getManager_CustomersTableFactory
//        // NAVIGATE
//        openAccountFactory.navigateTo();
//        // INTERACT
//        openAccountFactory.getButtonCustomers().click();
//        // ASSERT
//        assert_Navigation(BaseTest.factories.getManager_CustomersTable.getUrl());
//    }
//
//    @Test
//    public void test_Z4BaseTest.factories.getManager_CustomersTable() {
//        // SETUP NAVIGATOR
//        BaseTest.factories.getManager_CustomersTable.clearNavigationElements();
//        BaseTest.factories.getManager_CustomersTable.addNavigationElement(BaseTest.factories.getHome().getButton_ManagerLogin());
//        BaseTest.factories.getManager_CustomersTable.addNavigationElement(BaseTest.factories.getManager_Dashboard().getButtonCustomers());
//
//        // ELEMENT 1: home_button
//        // NAVIGATE INTERACT ASSERT
//        BaseTest.factories.getManager_CustomersTable.testNavbar_homeButton();
//
//        // ELEMENT 2: table (initial load of the table)
//        // NAVIGATE
//        BaseTest.factories.getManager_CustomersTable.navigateTo();
//        // INTERACT
//        List<List<WebElement>> userAccounts = BaseTest.factories.getManager_CustomersTable.getTable(BaseTest.factories.getManager_CustomersTable.getTable_userAccounts());
//        System.out.println("\nInitial load of userAccounts");
//        for (List<WebElement> row : userAccounts) {
//            System.out.println("User: " + row.get(0).getText());
//        }
//        // ASSERT
//        Assert.assertEquals(userAccounts.size(), 7); // size includes header row and newly added customer
//
//
//        // ELEMENT 3: table sorting (all columns - firstName, lastName, postCode)
//        // NAVIGATE - continuing from previous code
//        // SETUP - get WebElements of the sortable headers
//        List<WebElement> sortableColumnHeaders = BaseTest.factories.getManager_CustomersTable.getTable_userAccounts().findElements(By.xpath(".//a[contains(@ng-click, \"sortType\")]"));
//        // INTERACT && ASSERT
//        for (int i = 0; i < 3; i++) {
//            // get expected assert
//            List<List<WebElement>> tableSorted_expect = BaseTest.factories.getManager_CustomersTable.sortTableColumn(userAccounts, i);
//
//            // sort table element - 1st click sorts descending, 2nd sorts ascending
//            sortableColumnHeaders.get(i).click();
//            sortableColumnHeaders.get(i).click();
//
//            // get actual assert
//            List<List<WebElement>> tableSorted_actual = BaseTest.factories.getManager_CustomersTable.getTable(BaseTest.factories.getManager_CustomersTable.getTable_userAccounts());
//
//            // VIEW COMPARING MATRICES
//            System.out.println("***\nCOMPARE MATRICES");
//            for (int j = 0; j < tableSorted_actual.size() ; j++) {
//                System.out.println(
//                        "actual: " + tableSorted_actual.get(j).get(i).getText() +
//                        "\nexpected: " + tableSorted_expect.get(j).get(i).getText() +
//                        "\n"
//                );
//            }
//
//            // ASSERT
//            for (int j = 0; j < tableSorted_actual.size(); j++) {
//                Assert.assertEquals(tableSorted_actual.get(j).get(i).getText(), tableSorted_expect.get(j).get(i).getText());
//            }
//        }
//
//        // ELEMENT 4: table search-bar
//        // NAVIGATE
//        BaseTest.factories.getManager_CustomersTable.navigateTo();
//        // INTERACT && ASSERT
//        // search gets "Harry Potter", plus header row == size() of 2
//        BaseTest.factories.getManager_CustomersTable.getInputSearchBar().sendKeys("har");
//        Assert.assertEquals(
//                BaseTest.factories.getManager_CustomersTable.getTable(BaseTest.factories.getManager_CustomersTable.getTable_userAccounts()).size(),
//                2
//        );
//        // search gets "Ron" and Neville "Longbottom", plus header row == size() of 3
//        BaseTest.factories.getManager_CustomersTable.getInputSearchBar().clear();
//        BaseTest.factories.getManager_CustomersTable.getInputSearchBar().sendKeys("on");
//        Assert.assertEquals(
//                BaseTest.factories.getManager_CustomersTable.getTable(BaseTest.factories.getManager_CustomersTable.getTable_userAccounts()).size(),
//                3
//        );
//        BaseTest.factories.getManager_CustomersTable.getInputSearchBar().clear(); // search is persistent and fails customer deleting test
//
//        // ELEMENT 5: delete a customer
//        // NAVIGATE
//        BaseTest.factories.getManager_CustomersTable.navigateTo();
//        // INTERACT
//        int tableSizeBeforeDelete = BaseTest.factories.getManager_CustomersTable.getTable(BaseTest.factories.getManager_CustomersTable.getTable_userAccounts()).size();
//        // Dumbledore died so we closed his accounts
//        driver.findElement(By.xpath("//td[contains(text(), \"Albus\")]/following-sibling::td//button")).click();
//        // ASSERT
//        // assert just 1 row was deleted
//        Assert.assertEquals(
//                BaseTest.factories.getManager_CustomersTable.getTable(BaseTest.factories.getManager_CustomersTable.getTable_userAccounts()).size(),
//                tableSizeBeforeDelete - 1
//        );
//        // assert the given customer was deleted
//        boolean albusExists = true;
//        int numberOfAlbus = driver.findElements(By.xpath("//td[contains(text(), \"Albus\")]")).size();
//        if (numberOfAlbus == 0) albusExists = false;
//        Assert.assertFalse(albusExists);
//
//    }
//}
