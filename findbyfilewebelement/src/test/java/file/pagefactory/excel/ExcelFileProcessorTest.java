package file.pagefactory.excel;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import file.pagefactory.BaseFileProcessorTest;
import file.pagefactory.FieldByCache;
import file.pagefactory.FileProcessor;
import file.pagefactory.TestPage;

public class ExcelFileProcessorTest extends BaseFileProcessorTest {
	
	@Override
	public FileProcessor createFileProcessor() {
		return new ExcelFileProcessor();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class getRequisiteAnnotation() {
		return ExcelAnnotation.class;
	}
	
	@Override
	public TestPage createValidFilePathPage() {
		return new ValidFilePathPage();
	}

	@Override
	public TestPage createInValidFilePathPage() {
		return new InValidFilePathPage();
	}

	@Override
	public TestPage createInValidPageObjectPathPage() {
		return new InValidPageObjectPathPage();
	}

	@Override
	public TestPage createInValidFieldNamePage() {
		return new InValidFieldNamePage();
	}
	
	@Override
	public TestPage createInValidHowPage() {
		return new InValidHowPage();
	}
	
	@Test
	public void testValidFilePathCustomSheet() {
		Field field = createAndSetupEFP(new ValidFilePathCustomSheetPage(),"validFilePathCustomSheet");				
		assertEquals("By stored in cache is not correct.", By.name("validFilePathCustomSheet"), FieldByCache.getByForField(field));
	}
	
	@Test
	public void testValidFileInvalidHeader() {
		Field field = createAndSetupEFP(new ValidFileInvalidHeadersPage(),"validFileInvalidHeader");				
		assertEquals("By stored in cache is not correct.", By.id("validFileInvalidHeader"), FieldByCache.getByForField(field));
	}
	
	@Test
	public void testMissingFieldName()  {
		Throwable fnfe = null;
		try{
			createAndSetupEFP(new MissingFieldNamePage(),"missingFieldName");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be IllegalArgumentException", IllegalArgumentException.class, fnfe.getClass());
	}
	
	@Test
	public void testMissingHow()  {
		Throwable fnfe = null;
		try{
			createAndSetupEFP(new MissingHowPage(),"missingHow");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be IllegalArgumentException", IllegalArgumentException.class, fnfe.getClass());
	}
	
	@Test
	public void testMissingUsing()  {
		Throwable fnfe = null;
		try{
			createAndSetupEFP(new MissingUsingPage(),"missingUsing");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be IllegalArgumentException", IllegalArgumentException.class, fnfe.getClass());
	}
	
	private Field createAndSetupEFP(TestPage page, String fieldName) {
		try {
			ExcelFileProcessor efp = new ExcelFileProcessor();
			Field field = page.getClass().getField(fieldName);
			efp.populateData(field);
			return field;
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}		
	}
		
	
	@ExcelFile(filePath = "src/test/resources/excel/ValidFilePathData.xlsx")
	public class ValidFilePathPage implements TestPage{		
		@FindByExcel
		public WebElement validFilePath;
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/ValidFilePathCustomSheetData.xlsx", sheetName="FieldDetails")
	public class ValidFilePathCustomSheetPage implements TestPage{		
		@FindByExcel
		public WebElement validFilePathCustomSheet;
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/ValidFileInvalidHeadersData.xlsx")
	public class ValidFileInvalidHeadersPage implements TestPage{		
		@FindByExcel
		public WebElement validFileInvalidHeader;
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/InValidFilePathData.xlsx")
	public class InValidFilePathPage implements TestPage{
		@FindByExcel
		public WebElement inValidFilePath;
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/InValidPageObjectPathData.xlsx")
	public class InValidPageObjectPathPage implements TestPage{
		@FindByExcel
		public WebElement inValidPageObjectPath;
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/InValidFieldNameData.xlsx")
	public class InValidFieldNamePage implements TestPage{
		@FindByExcel
		public WebElement inValidFieldName;
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/InValidHowData.xlsx")
	public class InValidHowPage implements TestPage{
		@FindByExcel
		public WebElement inValidHow;
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/MissingFieldNameData.xlsx")
	public class MissingFieldNamePage implements TestPage{
		@FindByExcel
		public WebElement missingFieldName;
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/MissingHowData.xlsx")
	public class MissingHowPage implements TestPage{
		@FindByExcel
		public WebElement missingHow;
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/MissingUsingData.xlsx")
	public class MissingUsingPage implements TestPage{
		@FindByExcel
		public WebElement missingUsing;
	}
}


