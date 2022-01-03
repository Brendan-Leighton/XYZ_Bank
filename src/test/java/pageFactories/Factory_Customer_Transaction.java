package pageFactories;
// JAVA
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
// SELENIUM
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Factory_Customer_Transaction extends Factory__Index {

    public Factory_Customer_Transaction(WebDriver driver) {
        super(
                "https://www.way2automation.com/angularjs-protractor/banking/#/listTx"
        );
        PageFactory.initElements(driver, this);
    }

    // LOCATORS

    private final String button_back = "//button[@ng-click=\"back()\"]";
    private final String button_reset = "//button[@ng-click=\"reset()\"]";
    private final String input_start_id = "start";
    private final String input_end_id = "end";
    private final String table_transactions = "//table[@class=\"table table-bordered table-striped\"]";
    private final String table_sortDates = "//a[@ng-click=\"sortType = 'date'; sortReverse = !sortReverse\"]";
    private final String table_prevPage = "//button[@ng-click=\"scrollLeft()\"]";
    private final String table_nextPage = "//button[@ng-click=\"scrollRight()\"]";

    // FINDERS

    @FindBy(xpath = button_back)
    private WebElement buttonBack;

    @FindBy(xpath = button_reset)
    private WebElement buttonReset;

    @FindBy(id = input_start_id)
    private WebElement inputDateStart;

    @FindBy(id = input_end_id)
    private WebElement inputDateEnd;

    @FindBy(xpath = table_transactions)
    private WebElement tableTransactions;

    public WebElement getTableSortDates() {
        return tableSortDates;
    }

    @FindBy(xpath = table_sortDates)
    private WebElement tableSortDates;

    @FindBy(xpath = table_prevPage)
    private WebElement tableButtonPrevPage;

    @FindBy(xpath = table_nextPage)
    private WebElement tableButtonNextPage;

    // GETTERS

    public WebElement getButtonBack() {
        return buttonBack;
    }

    public WebElement getButtonReset() {
        return buttonReset;
    }

    public WebElement getInputDateStart() {
        return inputDateStart;
    }

    public WebElement getInputDateEnd() {
        return inputDateEnd;
    }

    public WebElement getTableTransactions() {
        return tableTransactions;
    }

    public WebElement getTableButtonPrevPage() {
        return tableButtonPrevPage;
    }

    public WebElement getTableButtonNextPage() {
        return tableButtonNextPage;
    }

    @Override
    public void setNavElements() {
        Factory_Customer_Dashboard customerDashboard = _Factories.Customer_Dashboard();
        this.setNavigationElements(customerDashboard.getNavElements());
        this.addNavigationElement(customerDashboard.getButton_Transactions());
    }

    public Date parseDateString(String date, boolean datePickerDate) throws ParseException {
        System.out.println("date: " + date + "... is datePicker: " +datePickerDate);
        if (datePickerDate) {
            String[] temp = date.split("T");
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(temp[0] + " " + temp[1]);
        }
        else {
            int end = date.indexOf(" AM");
            String[] temp = date.substring(0, end).split(",");
            //  Jan 1, 2015 12:00:00 AM  -> //  Jan 1 2015 12:00:00
            return new SimpleDateFormat("MMM d yyyy hh:mm:ss").parse(temp[0] + temp[1]);
        }
    }
}
