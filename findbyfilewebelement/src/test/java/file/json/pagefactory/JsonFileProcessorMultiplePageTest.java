package file.json.pagefactory;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import file.TestPage;
import file.pagefactory.FieldByCache;

public class JsonFileProcessorMultiplePageTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		JsonFileProcessor efp = new JsonFileProcessor();
		FirstMultiplePage fmp = new FirstMultiplePage();
		Field field = fmp.getClass().getField("secondField");
		efp.populateData(field);		
	}
		
	@Test
	public void testCacheSize() {
		assertEquals("Number of fields in cache should be 6.", 6, FieldByCache.size());
	}
	
	@Test
	public void testTogetherSamePOFields() throws Exception {
		FirstMultiplePage fmp = new FirstMultiplePage();
		Field field1 = fmp.getClass().getField("firstField");
		Field field2 = fmp.getClass().getField("secondField");
		
		assertTrue("firstField of FirstMultiplePage PO is not present in cache.", FieldByCache.doesByExistForField(field1));
		assertTrue("secondField of FirstMultiplePage PO is not present in cache.", FieldByCache.doesByExistForField(field2));
	}
	
	@Test
	public void testSplitSamePOFields() throws Exception {
		ThirdMultiplePage tmp = new ThirdMultiplePage();
		Field field1 = tmp.getClass().getField("firstField");
		Field field2 = tmp.getClass().getField("secondField");
		Field field3 = tmp.getClass().getField("fourthField");
	
		assertTrue("firstField of ThirdMultiplePage PO is not present in cache.", FieldByCache.doesByExistForField(field1));
		assertTrue("secondField of ThirdMultiplePage PO is not present in cache.", FieldByCache.doesByExistForField(field2));
		assertTrue("fourthField of ThirdMultiplePage PO is not present in cache.", FieldByCache.doesByExistForField(field3));
	}
	
	
	@JsonFile(filePath = "src/test/resources/json/ValidMultiplePageData.json")
	public static class FirstMultiplePage implements TestPage{		
		@FindByJson
		public WebElement firstField;
		@FindByJson
		public List<WebElement> secondField;		
	}
	
	@JsonFile(filePath = "src/test/resources/json/ValidMultiplePageData.json")
	public static class SecondMultiplePage implements TestPage{		
		@FindByJson
		public WebElement thirdField;		
	}
	
	@JsonFile(filePath = "src/test/resources/json/ValidMultiplePageData.json")
	public static class ThirdMultiplePage implements TestPage{		
		@FindByJson
		public WebElement firstField;
		@FindByJson
		public List<WebElement> secondField;
		@FindByJson
		public WebElement fourthField;
	}

}
