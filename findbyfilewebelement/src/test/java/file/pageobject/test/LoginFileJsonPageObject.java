package file.pageobject.test;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import file.pagefactory.FileElementLocatorFactory;
import file.pagefactory.FileFieldDecorator;
import file.pagefactory.json.FindByJson;
import file.pagefactory.json.JsonFile;
import file.pagefactory.json.JsonFileProcessor;
import file.pagefactory.properties.FindByProperties;
import file.pagefactory.properties.PropertiesFileProcessor;

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
	
	@FindByJson
	private List<WebElement> multiple;
	
	@FindByJson
	private List<WebElement> multipleCSS;
	
	@FindBy(how = How.XPATH, using="//input[@name='pwd' or @id='username' or @id='newPwd']")
	private List<WebElement> multipleNormal;

	/*@FindBy(how=How.CSS, using="")
	private WebElement loginImage;*/
	
	
	public LoginFileJsonPageObject(WebDriver driver) {
				
		FileElementLocatorFactory felf = new FileElementLocatorFactory(driver, new JsonFileProcessor());
		FileFieldDecorator ffd = new FileFieldDecorator(felf);
		PageFactory.initElements(ffd, this);
	}
	
	public  void enterAndSubmitLoginDetails(String user, String pwd) {
		
		usernameInput.clear();
		passwordInput.clear();
		
		usernameInput.sendKeys(user);
		passwordInput.sendKeys(pwd);
		
		System.out.println("FILE XPATH SIZE "+multiple.size());
		System.out.println("FILE CSS SIZE "+multipleCSS.size());
		System.out.println("NORMAL SIZE "+multipleNormal.size());
		
/*		loginui.get("usernameInput").sendKeys(user);
		loginui.get("passwordInput").sendKeys(pwd);
*/		
		loginLink.click();
	}	

}
