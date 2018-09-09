package file.pagefactory;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import file.TestPage;
import file.excel.pagefactory.ExcelAnnotation;
import file.excel.pagefactory.ExcelFile;
import file.excel.pagefactory.ExcelFileProcessor;
import file.excel.pagefactory.FindByExcel;
import file.json.pagefactory.FindByJson;
import file.json.pagefactory.JsonAnnotation;
import file.json.pagefactory.JsonFile;
import file.json.pagefactory.JsonFileProcessor;
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
	 * - Three threads of different file processor type at almost same time updating same page object data - Check in assertvalidannot to address this.
	 * - Three threads of different file processor type at almost same time updating different page object data.
	 * 
	 * - Three threads of different file processor type at different time updating same page object data - Check in assertvalidannot to address this.
	 * - Three threads of different file processor type at different time updating different page object data.
	 * 
	 * -- OTHER TEST CASE
	 * - Three threads of different file processor type at almost same time updating different page object data with some standard @FindBy fields.
	 * - Two threads. Two PageObject. Two data file. One file has fields of one PO and some of second PO. Second file has other fields of second PO.
	 */

	@Before
	public void setup() {
		FieldByCache.removeDetails();
	}

	private void genericTwoThreads(FileProcessor[] fp, TestPage[] pages, boolean differentPageObject,
			boolean differentTime,Integer[] checkCalls, Integer[] parseCalls, String findByAnnotation) 
					throws InterruptedException {

		Thread thread1 = fileThread(fp[0], pages[0]);
		Thread thread2 = fileThread(fp[1], pages[1]);

		thread1.start();
		if (differentTime)
			Thread.sleep(1000);
		thread2.start();

		thread1.join(5000);
		thread2.join(5000);

		Mockito.verify(fp[0], Mockito.times(checkCalls[0])).checkAndCallParseDataSource(Mockito.any(Field.class));
		Mockito.verify(fp[1], Mockito.times(checkCalls[1])).checkAndCallParseDataSource(Mockito.any(Field.class));

		if (differentTime) {
			Mockito.verify(fp[0], Mockito.times(parseCalls[0])).parseDataSource();
			Mockito.verify(fp[1], Mockito.times(parseCalls[1])).parseDataSource();
		} else {
			// Need a better check to get exact counts
			Mockito.verify(fp[0], Mockito.atMost(parseCalls[0])).parseDataSource();
			Mockito.verify(fp[1], Mockito.atMost(parseCalls[1])).parseDataSource();
		}

		//checkFieldDataInCache(pages, findByAnnotation);
		
	}
	
	private void genericThreads(FileProcessor[] fp, TestPage[] pages, boolean differentPageObject,
			boolean differentTime,Integer[] checkCalls, Integer[] parseCalls, String[] findByAnnotation) 
					throws InterruptedException {
		
		Thread[] threads = new Thread[fp.length];
		
		for(int i = 0;i < fp.length;i++) {
			threads[i] = fileThread(fp[i], pages[i]);
		}
		
		threads[0].start();		
		for(int i = 1;i < threads.length;i++) {
			if(differentTime)
				Thread.sleep(1000);
			threads[i].start();
		}
		
		for(int i = 0;i < threads.length;i++) {
			threads[i].join(5000);
		}
		
		for(int i = 0;i < threads.length;i++) {
			Mockito.verify(fp[i], Mockito.times(checkCalls[i])).checkAndCallParseDataSource(Mockito.any(Field.class));
	
			if (differentTime) {
				Mockito.verify(fp[i], Mockito.times(parseCalls[i])).parseDataSource();
			} else {
				// Need a better check to get exact counts
				Mockito.verify(fp[i], Mockito.atMost(parseCalls[i])).parseDataSource();
			}	
		}
		checkFieldDataInCache(pages, findByAnnotation);

	}
	
	@SuppressWarnings("unchecked")
	private void checkFieldDataInCache(TestPage[] pages, String[] findby) {
		int count = 0;
		@SuppressWarnings("rawtypes")
		Set<Class> classDetails = new HashSet<>();
		try {
			for(int i = 0; i < pages.length ; i ++) {
				TestPage page = pages[i];
				if(classDetails.add(page.getClass())) {
					@SuppressWarnings("rawtypes")
					Class annot = Class.forName(findby[i]);
					for (Field field : page.getClass().getDeclaredFields()) {
						if (field.getAnnotation(annot) != null) {
							count++;
							Assert.assertTrue(field.getName() + " of class " + page.getClass() + " is not present in cache",
									FieldByCache.doesByExistForField(field));
						}
					}
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

		// Second thread will be waiting at the synchronized line of
		// AbstractFileAnnotations
		// buildBy(Field) method. When the second thread enters it will return from the
		// checkAndCallParseDataSource(Field) check in AbstractFileAnnotations.

		PropertiesFileProcessor pfp1 = Mockito.spy(PropertiesFileProcessor.class);
		PropertiesFileProcessor pfp2 = Mockito.spy(PropertiesFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectPropertiesFirst(), new PageObjectPropertiesFirst() };
		Integer[] checkCalls = { 1, 1 };
		Integer[] parseCalls = { 1, 1 };
		String[] annotations = { PropertiesAnnotation.getFindByAnnotationFullName(),
				PropertiesAnnotation.getFindByAnnotationFullName()};

		genericThreads(fp, tp, false, false, checkCalls, parseCalls,annotations);
	}

	// Two threads of properties file processor at different time updating same
	// page object data
	@Test()
	public void twoDifferentTimePropertiesFileThreadsWithSamePageObject()
			throws NoSuchFieldException, SecurityException, InterruptedException {

		// Second thread will be not enter the if condition in AbstractFileAnnotations
		// buildBy(boolean) method and return. This will not enter the
		// PropertiesFileProcessor
		// checkAndCallParseDataSource(Field).

		PropertiesFileProcessor pfp1 = Mockito.spy(PropertiesFileProcessor.class);
		PropertiesFileProcessor pfp2 = Mockito.spy(PropertiesFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectPropertiesFirst(), new PageObjectPropertiesFirst() };
		Integer[] checkCalls = { 1, 0 };
		Integer[] parseCalls = { 1, 0 };
		String[] annotations = { PropertiesAnnotation.getFindByAnnotationFullName(),
				PropertiesAnnotation.getFindByAnnotationFullName()};

		genericThreads(fp, tp, false, false, checkCalls, parseCalls,annotations);
	}

	// Two threads of properties file processor at almost same time updating different
	// page object data from same file
	@Test()
	public void twoSameTimePropertiesFileThreadsWithDifferentPageObjectSameFile()
			throws NoSuchFieldException, SecurityException, InterruptedException {
		

		PropertiesFileProcessor pfp1 = Mockito.spy(PropertiesFileProcessor.class);
		PropertiesFileProcessor pfp2 = Mockito.spy(PropertiesFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectPropertiesSecond(), new PageObjectPropertiesThird() };
		Integer[] checkCalls = { 1, 1 };
		Integer[] parseCalls = { 1, 1 };		
		String[] annotations = { PropertiesAnnotation.getFindByAnnotationFullName(),
		PropertiesAnnotation.getFindByAnnotationFullName()};

		genericThreads(fp, tp, false, false, checkCalls, parseCalls,annotations);
	}
	
	
	// Two threads of properties file processor at almost same time updating different
	// page object data from different file
	@Test()
	public void twoSameTimePropertiesFileThreadsWithDifferentPageObjectDifferentFile()
			throws NoSuchFieldException, SecurityException, InterruptedException {
		

		PropertiesFileProcessor pfp1 = Mockito.spy(PropertiesFileProcessor.class);
		PropertiesFileProcessor pfp2 = Mockito.spy(PropertiesFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectPropertiesFourth(), new PageObjectPropertiesFifth() };
		Integer[] checkCalls = { 1, 1 };
		Integer[] parseCalls = { 1, 1 };
		String[] annotations = { PropertiesAnnotation.getFindByAnnotationFullName(),
				PropertiesAnnotation.getFindByAnnotationFullName()};

		genericThreads(fp, tp, false, false, checkCalls, parseCalls,annotations);
	}
	
	// Two threads of properties file processor at different time updating different
	// page object data from same file
	@Test()
	public void twoDifferentTimePropertiesFileThreadsWithDifferentPageObjectSameFile()
			throws NoSuchFieldException, SecurityException, InterruptedException {
		

		PropertiesFileProcessor pfp1 = Mockito.spy(PropertiesFileProcessor.class);
		PropertiesFileProcessor pfp2 = Mockito.spy(PropertiesFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectPropertiesSecond(), new PageObjectPropertiesThird() };
		Integer[] checkCalls = { 1, 0 };
		Integer[] parseCalls = { 1, 0 };
		String[] annotations = { PropertiesAnnotation.getFindByAnnotationFullName(),
				PropertiesAnnotation.getFindByAnnotationFullName()};

		genericThreads(fp, tp, false, false, checkCalls, parseCalls,annotations);
	}	
	
	// Two threads of properties file processor at different time updating different
	// page object data from different file
	@Test()
	public void twoDifferentTimePropertiesFileThreadsWithDifferentPageObjectDifferentFile()
			throws NoSuchFieldException, SecurityException, InterruptedException {
		

		PropertiesFileProcessor pfp1 = Mockito.spy(PropertiesFileProcessor.class);
		PropertiesFileProcessor pfp2 = Mockito.spy(PropertiesFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectPropertiesFourth(), new PageObjectPropertiesFifth() };
		Integer[] checkCalls = { 1, 1 };
		Integer[] parseCalls = { 1, 1 };
		String[] annotations = { PropertiesAnnotation.getFindByAnnotationFullName(),
				PropertiesAnnotation.getFindByAnnotationFullName()};

		genericThreads(fp, tp, false, false, checkCalls, parseCalls,annotations);
	}
	
	//Three threads of different file processor type at almost same time updating 
	//different page object data.
	@Test()
	public void threeSameTimeFileProcessorThreadsWithDifferentPageObject()
			throws NoSuchFieldException, SecurityException, InterruptedException {
		

		ExcelFileProcessor pfp1 = Mockito.spy(ExcelFileProcessor.class);
		JsonFileProcessor pfp2 = Mockito.spy(JsonFileProcessor.class);
		PropertiesFileProcessor pfp3 = Mockito.spy(PropertiesFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2, pfp3 };
		TestPage[] tp = { new PageObjectExcelFirst(), new PageObjectJsonFirst(),
				new PageObjectPropertiesFirst()};
		Integer[] checkCalls = { 1, 1, 1 };
		Integer[] parseCalls = { 1, 1, 1 };
		String[] annotations = { ExcelAnnotation.getFindByAnnotationFullName(),
				JsonAnnotation.getFindByAnnotationFullName(),
				PropertiesAnnotation.getFindByAnnotationFullName()};

		genericThreads(fp, tp, true, false, checkCalls, parseCalls, annotations);
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

	@PropertiesFile(filePath = "src/test/resources/properties/ThreadPOPropertiesOneData.properties")
	public class PageObjectPropertiesFirst implements TestPage {
		@FindByProperties
		private WebElement element1;
		@FindByProperties
		private WebElement element2;
	}

	@PropertiesFile(filePath = "src/test/resources/properties/ThreadPOPropertiesTwoData.properties")
	public class PageObjectPropertiesSecond implements TestPage {
		@FindByProperties
		private WebElement element3;
		@FindByProperties
		private WebElement element4;
	}

	@PropertiesFile(filePath = "src/test/resources/properties/ThreadPOPropertiesTwoData.properties")
	public class PageObjectPropertiesThird implements TestPage {
		@FindByProperties
		private WebElement element5;
		@FindByProperties
		private WebElement element6;
	}
	
	@PropertiesFile(filePath = "src/test/resources/properties/ThreadPOPropertiesFourthData.properties")
	public class PageObjectPropertiesFourth implements TestPage {
		@FindByProperties
		private WebElement element7;
		@FindByProperties
		private WebElement element8;
	}

	@PropertiesFile(filePath = "src/test/resources/properties/ThreadPOPropertiesFifthData.properties")
	public class PageObjectPropertiesFifth implements TestPage {
		@FindByProperties
		private WebElement element9;
		@FindByProperties
		private WebElement element10;
	}

	@JsonFile(filePath = "src/test/resources/json/ThreadPOJsonOneData.json")
	public class PageObjectJsonFirst implements TestPage {
		@FindByJson
		private WebElement element1;
		@FindByJson
		private WebElement element2;
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/ThreadPOExcelOneData.xlsx")
	public class PageObjectExcelFirst implements TestPage {
		@FindByExcel
		private WebElement element1;
		@FindByExcel
		private WebElement element2;
	}
}
