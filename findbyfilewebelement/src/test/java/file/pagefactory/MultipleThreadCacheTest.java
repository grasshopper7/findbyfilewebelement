package file.pagefactory;

import java.lang.reflect.Field;

import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import file.properties.pagefactory.FindByProperties;
import file.properties.pagefactory.PropertiesFile;
import file.properties.pagefactory.PropertiesFileProcessor;

public class MultipleThreadCacheTest {

	// Two threads of properties file processor at almost same time updating same
	// page object data
	@Test()
	public void twoSameTimePropertiesFileThreadsWithSamePageObject()
			throws NoSuchFieldException, SecurityException, InterruptedException {

		PropertiesFileProcessor pfp1 = Mockito.spy(PropertiesFileProcessor.class);
		PropertiesFileProcessor pfp2 = Mockito.spy(PropertiesFileProcessor.class);		
		
		Thread thread1 = propertiesThread(pfp1);
		Thread thread2 = propertiesThread(pfp2);
		
		thread1.start();
		thread2.start();
		
		thread1.join(5000);
		thread2.join(5000);
		
		//Second thread will be waiting at the synchronized line in AbstractFileAnnotations
		//buildBy(boolean) method. This will enter the PropertiesFileProcessor 
		//checkAndCallParseDataSource(Field) but will return after check.
		Mockito.verify(pfp1, Mockito.times(1)).checkAndCallParseDataSource(Mockito.any(Field.class));
		Mockito.verify(pfp2, Mockito.times(1)).checkAndCallParseDataSource(Mockito.any(Field.class));
		
		Mockito.verify(pfp1, Mockito.times(1)).parseDataSource();
		Mockito.verify(pfp2, Mockito.times(0)).parseDataSource();
		
		System.out.println(FieldByCache.size());
	}
	
	// Two threads of properties file processor at different time updating same
	// page object data
	@Test()
	public void twoDifferentTimePropertiesFileThreadsWithSamePageObject()
			throws NoSuchFieldException, SecurityException, InterruptedException {

		PropertiesFileProcessor pfp1 = Mockito.spy(PropertiesFileProcessor.class);
		PropertiesFileProcessor pfp2 = Mockito.spy(PropertiesFileProcessor.class);		
		
		Thread thread1 = propertiesThread(pfp1);
		Thread thread2 = propertiesThread(pfp2);
		
		thread1.start();
		Thread.sleep(4000);
		thread2.start();
		
		thread1.join(5000);
		thread2.join(5000);
		
		//Second thread will be not enter the if condition in AbstractFileAnnotations
		//buildBy(boolean) method and return. This will not enter the PropertiesFileProcessor 
		//checkAndCallParseDataSource(Field).
		Mockito.verify(pfp1, Mockito.times(1)).checkAndCallParseDataSource(Mockito.any(Field.class));
		Mockito.verify(pfp2, Mockito.never()).checkAndCallParseDataSource(Mockito.any(Field.class));
		
		Mockito.verify(pfp1, Mockito.times(1)).parseDataSource();
		Mockito.verify(pfp2, Mockito.never()).parseDataSource();
		
		System.out.println(FieldByCache.size());
	}

	private Thread propertiesThread(PropertiesFileProcessor pfp) {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				WebDriver driver = Mockito.mock(WebDriver.class);
				FileElementLocatorFactory felf = new FileElementLocatorFactory(driver, pfp);
				PageFactory.initElements(felf, new PageObjectProperties());
			}
		});
	}

	@PropertiesFile(filePath = "src/test/resources/properties/ThreadPOPropertiesData.properties")
	public class PageObjectProperties {
		@FindByProperties
		private WebElement element1;
		@FindByProperties
		private WebElement element2;
	}

}
