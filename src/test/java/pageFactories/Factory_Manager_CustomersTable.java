package pageFactories;
// SELENIUM
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
// JAVA
import java.util.List;

public class Factory_Manager_CustomersTable extends Factory__Index {

    List<List<WebElement>> customerTable;
    public Factory_Manager_CustomersTable(WebDriver driver) {
        super(
                "https://www.way2automation.com/angularjs-protractor/banking/#/manager/list"
        );
        PageFactory.initElements(driver, this);
    }

    // LOCATORS

    final String button_addCustomer = "//button[@ng-click=\"addCust()\"]";
    final String button_openAccount = "//button[@ng-click=\"openAccount()\"]";
    final String input_searchBar = "//input[@ng-model=\"searchCustomer\"]";
    final String table_userAccounts = "//table[@class=\"table table-bordered table-striped\"]";

    // FINDERS

    @FindBy(xpath = button_addCustomer)
    private WebElement buttonAddCustomer;

    @FindBy(xpath = button_openAccount)
    private WebElement buttonOpenAccount;

    @FindBy(xpath = input_searchBar)
    private WebElement inputSearchBar;

    @FindBy(xpath = table_userAccounts)
    private WebElement tableUserAccounts;

    // GETTERS

    public WebElement getButtonAddCustomer() { return buttonAddCustomer; }

    public WebElement getButtonOpenAccount() { return buttonOpenAccount; }

    public WebElement getInputSearchBar() { return inputSearchBar; }

    public WebElement getTable_userAccounts() { return tableUserAccounts; }

    @Override
    public void setNavElements() {}
}
