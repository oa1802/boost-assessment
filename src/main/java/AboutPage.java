import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AboutPage {

    final String countryCellsLocator = "div.elementor-widget-wrap > section > div > div.elementor-col-33";

    @FindBy(css = countryCellsLocator) List<WebElement> countryCells;

    public AboutPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}