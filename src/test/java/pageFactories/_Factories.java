package pageFactories;

import org.openqa.selenium.WebDriver;
import utils.Drivers;

public class _Factories {
    private static _Factories FACTORIES;
    // PROPERTIES
    Factory_Home home;
    // customer pages
    Factory_Customer_Login customer_Login;
    Factory_Customer_Dashboard customer_Dashboard;
    Factory_Customer_Transaction customer_Transactions;
    // manager pages
    Factory_Manager_Dashboard manager_Dashboard;
    Factory_Manager_AddCustomer manager_addCustomer;
    Factory_Manager_OpenAccount manager_openAccount;
    Factory_Manager_CustomersTable manager_CustomersTable;

    // CONSTRUCTOR
    private _Factories(WebDriver driver) {
        this.home = new Factory_Home(driver);
        // customer pages
        this.customer_Login = new Factory_Customer_Login(driver);
        this.customer_Dashboard = new Factory_Customer_Dashboard(driver);
        this.customer_Transactions = new Factory_Customer_Transaction(driver);
        // manager pages
        this.manager_Dashboard = new Factory_Manager_Dashboard(driver);
        this.manager_addCustomer = new Factory_Manager_AddCustomer(driver);
        this.manager_openAccount = new Factory_Manager_OpenAccount(driver);
        this.manager_CustomersTable = new Factory_Manager_CustomersTable(driver);
    }

    public static _Factories getFactories() {
        if (FACTORIES == null) {
            FACTORIES = new _Factories(Drivers.getDriver());
        }
        return FACTORIES;
    }

    public _Factories get() {
        return FACTORIES;
    }

    public static Factory_Home Home() {
        return getFactories().home;
    }

    public static Factory_Customer_Login Customer_Login() {
        return getFactories().customer_Login;
    }

    public static Factory_Customer_Dashboard Customer_Dashboard() {
        return getFactories().customer_Dashboard;
    }

    public static Factory_Customer_Transaction Customer_Transactions() {
        return getFactories().customer_Transactions;
    }

    public static Factory_Manager_Dashboard Manager_Dashboard() {
        return getFactories().manager_Dashboard;
    }

    public static Factory_Manager_AddCustomer Manager_AddCustomer() {
        return getFactories().manager_addCustomer;
    }

    public static Factory_Manager_OpenAccount Manager_OpenAccount() {
        return getFactories().manager_openAccount;
    }

    public static Factory_Manager_CustomersTable Manager_CustomersTable() {
        return getFactories().manager_CustomersTable;
    }
}
