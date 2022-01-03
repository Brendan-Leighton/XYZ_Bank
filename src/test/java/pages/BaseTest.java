package pages;
// JAVA
import java.io.IOException;
// SELENIUM
import org.openqa.selenium.WebDriver;
// TEST NG
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
// LOG-4-J
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
// MY CUSTOM
import pageFactories._Factories;
import utils.Asserts;
import utils.Drivers;
import utils.Excel_Sheet;
import utils.Waits;

public class BaseTest {
    private _Factories FACTORIES;
    WebDriver driver;
    Waits waits;
    Asserts asserts;
    private static Excel_Sheet excel;
    static Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeTest
    public void setup() throws IOException {
        PropertyConfigurator.configure("C:\\Users\\br3nd\\IdeaProjects\\Selenium\\src\\test\\java\\resources\\log4j2-test.properties");
        driver = Drivers.getDriver();
//        driver.manage().window().maximize();
        waits = Waits.getWaits();
        asserts = Asserts.getAsserts();
        FACTORIES = _Factories.getFactories();
        logger.info("@BeforeTest: info");
        logger.warn("@BeforeTest: warn");
        logger.error("@BeforeTest: error");
        logger.debug("@BeforeTest: debug");
        logger.fatal("@BeforeTest: fatal");
    }

    @AfterTest
    public void teardown() { Drivers.quit(); }

    public static Excel_Sheet getExcel() throws IOException {
        if (excel == null) {
            excel = Excel_Sheet.getExcelSheet(0, "C:\\Users\\br3nd\\IdeaProjects\\Selenium\\src\\test\\java\\resources\\Way2Auto_input.xlsx");
        }
        return excel;
    }
}



