package file.pagefactory.excel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import file.pagefactory.FieldByCache;
import file.pagefactory.TestPage;
import file.pagefactory.excel.ExcelAnnotation;
import file.pagefactory.excel.ExcelFile;
import file.pagefactory.excel.ExcelFileProcessor;
import file.pagefactory.excel.FindByExcel;

public class ExcelFileProcessorTest {
	
	@Test
	public void testExcelAnnotation() {
		Field field = mock(Field.class);
		ExcelFileProcessor pfp = new ExcelFileProcessor();
		assertEquals("Should return ExcelAnnotation", ExcelAnnotation.class, pfp.getAnnotation(field).getClass());
	}

	@Test
	public void testValidFilePath() {
		Field field = createAndSetupEFP(new ValidFilePathPage(),"validFilePath");				
		assertEquals("By stored in cache is not correct.", By.id("validFilePath"), FieldByCache.getByForField(field));
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
	public void testInValidFilePath() {
		Throwable fnfe = null;
		try{
			createAndSetupEFP(new InValidFilePathPage(),"inValidFilePath");	
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be FileNotFoundExeption", FileNotFoundException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidPageObjectPath() {
		Throwable fnfe = null;
		try{
			createAndSetupEFP(new InValidPageObjectPathPage(),"inValidPageObjectPath");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be ClassNotFoundException", ClassNotFoundException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidFieldName()  {
		Throwable fnfe = null;
		try{
			createAndSetupEFP(new InValidFieldNamePage(),"inValidFieldName");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be NoSuchFieldException", NoSuchFieldException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidHow()  {
		Throwable fnfe = null;
		try{
			createAndSetupEFP(new InValidHowPage(),"inValidHow");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be IllegalArgumentException", IllegalArgumentException.class, fnfe.getClass());
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


