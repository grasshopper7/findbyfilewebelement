package file.pageobject.test;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import file.pagefactory.FileElementLocatorFactory;
import file.pagefactory.FileFieldDecorator;
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
	
	@FindByProperties
	private List<WebElement> multiple;
	
	@FindByProperties
	private List<WebElement> multipleCSS;
	
	@FindBy(how = How.XPATH, using="//input[@name='pwd' or @id='username' or @id='newPwd']")
	private List<WebElement> multipleNormal;

	/*
	 * @FindBy(how=How.CSS, using="") private WebElement loginImage;
	 */

	public LoginFilePropertiesPageObject(WebDriver driver) {

		FileElementLocatorFactory felf = new FileElementLocatorFactory(driver, new PropertiesFileProcessor());
		FileFieldDecorator ffd = new FileFieldDecorator(felf);
		PageFactory.initElements(ffd, this);
	}

	public void enterAndSubmitLoginDetails(String user, String pwd) {

		usernameInput.clear();
		passwordInput.clear();

		usernameInput.sendKeys(user);
		passwordInput.sendKeys(pwd);
		
		System.out.println("FILE XPATH SIZE "+multiple.size());
		System.out.println("FILE CSS SIZE "+multipleCSS.size());
		System.out.println("NORMAL SIZE "+multipleNormal.size());

		/*
		 * loginui.get("usernameInput").sendKeys(user);
		 * loginui.get("passwordInput").sendKeys(pwd);
		 */
		loginLink.click();
	}

}
