package file.pagefactory.json;

import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebElement;

import file.pagefactory.BaseMultipleThreadCacheTest;
import file.pagefactory.FileProcessor;
import file.pagefactory.TestPage;

public class JsonFPMultipleThreadCacheTest extends BaseMultipleThreadCacheTest{

	// Two threads of Json file processor at different time updating same
	// page object data
	@Test()
	public void twoDifferentTimeJsonFileThreadsWithSamePageObject()
			throws NoSuchFieldException, SecurityException, InterruptedException {

		// Second thread will be not enter the if condition in AbstractFileAnnotations
		// buildBy(boolean) method and return. This will not enter the
		// JsonFileProcessor
		// checkAndCallParseDataSource(Field).

		JsonFileProcessor pfp1 = Mockito.spy(JsonFileProcessor.class);
		JsonFileProcessor pfp2 = Mockito.spy(JsonFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectJsonFirst(), new PageObjectJsonFirst() };
		Integer[] checkCalls = { 1, 0 };
		Integer[] parseCalls = { 1, 0 };
		String[] annotations = { JsonAnnotation.getFindByAnnotationFullName(),
				JsonAnnotation.getFindByAnnotationFullName()};

		createThreadsAssertCache(fp, tp, false, false, checkCalls, parseCalls,annotations);
	}

	// Two threads of Json file processor at almost same time updating different
	// page object data from same file
	@Test()
	public void twoSameTimeJsonFileThreadsWithDifferentPageObjectSameFile()
			throws NoSuchFieldException, SecurityException, InterruptedException {
		

		JsonFileProcessor pfp1 = Mockito.spy(JsonFileProcessor.class);
		JsonFileProcessor pfp2 = Mockito.spy(JsonFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectJsonSecond(), new PageObjectJsonThird() };
		Integer[] checkCalls = { 1, 1 };
		Integer[] parseCalls = { 1, 1 };		
		String[] annotations = { JsonAnnotation.getFindByAnnotationFullName(),
		JsonAnnotation.getFindByAnnotationFullName()};

		createThreadsAssertCache(fp, tp, false, false, checkCalls, parseCalls,annotations);
	}
	
	
	// Two threads of Json file processor at almost same time updating different
	// page object data from different file
	@Test()
	public void twoSameTimeJsonFileThreadsWithDifferentPageObjectDifferentFile()
			throws NoSuchFieldException, SecurityException, InterruptedException {
		

		JsonFileProcessor pfp1 = Mockito.spy(JsonFileProcessor.class);
		JsonFileProcessor pfp2 = Mockito.spy(JsonFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectJsonFourth(), new PageObjectJsonFifth() };
		Integer[] checkCalls = { 1, 1 };
		Integer[] parseCalls = { 1, 1 };
		String[] annotations = { JsonAnnotation.getFindByAnnotationFullName(),
				JsonAnnotation.getFindByAnnotationFullName()};

		createThreadsAssertCache(fp, tp, false, false, checkCalls, parseCalls,annotations);
	}
	
	// Two threads of Json file processor at different time updating different
	// page object data from same file
	@Test()
	public void twoDifferentTimeJsonFileThreadsWithDifferentPageObjectSameFile()
			throws NoSuchFieldException, SecurityException, InterruptedException {
		

		JsonFileProcessor pfp1 = Mockito.spy(JsonFileProcessor.class);
		JsonFileProcessor pfp2 = Mockito.spy(JsonFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectJsonSecond(), new PageObjectJsonThird() };
		Integer[] checkCalls = { 1, 0 };
		Integer[] parseCalls = { 1, 0 };
		String[] annotations = { JsonAnnotation.getFindByAnnotationFullName(),
				JsonAnnotation.getFindByAnnotationFullName()};

		createThreadsAssertCache(fp, tp, false, false, checkCalls, parseCalls,annotations);
	}	
	
	// Two threads of Json file processor at different time updating different
	// page object data from different file
	@Test()
	public void twoDifferentTimeJsonFileThreadsWithDifferentPageObjectDifferentFile()
			throws NoSuchFieldException, SecurityException, InterruptedException {
		

		JsonFileProcessor pfp1 = Mockito.spy(JsonFileProcessor.class);
		JsonFileProcessor pfp2 = Mockito.spy(JsonFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectJsonFourth(), new PageObjectJsonFifth() };
		Integer[] checkCalls = { 1, 1 };
		Integer[] parseCalls = { 1, 1 };
		String[] annotations = { JsonAnnotation.getFindByAnnotationFullName(),
				JsonAnnotation.getFindByAnnotationFullName()};

		createThreadsAssertCache(fp, tp, false, false, checkCalls, parseCalls,annotations);
	}
	

	@JsonFile(filePath = "src/test/resources/json/ThreadPOJsonOneData.json")
	public class PageObjectJsonFirst implements TestPage {
		@FindByJson
		private WebElement element1;
		@FindByJson
		private WebElement element2;
	}

	@JsonFile(filePath = "src/test/resources/json/ThreadPOJsonTwoData.json")
	public class PageObjectJsonSecond implements TestPage {
		@FindByJson
		private WebElement element3;
		@FindByJson
		private WebElement element4;
	}

	@JsonFile(filePath = "src/test/resources/json/ThreadPOJsonTwoData.json")
	public class PageObjectJsonThird implements TestPage {
		@FindByJson
		private WebElement element5;
		@FindByJson
		private WebElement element6;
	}
	
	@JsonFile(filePath = "src/test/resources/json/ThreadPOJsonFourthData.json")
	public class PageObjectJsonFourth implements TestPage {
		@FindByJson
		private WebElement element7;
		@FindByJson
		private WebElement element8;
	}

	@JsonFile(filePath = "src/test/resources/json/ThreadPOJsonFifthData.json")
	public class PageObjectJsonFifth implements TestPage {
		@FindByJson
		private WebElement element9;
		@FindByJson
		private WebElement element10;
	}

}
