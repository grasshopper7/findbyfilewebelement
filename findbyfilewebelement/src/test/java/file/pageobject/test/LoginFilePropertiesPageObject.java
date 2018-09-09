package file.pageobject.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import file.pagefactory.FileElementLocatorFactory;
import file.pagefactory.properties.FindByProperties;
import file.pagefactory.properties.PropertiesFile;
import file.pagefactory.properties.PropertiesFileProcessor;

@PropertiesFile(filePath = "E:/sample-properties-file.txt")
public class LoginFilePropertiesPageObject {

	@FindByProperties
	private WebElement usernameInput;

	@FindByProperties
	private WebElement passwordInput;

	/*
	 * @MultipleFind(elementDetails = { "usernameInput@@ID@@username",
	 * "passwordInput@@NAME@@pwd" }) private Map<String, WebElement> loginui;
	 */

	@FindBy(how = How.ID, using = "loginButton")
	private WebElement loginLink;

	/*
	 * @FindBy(how=How.CSS, using="") private WebElement loginImage;
	 */

	public LoginFilePropertiesPageObject(WebDriver driver) {

		FileElementLocatorFactory melf = new FileElementLocatorFactory(driver, new PropertiesFileProcessor());
		PageFactory.initElements(melf, this);
	}

	public void enterAndSubmitLoginDetails(String user, String pwd) {

		usernameInput.clear();
		passwordInput.clear();

		usernameInput.sendKeys(user);
		passwordInput.sendKeys(pwd);

		/*
		 * loginui.get("usernameInput").sendKeys(user);
		 * loginui.get("passwordInput").sendKeys(pwd);
		 */
		loginLink.click();
	}

}
