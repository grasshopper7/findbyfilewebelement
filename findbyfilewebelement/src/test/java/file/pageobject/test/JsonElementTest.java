package file.pageobject.test;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class JsonElementTest {

	@Test
	public void test() {
		System.setProperty("webdriver.chrome.driver", "E:\\Software Testing\\Selenium\\Jars\\chromedriver.exe");
			
		WebDriver driver = new ChromeDriver();	
		driver.manage().window().maximize();
		driver.get("http://127.0.0.1:90/login.do");
		
		LoginFileJsonPageObject lmpo = new LoginFileJsonPageObject(driver);
		
		lmpo.enterAndSubmitLoginDetails("admin", "manager");
		
		//driver.quit();
	}
}
