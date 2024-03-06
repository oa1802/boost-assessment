import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ContactPage {

    final String firstNameInputLocator = ".first_name > input";
    final String lastNameInputLocator = ".last_name > input";
    final String emailInputLocator = ".email > input";
    final String titleInputLocator = ".job_title > input";
    final String companyInputLocator = ".company > input";
    final String countrySelectorOptionsLocator = ".country > select > option";
    final String stateSelectorOptionsLocator = ".state > select > option";
    final String commentsInputLocator = ".comments > input";
    final String careerOpportunitiesCheckBoxLocator = "//label[text()='Career Opportunities']/preceding-sibling::input[@type='checkbox']";
    final String submitButtonLocator = "input[type='submit']";

    @FindBy(css = firstNameInputLocator) WebElement firstNameInput;
    @FindBy(css = lastNameInputLocator) WebElement lastNameInput;
    @FindBy(css = emailInputLocator) WebElement emailInput;
    @FindBy(css = titleInputLocator) WebElement titleInput;
    @FindBy(css = companyInputLocator) WebElement companyInput;
    @FindBy(css = countrySelectorOptionsLocator) List<WebElement> countrySelectorOptions;
    @FindBy(css = stateSelectorOptionsLocator) List<WebElement> stateSelectorOptions;
    @FindBy(css = commentsInputLocator) WebElement commentsInput;
    @FindBy(xpath = careerOpportunitiesCheckBoxLocator) WebElement careerOpportunitiesCheckBox;
    @FindBy(css = submitButtonLocator) WebElement submitButton;

    public ContactPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}