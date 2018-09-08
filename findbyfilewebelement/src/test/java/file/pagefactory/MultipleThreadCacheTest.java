package file.pagefactory;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import file.TestPage;
import file.properties.pagefactory.FindByProperties;
import file.properties.pagefactory.PropertiesAnnotation;
import file.properties.pagefactory.PropertiesFile;
import file.properties.pagefactory.PropertiesFileProcessor;

public class MultipleThreadCacheTest {
	
	/*
	 * Tests
	 * - Two threads of same file processor type at almost same time updating same page object data, repeat for other 2 types.
	 * - Two threads of same file processor type at different time updating same page object data, repeat for other 2 types.
	 * - Two threads of same file processor type at almost same time updating different page object data from same file, repeat for other 2 types.
	 * - Two threads of same file processor type at almost same time updating different page object data from different file, repeat for other 2 types.
	 * - Two threads of same file processor type at different time updating different page object data from same file, repeat for other 2 types.
	 * - Two threads of same file processor type at different time updating different page object data from different file, repeat for other 2 types.
	 * 
	 * - Three threads of different file processor type at almost same time updating same page object data
	 * - Three threads of different file processor type at almost same time updating different page object data
	 * 
	 * - Three threads of different file processor type at different time updating same page object data
	 * - Three threads of different file processor type at different time updating different page object data
	 * 
	 * -- OTHER TEST CASE
	 * - Three threads of different file processor type at almost same time updating different page object data with some standard @FindBy fields.
	 * - Two threads. Two PageObject. Two data file. One file has fields of one PO and some of second PO. Second file has other fields of second PO.
	 */
	
	@Before
	public void setup() {
		FieldByCache.removeDetails();
	}
	
	/*@After
	public void teardown() {
		System.out.println(FieldByCache.size());
	}*/

	private void genericTwoThreadsSamePageObject(FileProcessor[] fp, TestPage[] pages, boolean differentTime, 
			Integer[] checkCalls, Integer[] parseCalls, String findByAnnotation) throws InterruptedException {
						
		Thread thread1 = fileThread(fp[0], pages[0]);
		Thread thread2 = fileThread(fp[1], pages[1]);
		
		thread1.start();
		if(differentTime)
			Thread.sleep(1000);
		thread2.start();
		
		thread1.join(5000);
		thread2.join(5000);
		
		Mockito.verify(fp[0], Mockito.times(checkCalls[0])).checkAndCallParseDataSource(Mockito.any(Field.class));
		Mockito.verify(fp[1], Mockito.times(checkCalls[1])).checkAndCallParseDataSource(Mockito.any(Field.class));
		
		if(differentTime) {
			Mockito.verify(fp[0], Mockito.times(parseCalls[0])).parseDataSource();
			Mockito.verify(fp[1], Mockito.times(parseCalls[1])).parseDataSource();
		} else {
			//Need a better check to get exact counts
			Mockito.verify(fp[0], Mockito.atMost(parseCalls[0])).parseDataSource();
			Mockito.verify(fp[1], Mockito.atMost(parseCalls[1])).parseDataSource();
		}
		
		/*Assert.assertEquals("Locator Data has not been parsed properly.", 
				findByFieldCountInPageObject(pages[0],findByAnnotation), FieldByCache.size());*/
		checkFieldDataInCache(pages[0],findByAnnotation);
		
	}
	
	@SuppressWarnings("unchecked")
	private void checkFieldDataInCache(TestPage page, String findby) {
		int count =0 ;
		try {
			@SuppressWarnings("rawtypes")
			Class annot = Class.forName(findby);			
			for(Field f : page.getClass().getDeclaredFields()) {
				if(f.getAnnotation(annot) != null) {
					count++;
					Assert.assertTrue(f.getName()+" of class " + page.getClass() + " is not present in cache",
							FieldByCache.doesByExistForField(f));
				}
			}			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		Assert.assertEquals("Locator Data has not been parsed properly.", count, FieldByCache.size());
	}
	
	
	// Two threads of properties file processor at almost same time updating same
	// page object data
	@Test()
	public void twoSameTimePropertiesFileThreadsWithSamePageObject()
			throws NoSuchFieldException, SecurityException, InterruptedException {

		//Second thread will be waiting at the synchronized line of AbstractFileAnnotations
		//buildBy(Field) method. When the second thread enters it will return from the
		//checkAndCallParseDataSource(Field) check in AbstractFileAnnotations.
		
		PropertiesFileProcessor pfp1 = Mockito.spy(PropertiesFileProcessor.class);
		PropertiesFileProcessor pfp2 = Mockito.spy(PropertiesFileProcessor.class);	
		
		FileProcessor[] fp = {pfp1, pfp2};
		TestPage[] tp = {new PageObjectProperties(), new PageObjectProperties()};
		Integer[] checkCalls = {1,1};
		Integer[] parseCalls = {1,1};
		
		genericTwoThreadsSamePageObject(fp,tp,false,checkCalls,parseCalls,
				PropertiesAnnotation.getFindByAnnotationFullName());
	}
	
	// Two threads of properties file processor at different time updating same
	// page object data
	@Test()
	public void twoDifferentTimePropertiesFileThreadsWithSamePageObject()
			throws NoSuchFieldException, SecurityException, InterruptedException {
				
		//Second thread will be not enter the if condition in AbstractFileAnnotations
		//buildBy(boolean) method and return. This will not enter the PropertiesFileProcessor 
		//checkAndCallParseDataSource(Field).
		
		PropertiesFileProcessor pfp1 = Mockito.spy(PropertiesFileProcessor.class);
		PropertiesFileProcessor pfp2 = Mockito.spy(PropertiesFileProcessor.class);	
		
		FileProcessor[] fp = {pfp1, pfp2};
		TestPage[] tp = {new PageObjectProperties(), new PageObjectProperties()};
		Integer[] checkCalls = {1,0};
		Integer[] parseCalls = {1,0};
		
		genericTwoThreadsSamePageObject(fp,tp,true,checkCalls,parseCalls,
				PropertiesAnnotation.getFindByAnnotationFullName());								
	}

	private Thread fileThread(FileProcessor pfp, TestPage testPage) {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				WebDriver driver = Mockito.mock(WebDriver.class);
				FileElementLocatorFactory felf = new FileElementLocatorFactory(driver, pfp);
				PageFactory.initElements(felf, testPage);
			}
		});
	}

	@PropertiesFile(filePath = "src/test/resources/properties/ThreadPOPropertiesData.properties")
	public class PageObjectProperties implements TestPage {
		@FindByProperties
		private WebElement element1;
		@FindByProperties
		private WebElement element2;
	}

}
