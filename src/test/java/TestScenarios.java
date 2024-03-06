import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class TestScenarios {

    private static WebDriver driver;
    private static JavascriptExecutor javascriptExecutor;

    private NavigationBar navigationBar;
    private AboutPage aboutPage;
    private ContactPage contactPage;

    private String boostURL = "https://boostb2b.com";

    private static TestUtils testUtils;

    @BeforeAll
    public static void beforeAll() {
        testUtils = new TestUtils();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        javascriptExecutor = (JavascriptExecutor) driver;
    }

    @AfterEach
    public void afterAll() {
        driver.quit();
    }

    @Test
    public void scenario1() {

        // Navigate to boostb2b.com
        driver.get(boostURL);

        // Retrieve the list of tabs on the front page and save to a .csv or .txt with the title "BoostB2B_HeadersListing"
        navigationBar = new NavigationBar(driver);
        List<String> tabTitles = navigationBar.tabs.stream().map(WebElement::getText).toList();
        testUtils.saveListToFile("BoostB2B_HeadersListing", tabTitles);

        // Assert .txt file contents
        List<String> expectedTabTitles = Arrays.asList("Boost 100", "Buyers", "Suppliers", "Issuers", "Strategic Partners", "Solutions", "Insights", "Company", "Get Started");
        testUtils.assertFileContents("BoostB2B_HeadersListing", expectedTabTitles);

        // Get screenshot of screen and save as PNG with title "BoostB2B_Headers"
        testUtils.saveScreenshot(driver, "BoostB2B_Headers");

        // Assert .png file exists
        testUtils.assertFileExists("BoostB2B_Headers");
    }

    @Test
    public void scenario2() {

        // Navigate to boostb2b.com
        driver.get(boostURL);

        // Navigate to the about page
        navigationBar = new NavigationBar(driver);
        javascriptExecutor.executeScript("arguments[0].click();", navigationBar.companyTab);
        javascriptExecutor.executeScript("arguments[0].click();", navigationBar.companyTab);

        // Validate the following countries exist in the Global Footprint portion
        aboutPage = new AboutPage(driver);
        List<String> actualCountries = aboutPage.countryCells.stream()
                .map(WebElement::getText)
                .filter(text -> !text.isEmpty())
                .toList();
        actualCountries = actualCountries.subList(9, actualCountries.size());
        List<String> expectedCountriesList = Arrays.asList("AUSTRALIA", "AUSTRIA", "BELGIUM", "BRAZIL", "BULGARIA", "CANADA", "COLOMBIA", "CROATIA", "CYPRUS", "CZECH REPUBLIC", "DENMARK", "ECUADOR", "ESTONIA", "FINLAND", "FRANCE", "GERMANY", "GREECE", "HONG KONG", "HUNGARY", "ICELAND", "INDIA", "IRELAND", "ITALY", /* Website UI includes "JAMAICA" */ "LATVIA", "LIECHTENSTEIN", "LITHUANIA", "LUXEMBOURG", "MALAYSIA", "MALTA", "MEXICO", "NETHERLANDS", "NEW ZEALAND", "NORWAY", "POLAND", "PORTUGAL", "PUERTO RICO", "ROMANIA", "SINGAPORE", "SLOVAKIA", "SLOVENIA", "SPAIN", "SWEDEN", "SWITZERLAND", /* Website UI does not include "TURKIYE" */ "TURKIYE", "U.S.A.", "UNITED ARAB EMIRATES", "UNITED KINGDOM");
        // Valid countries list
//        List<String> expectedCountriesList = Arrays.asList("AUSTRALIA", "AUSTRIA", "BELGIUM", "BRAZIL", "BULGARIA", "CANADA", "COLOMBIA", "CROATIA", "CYPRUS", "CZECH REPUBLIC", "DENMARK", "ECUADOR", "ESTONIA", "FINLAND", "FRANCE", "GERMANY", "GREECE", "HONG KONG", "HUNGARY", "ICELAND", "INDIA", "IRELAND", "ITALY", "JAMAICA", "LATVIA", "LIECHTENSTEIN", "LITHUANIA", "LUXEMBOURG", "MALAYSIA", "MALTA", "MEXICO", "NETHERLANDS", "NEW ZEALAND", "NORWAY", "POLAND", "PORTUGAL", "PUERTO RICO", "ROMANIA", "SINGAPORE", "SLOVAKIA", "SLOVENIA", "SPAIN", "SWEDEN", "SWITZERLAND", "U.S.A.", "UNITED ARAB EMIRATES", "UNITED KINGDOM");
        assertIterableEquals(expectedCountriesList, actualCountries); // Test will fail due to mismatch between expected and actual results
    }

    @Test
    public void scenario3() {

        // Navigate to boostb2b.com
        driver.get(boostURL);

        // Navigate to the contact page
        navigationBar = new NavigationBar(driver);
        javascriptExecutor.executeScript("arguments[0].click();", navigationBar.getStartedTab);

        // Populate form with information
        contactPage = new ContactPage(driver);
        WebElement formIFrame = driver.findElement(By.cssSelector("iframe[src='https://go.boostb2b.com/l/492571/2020-02-25/nwm8d3']"));
        driver.switchTo().frame(formIFrame);
        contactPage.firstNameInput.sendKeys("Omar");
        contactPage.lastNameInput.sendKeys("Alkhalili");
        contactPage.emailInput.sendKeys("omaralkha@gmail.com");
        contactPage.titleInput.sendKeys("Testing Technical Assessment");
        contactPage.companyInput.sendKeys("Testing Technical Assessment");
        contactPage.countrySelectorOptions.get(1).click();
        contactPage.stateSelectorOptions.get(33).click();
        String date = testUtils.getDate("America/New_York");
        contactPage.commentsInput.sendKeys(date);
        javascriptExecutor.executeScript("arguments[0].click();", contactPage.careerOpportunitiesCheckBox);

        // Pause for user to validate reCAPTCHA
        testUtils.sleep(30000);

        // Click send
        contactPage.submitButton.click();
        testUtils.sleep(5000);
    }
}