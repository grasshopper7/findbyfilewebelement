package file.pagefactory.properties;

import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebElement;

import file.pagefactory.BaseMultipleThreadCacheTest;
import file.pagefactory.FileProcessor;
import file.pagefactory.TestPage;

public class PropertiesFPMultipleThreadCacheTest extends BaseMultipleThreadCacheTest{

	// Two threads of properties file processor at same time updating same
	// page object data	
	@Test()
	public void twoSameTimePropertiesFileThreadsWithSamePageObject()
			throws NoSuchFieldException, SecurityException, InterruptedException {

		// Second thread will be not enter the if condition in AbstractFileAnnotations
		// buildBy(boolean) method and return. This will not enter the
		// PropertiesFileProcessor
		// checkAndCallParseDataSource(Field).

		PropertiesFileProcessor pfp1 = Mockito.spy(PropertiesFileProcessor.class);
		PropertiesFileProcessor pfp2 = Mockito.spy(PropertiesFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectPropertiesFirst(), new PageObjectPropertiesFirst() };
		Integer[] checkCalls = { 1, 1 };
		Integer[] parseCalls = { 1, 1 };
		String[] annotations = { PropertiesAnnotation.getFindByAnnotationFullName(),
				PropertiesAnnotation.getFindByAnnotationFullName()};

		createThreadsAssertCache(fp, tp, false, false, checkCalls, parseCalls,annotations);
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

		createThreadsAssertCache(fp, tp, false, true, checkCalls, parseCalls,annotations);
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

		createThreadsAssertCache(fp, tp, true, false, checkCalls, parseCalls,annotations);
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

		createThreadsAssertCache(fp, tp, true, false, checkCalls, parseCalls,annotations);
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

		createThreadsAssertCache(fp, tp, true, true, checkCalls, parseCalls,annotations);
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

		createThreadsAssertCache(fp, tp, true, true, checkCalls, parseCalls,annotations);
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
	
	@PropertiesFile(filePath = "src/test/resources/properties/ThreadPOPropertiesFourData.properties")
	public class PageObjectPropertiesFourth implements TestPage {
		@FindByProperties
		private WebElement element7;
		@FindByProperties
		private WebElement element8;
	}

	@PropertiesFile(filePath = "src/test/resources/properties/ThreadPOPropertiesFiveData.properties")
	public class PageObjectPropertiesFifth implements TestPage {
		@FindByProperties
		private WebElement element9;
		@FindByProperties
		private WebElement element10;
	}

}
