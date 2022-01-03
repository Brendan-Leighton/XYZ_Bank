package pageFactories;
// JAVA
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
// SELENIUM
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
// MY CUSTOM
import utils.Drivers;
import utils.Waits;

public class Factory_Customer_Dashboard extends Factory__Index {

    List<List<Integer>> accountsForAllUsers = new ArrayList<>(5);

    public Factory_Customer_Dashboard(WebDriver driver) {
        super(
                "https://www.way2automation.com/angularjs-protractor/banking/#/account"
        );

        PageFactory.initElements(driver, this);
    }

    /**
     * Page Elements and their GETTERS
     */

    // LOCATORS
    final String textGreeting_userName = "//strong//span";
    final String select_accountNumber = "//select[@id=\"accountSelect\"]";
    final String options_accountNumber = "//option";
    final String div_accountInfo_list = "//div[@class=\"center\"]//strong";
    final String button_Transactions = "//button[@ng-click=\"transactions()\"]";
    final String button_Deposit = "//button[@ng-click=\"deposit()\"]";
    final String button_Withdraw = "//button[@ng-click=\"withdrawl()\"]";  // misspelled in HTML
    // deposit component
    final String deposit_form = "//form[@ng-submit=\"deposit()\"]";
    // withdraw component
    final String withdraw_form = "//form[@ng-submit=\"withdrawl()\"]";  // misspelled in HTML
    final String withdraw_errorMessage_notEnoughFunds = "//span[@class=\"error ng-binding\"]";
    // deposit/withdraw generic
    final String input_amount = "//input";
    final String button_submit = "//button";

    // FINDERS

    @FindBy(xpath = textGreeting_userName)
    private WebElement userNameGreeting;

    @FindBy(xpath = select_accountNumber)
    private WebElement selectAccountNumber;

    @FindBy(xpath = select_accountNumber + options_accountNumber)
    private List<WebElement> optionsAccountNumber;

    @FindBy(xpath = div_accountInfo_list)
    private List<WebElement> accountInfo;

    @FindBy(xpath = button_Transactions)
    private WebElement buttonTransactions;

    @FindBy(xpath = button_Deposit)
    private WebElement buttonDeposit;

    @FindBy(xpath = button_Withdraw)
    private WebElement buttonWithdraw;

    @FindBy(xpath = deposit_form + input_amount)
    private WebElement depositComponentInput;

    @FindBy(xpath = deposit_form + button_submit)
    private WebElement depositComponentButton;

    @FindBy(xpath = withdraw_form + input_amount)
    private WebElement withdrawComponentInput;

    @FindBy(xpath = withdraw_form + button_submit)
    private WebElement withdrawComponentButton;

    @FindBy(xpath = withdraw_errorMessage_notEnoughFunds)
    private WebElement withdrawErrorMessageNotEnoughFunds;

    // GETTERS

    public WebElement getUserNameGreeting() {
        return userNameGreeting;
    }

    public WebElement getSelectAccountNumber() {
        return selectAccountNumber;
    }

    public List<WebElement> getOptionsAccountNumber() { return optionsAccountNumber; }

    public List<WebElement> getAccountInfo() {
        return accountInfo;
    }

    public WebElement getButton_Transactions() { return buttonTransactions; }

    public WebElement getButton_Deposit() {
        return buttonDeposit;
    }

    public WebElement getButton_Withdraw() {
        return buttonWithdraw;
    }

    public WebElement getDepositComponentInput() {
        return depositComponentInput;
    }

    public WebElement getDepositComponentButton() {
        return depositComponentButton;
    }

    public WebElement getWithdrawComponentInput() {
        return withdrawComponentInput;
    }

    public WebElement getWithdrawComponentButton() {
        return withdrawComponentButton;
    }

    public WebElement getWithdrawErrorMessageNotEnoughFunds() {
        return withdrawErrorMessageNotEnoughFunds;
    }

// DOERS
    public void depositMoney(int amount) throws IOException {
        if (!Drivers.getDriver().getCurrentUrl().equals(this.getUrl())) {
            _Factories.Customer_Login().loginUser_Hermoine();
        }
        int amount_beforeDeposit = Integer.parseInt(this.getAccountInfo().get(1).getText());
        this.getButton_Deposit().click();
        Waits.forElement(this.getDepositComponentInput());
        this.getDepositComponentInput().sendKeys(amount + "");
        this.getDepositComponentButton().click();
        int amount_afterDeposit = Integer.parseInt(this.getAccountInfo().get(1).getText());
        Assert.assertEquals(amount_afterDeposit, amount_beforeDeposit + amount);
    }

    public void withdrawMoney(int amount) {
        if (!Drivers.getDriver().getCurrentUrl().equals(this.getUrl())) {
            Drivers.getDriver().navigate().to(this.getUrl());
        }
        int amount_beforeWithdraw = Integer.parseInt(this.getAccountInfo().get(1).getText());
        this.getButton_Withdraw().click();
        new WebDriverWait(Drivers.getDriver(), 10).until(ExpectedConditions.elementToBeClickable(this.getWithdrawComponentButton()));
        this.getWithdrawComponentInput().sendKeys(amount + "");
        this.getWithdrawComponentButton().click();
        if (amount_beforeWithdraw - amount < 0) {
            WebElement errorMessage = this.getWithdrawErrorMessageNotEnoughFunds();
            new WebDriverWait(Drivers.getDriver(), 10).until(ExpectedConditions.visibilityOf(errorMessage));
            Assert.assertEquals(errorMessage.getText(), "Transaction Failed. You can not withdraw amount more than the balance.");
        }
        else {
            int amount_afterWithdraw = Integer.parseInt(this.getAccountInfo().get(1).getText());
            Assert.assertEquals(amount_afterWithdraw, amount_beforeWithdraw - amount);
        }
    }

    @Override
    public void setNavElements() {
        this.setNavigationElements(_Factories.Customer_Login().getNavElements());
    }
}
