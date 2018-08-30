package file.properties.pagefactory;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import file.TestPage;
import file.pagefactory.FieldByCache;

public class PropertiesFileProcessorMultiplePageTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PropertiesFileProcessor efp = new PropertiesFileProcessor();
		FirstMultiplePage fmp = new FirstMultiplePage();
		Field field = fmp.getClass().getField("secondField");
		efp.populateData(field);		
	}
		
	@Test
	public void testCacheSize() {
		assertEquals("Number of fields in cache should be 6.", 6, FieldByCache.size());
	}
	
	@Test
	public void testConsecutiveSamePONameFilled() throws Exception {
		//FirstMultiplePage PO class is mentioned in 2nd and 3rd row.
		FirstMultiplePage fmp = new FirstMultiplePage();
		Field field1 = fmp.getClass().getField("firstField");
		Field field2 = fmp.getClass().getField("secondField");
		
		assertTrue("firstField of FirstMultiplePage PO is not present in cache.", FieldByCache.doesByExistForField(field1));
		assertTrue("secondField of FirstMultiplePage PO is not present in cache.", FieldByCache.doesByExistForField(field2));
	}
	
	@Test
	public void testSamePONameSplitAmongRows() throws Exception {
		//ThirdMultiplePage PO class is mentioned in 4th, 5th and 7th row.
		ThirdMultiplePage tmp = new ThirdMultiplePage();
		Field field1 = tmp.getClass().getField("firstField");
		Field field2 = tmp.getClass().getField("secondField");
		Field field3 = tmp.getClass().getField("fourthField");
	
		assertTrue("firstField of ThirdMultiplePage PO is not present in cache.", FieldByCache.doesByExistForField(field1));
		assertTrue("secondField of ThirdMultiplePage PO is not present in cache.", FieldByCache.doesByExistForField(field2));
		assertTrue("fourthField of ThirdMultiplePage PO is not present in cache.", FieldByCache.doesByExistForField(field3));
	}
	
	
	@PropertiesFile(filePath = "src/test/resources/properties/ValidMultiplePageData.properties")
	public static class FirstMultiplePage implements TestPage{		
		@FindByProperties
		public WebElement firstField;
		@FindByProperties
		public List<WebElement> secondField;		
	}
	
	@PropertiesFile(filePath = "src/test/resources/properties/ValidMultiplePageData.properties")
	public static class SecondMultiplePage implements TestPage{		
		@FindByProperties
		public WebElement thirdField;		
	}
	
	@PropertiesFile(filePath = "src/test/resources/properties/ValidMultiplePageData.properties")
	public static class ThirdMultiplePage implements TestPage{		
		@FindByProperties
		public WebElement firstField;
		@FindByProperties
		public List<WebElement> secondField;
		@FindByProperties
		public WebElement fourthField;
	}

}
