package file.excel.pagefactory;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import file.TestPage;
import file.pagefactory.FieldByCache;


public class ExcelFileMultiplePageTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ExcelFileProcessor efp = new ExcelFileProcessor();
		FirstMultiplePage fmp = new FirstMultiplePage();
		Field field = fmp.getClass().getField("secondField");
		efp.populateData(field);		
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
		FieldByCache.printCache();
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
	public void testSamePONameFilledAndBlank() throws Exception {
		//ThirdMultiplePage PO class is mentioned in 4th and 5th row.
		ThirdMultiplePage tmp = new ThirdMultiplePage();
		Field field1 = tmp.getClass().getField("firstField");
		Field field2 = tmp.getClass().getField("secondField");
		
		assertTrue("firstField of ThirdMultiplePage PO is not present in cache.", FieldByCache.doesByExistForField(field1));
		assertTrue("secondField of ThirdMultiplePage PO is not present in cache.", FieldByCache.doesByExistForField(field2));
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
	
	
	@ExcelFile(filePath = "src/test/resources/excel/ValidMultiplePageData.xlsx")
	public static class FirstMultiplePage implements TestPage{		
		@FindByExcel
		public WebElement firstField;
		@FindByExcel
		public List<WebElement> secondField;		
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/ValidMultiplePageData.xlsx")
	public static class SecondMultiplePage implements TestPage{		
		@FindByExcel
		public WebElement thirdField;		
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/ValidMultiplePageData.xlsx")
	public static class ThirdMultiplePage implements TestPage{		
		@FindByExcel
		public WebElement firstField;
		@FindByExcel
		public List<WebElement> secondField;
		@FindByExcel
		public WebElement fourthField;
	}

}
