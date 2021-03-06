package file.pagefactory.json;

import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebElement;

import file.pagefactory.BaseMultipleThreadCacheTest;
import file.pagefactory.FileProcessor;
import file.pagefactory.TestPage;

public class JsonFPMultipleThreadCacheTest extends BaseMultipleThreadCacheTest{

	// Two threads of properties file processor at same time updating same
	// page object data	
	@Test()
	public void twoSameTimeJsonFileThreadsWithSamePageObject() {

		// Second thread will be not enter the if condition in AbstractFileAnnotations
		// buildBy(boolean) method and return. This will not enter the
		// JsonFileProcessor
		// checkAndCallParseDataSource(Field).

		JsonFileProcessor pfp1 = Mockito.spy(JsonFileProcessor.class);
		JsonFileProcessor pfp2 = Mockito.spy(JsonFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectJsonFirst(), new PageObjectJsonFirst() };
		Integer[] checkCalls = { 1, 1 };
		Integer[] parseCalls = { 1, 1 };
		String[] annotations = { JsonAnnotation.getFindByAnnotationFullName(),
				JsonAnnotation.getFindByAnnotationFullName()};

		createThreadsAssertCache(fp, tp, false, false, checkCalls, parseCalls,annotations);
	}	
	
	// Two threads of Json file processor at different time updating same
	// page object data
	@Test()
	public void twoDifferentTimeJsonFileThreadsWithSamePageObject() {

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

		createThreadsAssertCache(fp, tp, false, true, checkCalls, parseCalls,annotations);
	}

	// Two threads of Json file processor at almost same time updating different
	// page object data from same file
	@Test()
	public void twoSameTimeJsonFileThreadsWithDifferentPageObjectSameFile() {
		
		JsonFileProcessor pfp1 = Mockito.spy(JsonFileProcessor.class);
		JsonFileProcessor pfp2 = Mockito.spy(JsonFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectJsonSecond(), new PageObjectJsonThird() };
		Integer[] checkCalls = { 1, 1 };
		Integer[] parseCalls = { 1, 1 };		
		String[] annotations = { JsonAnnotation.getFindByAnnotationFullName(),
		JsonAnnotation.getFindByAnnotationFullName()};

		createThreadsAssertCache(fp, tp, true, false, checkCalls, parseCalls,annotations);
	}
	
	
	// Two threads of Json file processor at almost same time updating different
	// page object data from different file
	@Test()
	public void twoSameTimeJsonFileThreadsWithDifferentPageObjectDifferentFile() {
		
		JsonFileProcessor pfp1 = Mockito.spy(JsonFileProcessor.class);
		JsonFileProcessor pfp2 = Mockito.spy(JsonFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectJsonFourth(), new PageObjectJsonFifth() };
		Integer[] checkCalls = { 1, 1 };
		Integer[] parseCalls = { 1, 1 };
		String[] annotations = { JsonAnnotation.getFindByAnnotationFullName(),
				JsonAnnotation.getFindByAnnotationFullName()};

		createThreadsAssertCache(fp, tp, true, false, checkCalls, parseCalls,annotations);
	}
	
	// Two threads of Json file processor at different time updating different
	// page object data from same file
	@Test()
	public void twoDifferentTimeJsonFileThreadsWithDifferentPageObjectSameFile() {
		
		JsonFileProcessor pfp1 = Mockito.spy(JsonFileProcessor.class);
		JsonFileProcessor pfp2 = Mockito.spy(JsonFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectJsonSecond(), new PageObjectJsonThird() };
		Integer[] checkCalls = { 1, 0 };
		Integer[] parseCalls = { 1, 0 };
		String[] annotations = { JsonAnnotation.getFindByAnnotationFullName(),
				JsonAnnotation.getFindByAnnotationFullName()};

		createThreadsAssertCache(fp, tp, true, true, checkCalls, parseCalls,annotations);
	}	
	
	// Two threads of Json file processor at different time updating different
	// page object data from different file
	@Test()
	public void twoDifferentTimeJsonFileThreadsWithDifferentPageObjectDifferentFile() {
		
		JsonFileProcessor pfp1 = Mockito.spy(JsonFileProcessor.class);
		JsonFileProcessor pfp2 = Mockito.spy(JsonFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectJsonFourth(), new PageObjectJsonFifth() };
		Integer[] checkCalls = { 1, 1 };
		Integer[] parseCalls = { 1, 1 };
		String[] annotations = { JsonAnnotation.getFindByAnnotationFullName(),
				JsonAnnotation.getFindByAnnotationFullName()};

		createThreadsAssertCache(fp, tp, true, true, checkCalls, parseCalls,annotations);
	}
	

	@JsonFile(filePath = "src/test/resources/json/ThreadPOJsonOneData.json")
	public static class PageObjectJsonFirst implements TestPage {
		@FindByJson
		private WebElement element1;
		@FindByJson
		private WebElement element2;
	}

	@JsonFile(filePath = "src/test/resources/json/ThreadPOJsonTwoData.json")
	public static class PageObjectJsonSecond implements TestPage {
		@FindByJson
		private WebElement element3;
		@FindByJson
		private WebElement element4;
	}

	@JsonFile(filePath = "src/test/resources/json/ThreadPOJsonTwoData.json")
	public static class PageObjectJsonThird implements TestPage {
		@FindByJson
		private WebElement element5;
		@FindByJson
		private WebElement element6;
	}
	
	@JsonFile(filePath = "src/test/resources/json/ThreadPOJsonFourData.json")
	public static class PageObjectJsonFourth implements TestPage {
		@FindByJson
		private WebElement element7;
		@FindByJson
		private WebElement element8;
	}

	@JsonFile(filePath = "src/test/resources/json/ThreadPOJsonFiveData.json")
	public static class PageObjectJsonFifth implements TestPage {
		@FindByJson
		private WebElement element9;
		@FindByJson
		private WebElement element10;
	}

}
