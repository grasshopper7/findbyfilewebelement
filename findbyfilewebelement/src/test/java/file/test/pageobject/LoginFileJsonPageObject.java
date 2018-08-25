package file.test.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import file.json.pagefactory.FindByJson;
import file.json.pagefactory.JsonFile;
import file.json.pagefactory.JsonFileProcessor;
import file.pagefactory.FileElementLocatorFactory;

@JsonFile(filePath="E:/sample-json-file.json")
public class LoginFileJsonPageObject {
	
	@FindByJson
	private WebElement usernameInput;
	
	@FindByJson
	private WebElement passwordInput;
	
	/*@MultipleFind(elementDetails = { "usernameInput@@ID@@username", "passwordInput@@NAME@@pwd" })
	private Map<String, WebElement> loginui;*/
	
	@FindBy(how=How.ID, using="loginButton")
	private WebElement loginLink;

	/*@FindBy(how=How.CSS, using="")
	private WebElement loginImage;*/
	
	
	public LoginFileJsonPageObject(WebDriver driver) {
				
		FileElementLocatorFactory melf = new FileElementLocatorFactory(driver, new JsonFileProcessor());
		PageFactory.initElements(melf, this);
	}
	
	public  void enterAndSubmitLoginDetails(String user, String pwd) {
		
		usernameInput.clear();
		passwordInput.clear();
		
		usernameInput.sendKeys(user);
		passwordInput.sendKeys(pwd);
		
/*		loginui.get("usernameInput").sendKeys(user);
		loginui.get("passwordInput").sendKeys(pwd);
*/		
		loginLink.click();
	}	

}
