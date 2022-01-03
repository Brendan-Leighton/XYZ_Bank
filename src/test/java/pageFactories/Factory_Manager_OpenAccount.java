package pageFactories;
// JAVA
import java.util.List;
// SELENIUM
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
// MY CUSTOM
import utils.Drivers;
import utils.Waits;

public class Factory_Manager_OpenAccount extends Factory__Index {

    public Factory_Manager_OpenAccount(WebDriver driver) {
        super(
                "https://www.way2automation.com/angularjs-protractor/banking/#/manager/openAccount"
        );
        PageFactory.initElements(driver, this);
    }

    // ELEMENT'S SELECTORS

    final String input_customerName_id = "userSelect";
    final String input_currencyType_id = "currency";
    final String button_process = "//button[@type=\"submit\"]";
    final String button_addCustomer = "//button[@ng-click=\"addCust()\"]";
    final String button_customers = "//button[@ng-click=\"showCust()\"]";

    // FIND ELEMENTS

    @FindBy(id = input_customerName_id)
    private WebElement inputCustomerName;

    @FindBy(id = input_currencyType_id)
    private WebElement inputCurrencyType;

    @FindBy(xpath = button_process)
    private WebElement buttonProcess;

    @FindBy(xpath = button_addCustomer)
    private WebElement buttonAddCustomer;

    @FindBy(xpath = button_customers)
    private WebElement buttonCustomers;


    // ELEMENT GETTERS

    public WebElement getInputCustomerName() {
        return inputCustomerName;
    }

    public List<WebElement> getInputCustomerNameOptions() {
        return inputCustomerName.findElements(By.xpath(".//option"));
    }

    public WebElement getInputCurrencyType() {
        return inputCurrencyType;
    }

    public List<WebElement> getInputCurrencyTypeOptions() {
        return inputCurrencyType.findElements(By.xpath(".//option"));
    }

    public WebElement getButtonProcess() {
        return buttonProcess;
    }

    public WebElement getButtonAddCustomer() {
        return buttonAddCustomer;
    }

    public WebElement getButtonCustomers() {
        return buttonCustomers;
    }

    // METHODS

    @Override
    public void setNavElements() {
        Factory_Manager_Dashboard managerDashboard = _Factories.Manager_Dashboard();
        this.setNavigationElements(managerDashboard.getNavElements());
        this.addNavigationElement(managerDashboard.getButtonOpenAccount());
    }

    public void openAccount(String customersName) {
        // navigate to open-account page
        navigateTo_simple();

        // use customersName to determine its index#
        final Factory_Manager_OpenAccount MANAGER_OPEN_ACCOUNT = _Factories.Manager_OpenAccount();
        Waits.forElement_andClick(MANAGER_OPEN_ACCOUNT.getInputCustomerName());
        Waits.forElement(getInputCustomerNameOptions().get(0));
        int customersIndex = 0;
        for (int i = 0; i < getInputCustomerNameOptions().size(); i++) {
            if (getInputCustomerNameOptions().get(i).getText().contains(customersName)) {
                customersIndex = i;
            }
        }

        // select added user from dropdown
        getInputCustomerNameOptions().get(customersIndex).click();

        // select currency for the new account
        Waits.forElement_andClick(getInputCurrencyType());
        Waits.forElement_andClick(getInputCurrencyTypeOptions().get(1)); // 1 == "dollar"
        Assert.assertEquals(getInputCurrencyType().getAttribute("value"), "Dollar");

        // submit form to create new account for newly added user
        getButtonProcess().click();

        // ASSERT - get alert-text for assert comparison
        String alertText = Drivers.getDriver().switchTo().alert().getText();
        Drivers.getDriver().switchTo().alert().accept();
        Drivers.getDriver().switchTo().defaultContent();
        Assert.assertTrue(alertText.contains("Account created successfully with account Number :"));
    }
}
