package file.pageobject.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import file.pagefactory.FileElementLocatorFactory;
import file.pagefactory.excel.ExcelFile;
import file.pagefactory.excel.ExcelFileProcessor;
import file.pagefactory.excel.FindByExcel;

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
	
	
	public LoginFileExcelPageObject(WebDriver driver) {
				
		FileElementLocatorFactory melf = new FileElementLocatorFactory(driver, new ExcelFileProcessor());
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
