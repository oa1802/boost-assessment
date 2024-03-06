import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class NavigationBar {

    final String tabsLocator = "#menu-1-6e4221c > li";
    final String companyTabLocator = "//a[contains(text(), 'Company')]";
    final String getStartedTabLocator = "//a[contains(text(), 'Get Started')]";

    @FindBy(css = tabsLocator) List<WebElement> tabs;
    @FindBy(xpath = companyTabLocator) WebElement companyTab;
    @FindBy(xpath = getStartedTabLocator) WebElement getStartedTab;

    public NavigationBar(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}