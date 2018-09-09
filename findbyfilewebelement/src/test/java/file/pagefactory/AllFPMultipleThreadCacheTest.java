package file.pagefactory;

import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebElement;

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

		createThreadsAssertCache(fp, tp, true, false, checkCalls, parseCalls, annotations);
	}


	@PropertiesFile(filePath = "src/test/resources/properties/ThreadPOPropertiesOneData.properties")
	public class PageObjectPropertiesFirst implements TestPage {
		@FindByProperties
		private WebElement element1;
		@FindByProperties
		private WebElement element2;
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
