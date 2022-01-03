package pageFactories;
// JAVA
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
// SELENIUM
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
// MY CUSTOM
import utils.Drivers;
import utils.Waits;

public class Factory_Manager_AddCustomer extends Factory__Index {
    public Factory_Manager_AddCustomer(WebDriver driver) {
        super(
                "https://www.way2automation.com/angularjs-protractor/banking/#/manager/addCust"
        );
        PageFactory.initElements(driver, this);
    }

    // LOCATORS

    final String input_firstName = "//input[@ng-model=\"fName\"]";
    final String input_lastName = "//input[@ng-model=\"lName\"]";
    final String input_postCode = "//input[@ng-model=\"postCd\"]";
    final String button_addCustomer = "//button[@type=\"submit\"]";
    final String button_openAccount = "//button[@ng-click=\"openAccount()\"]";
    final String button_customers = "//button[@ng-click=\"showCust()\"]";

    // FINDERS

    @FindBy(xpath = input_firstName)
    private WebElement inputFirstName;

    @FindBy(xpath = input_lastName)
    private WebElement inputLastName;

    @FindBy(xpath = input_postCode)
    private WebElement inputPostCode;

    @FindBy(xpath = button_addCustomer)
    private WebElement buttonAddCustomer;

    @FindBy(xpath = button_openAccount)
    private WebElement buttonOpenAccount;

    @FindBy(xpath = button_customers)
    private WebElement buttonCustomers;

    // GETTERS

    public WebElement getInputFirstName() { return inputFirstName; }
    public WebElement getInputLastName() { return inputLastName; }
    public WebElement getInputPostCode() { return inputPostCode; }
    public WebElement getButtonAddCustomer() { return buttonAddCustomer; }
    public WebElement getButtonOpenAccount() {
        return buttonOpenAccount;
    }
    public WebElement getButtonCustomers() {
        return buttonCustomers;
    }

    @Override
    public void setNavElements() {
        List<WebElement> navEls = new ArrayList<>();
        navEls.add(_Factories.Home().getButton_ManagerLogin());
        navEls.add(_Factories.Manager_Dashboard().getButtonAddCustomer());
        this.setNavigationElements(navEls);
    }

    public boolean addCustomer(Map<String, Map<String, String>> data) {
        // NAVIGATE
        Factory_Manager_CustomersTable MANAGER_CUSTOMER_TABLE = _Factories.Manager_CustomersTable();
        MANAGER_CUSTOMER_TABLE.navigateTo_simple();

        // WAIT FOR <table>
        Waits.forElement(MANAGER_CUSTOMER_TABLE.getTable_userAccounts());
        List<List<WebElement>> currentCustomers = MANAGER_CUSTOMER_TABLE.getTable(MANAGER_CUSTOMER_TABLE.getTable_userAccounts());

        // CHECK IF CUSTOMER ALREADY EXISTS
        boolean customerExists = false;
        for (List<WebElement> customer : currentCustomers) {
            if (customer.get(0).getText().equals(data.get("Manager-AddCustomer").get("INPUT-1"))) {
                customerExists = true;
            }
        }
        if (customerExists) return false;

        // IF CUSTOMER DOESN'T EXISTS THEN ADD THEM
        // NAVIGATE
        this.navigateTo_simple();

        // INTERACT 1: sendKeys to input fields
        Waits.forElement(getInputFirstName());
        getInputFirstName().sendKeys(data.get("Manager-AddCustomer").get("INPUT-1"));
        Waits.forElement(getInputLastName());
        getInputLastName().sendKeys(data.get("Manager-AddCustomer").get("INPUT-2"));
        Waits.forElement(getInputPostCode());
        getInputPostCode().sendKeys(data.get("Manager-AddCustomer").get("INPUT-3"));

        // ASSERT 1: input fields contain the correct text
        Assert.assertEquals(getInputFirstName().getAttribute("value"), data.get("Manager-AddCustomer").get("INPUT-1"));
        Assert.assertEquals(getInputLastName().getAttribute("value"), data.get("Manager-AddCustomer").get("INPUT-2"));
        Assert.assertEquals(getInputPostCode().getAttribute("value"), data.get("Manager-AddCustomer").get("INPUT-3"));

        // INTERACT 2: click add-customer button and assert alert
        getButtonAddCustomer().click();
        Waits.forAlert();
        String alertText = Drivers.getDriver().switchTo().alert().getText();
        System.out.println("alertText: " + alertText);
        Drivers.getDriver().switchTo().alert().accept();
        Drivers.getDriver().switchTo().defaultContent();

        // ASSERT 2: addCustomer_button
        Assert.assertTrue(alertText.contains("Customer added successfully with customer id :"));
        return false;
    }
}
