package base;

import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.selenium.Eyes;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.SearchPage;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class BaseTests4 {

    protected static WebDriver driver;
    protected static SearchPage page;
    protected static Eyes eyes;

    @BeforeClass
    public static void setUp() {
        Properties props = System.getProperties();
        try {
            props.load(new FileInputStream(new File("resources/test.properties")));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        driver = new ChromeDriver();
        initiateEyes();

        //driver.get(System.getProperty("site.url"));
        driver.get("https://the-internet.herokuapp.com/nested_frames");
        page = new SearchPage(driver);
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
        eyes.abortIfNotClosed();//if anycase validate window is not used
    }

    private static void initiateEyes() {
        eyes = new Eyes();
        eyes.setApiKey(System.getProperty("applitools.api.key"));
    }

    public void validateWindow() {
        eyes.open(driver, "Automation Bookstore", Thread.currentThread().getStackTrace()[2].getMethodName());
        eyes.setMatchLevel(MatchLevel.CONTENT); // setMatchLevel(enum)
        eyes.checkWindow();
        eyes.close();
    }

    public void validateElement(By locator) {
        eyes.open(driver, "Automation Bookstore", Thread.currentThread().getStackTrace()[2].getMethodName());
        eyes.checkElement(locator);
        eyes.close();
    }

    public void validateFrame(String locator) {
        eyes.open(driver, "The internet", Thread.currentThread().getStackTrace()[2].getMethodName());
        eyes.checkFrame(locator);
        eyes.close();
    }
}