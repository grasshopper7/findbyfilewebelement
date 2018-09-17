package file.pagefactory;

import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import file.pagefactory.excel.ExcelAnnotation;
import file.pagefactory.excel.ExcelFile;
import file.pagefactory.excel.ExcelFileProcessor;
import file.pagefactory.excel.FindByExcel;
import file.pagefactory.json.FindByJson;
import file.pagefactory.json.JsonAnnotation;
import file.pagefactory.json.JsonFile;
import file.pagefactory.json.JsonFileProcessor;
import file.pagefactory.properties.FindByProperties;
import file.pagefactory.properties.PropertiesAnnotation;
import file.pagefactory.properties.PropertiesFile;
import file.pagefactory.properties.PropertiesFileProcessor;

public class AllFPMultipleThreadCacheTest extends BaseMultipleThreadCacheTest{
	
	
	//Three threads of different file processor type at almost same time updating 
	//different page object data.
	@Test
	public void sameTimeAllFileProcessorThreadsWithDifferentPageObject()
			throws NoSuchFieldException, SecurityException, InterruptedException {
		
		executeAllFPThreads(false);
	}
	
	//Three threads of different file processor type at different time updating 
	//different page object data.
	@Test
	public void differentTimeAllFileProcessorThreadsWithDifferentPageObject()
			throws NoSuchFieldException, SecurityException, InterruptedException {
	
		executeAllFPThreads(true);
	}

	 
	private void executeAllFPThreads(boolean time) throws InterruptedException {
		
		ExcelFileProcessor pfp1 = Mockito.spy(ExcelFileProcessor.class);
		JsonFileProcessor pfp2 = Mockito.spy(JsonFileProcessor.class);
		PropertiesFileProcessor pfp3 = Mockito.spy(PropertiesFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2, pfp3 };
		TestPage[] tp = { new CommonValidPageObjects.PageObjectExcel(), 
				new CommonValidPageObjects.PageObjectJson(),
				new CommonValidPageObjects.PageObjectProperties()};
		Integer[] checkCalls = { 1, 1, 1 };
		Integer[] parseCalls = { 1, 1, 1 };
		String[] annotations = { ExcelAnnotation.getFindByAnnotationFullName(),
				JsonAnnotation.getFindByAnnotationFullName(),
				PropertiesAnnotation.getFindByAnnotationFullName()};

		createThreadsAssertCache(fp, tp, true, time, checkCalls, parseCalls, annotations);
	}

/*	@PropertiesFile(filePath = "src/test/resources/properties/MultipleThreadPOPropertiesData.properties")
	public static class PageObjectPropertiesFirst implements TestPage {
		@FindByProperties
		private WebElement element1;
		@FindByProperties
		private List<WebElement> element2;
		@FindBy
		private WebElement element3;
		private Object element4;
		private List<Object> element5;
	}

	@JsonFile(filePath = "src/test/resources/json/MultipleThreadPOJsonData.json")
	public static class PageObjectJsonFirst implements TestPage {
		@FindByJson
		private WebElement element1;
		@FindByJson
		private List<WebElement> element2;
		@FindBy
		private WebElement element3;
		private Object element4;
		private List<Object> element5;
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/MultipleThreadPOExcelData.xlsx")
	public static class PageObjectExcelFirst implements TestPage {
		@FindByExcel
		private WebElement element1;
		@FindByExcel
		private List<WebElement> element2;
		@FindBy
		private WebElement element3;
		private Object element4;
		private List<Object> element5;
	}*/
}
