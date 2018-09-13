package file.pageobject.test;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import file.pagefactory.FileElementLocatorFactory;
import file.pagefactory.FileFieldDecorator;
import file.pagefactory.excel.ExcelFile;
import file.pagefactory.excel.ExcelFileProcessor;
import file.pagefactory.excel.FindByExcel;
import file.pagefactory.properties.FindByProperties;
import file.pagefactory.properties.PropertiesFileProcessor;

@ExcelFile(filePath="E:\\sample-xlsx-file.xlsx")
public class LoginFileExcelPageObject {
	
	@FindByExcel
	private WebElement usernameInput;
	
	@FindByExcel
	private WebElement passwordInput;
	
	/*@MultipleFind(elementDetails = { "usernameInput@@ID@@username", "passwordInput@@NAME@@pwd" })
	private Map<String, WebElement> loginui;*/
	
	@FindBy(how=How.ID, using="loginButton")
	private WebElement loginLink;

	/*@FindBy(how=How.CSS, using="")
	private WebElement loginImage;*/
	
	@FindByExcel
	private List<WebElement> multiple;
	
	@FindByExcel
	private List<WebElement> multipleCSS;
	
	@FindBy(how = How.XPATH, using="//input[@name='pwd' or @id='username' or @id='newPwd']")
	private List<WebElement> multipleNormal;

	
	
	public LoginFileExcelPageObject(WebDriver driver) {
				
		FileElementLocatorFactory felf = new FileElementLocatorFactory(driver, new ExcelFileProcessor());
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
