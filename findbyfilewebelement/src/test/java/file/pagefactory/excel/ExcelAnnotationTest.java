package file.pagefactory.excel;

import java.lang.reflect.Field;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import file.pagefactory.AbstractFileAnnotations;
import file.pagefactory.BaseAnnotationTest;
import file.pagefactory.TestPage;
import file.pagefactory.excel.ExcelFile;
import file.pagefactory.json.JsonFile;
import file.pagefactory.properties.PropertiesFile;

public class ExcelAnnotationTest extends BaseAnnotationTest {

	@Override
	public TestPage createValidPageObject() {
		return new PageObject();
	}
	
	@Override
	public AbstractFileAnnotations createFileAnnotation(Field field) {
		return new ExcelAnnotation(field, new ExcelFileProcessor());
	}
	
	@Override
	public Field createValidField() {
		return createField(createValidPageObject(), "element1");
	}

	@Override
	public Field createValidSpyField() {
		return createSpyField(createValidPageObject(), "element1");
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class getFileAnnotationsClass() {
		return FindByExcel.class;
	}

	
	@Test
	public void testMissingFilePathAnnotation() {
		checkMissingFilePathAnnotation(new PageObjectWOFilePathAnnotation());
	}

	@Test
	public void testMissingBothAnnotations() {
		checkMissingBothAnnotations(new PageObjectWOFilePathAnnotation());
	}
		
	@Test
	public void testAdditionalJsonFileAnnotation() {		
		checkAdditionalFileAnnotations(new PageObjectExcelJsonFileAnnotation());
	}
	
	@Test
	public void testAdditionalPropertiesFileAnnotation() {		
		checkAdditionalFileAnnotations(new PageObjectExcelPropFileAnnotation());
	}
	
	@Test
	public void testAdditionalAllFileAnnotation() {
		checkAdditionalFileAnnotations(new PageObjectAllFileAnnotation());
	}
	
	
	@ExcelFile(filePath = "src/test/resources/excel/MissingElementData.xlsx")
	public class PageObject implements TestPage{
		@FindByExcel
		private WebElement element1;
		@FindBy(id="exampleId")
		private WebElement element2;
		@FindByExcel
		private WebElement elementMissData;
	}

	public class PageObjectWOFilePathAnnotation implements TestPage {
		@FindByExcel
		private WebElement element1;
	}

	@PropertiesFile(filePath="")
	@ExcelFile(filePath="")
	public class PageObjectExcelPropFileAnnotation implements TestPage {
		@FindByExcel
		private WebElement element1;
	}

	@ExcelFile(filePath="")
	@JsonFile(filePath="")
	public class PageObjectExcelJsonFileAnnotation implements TestPage {
		@FindByExcel
		private WebElement element1;
	}
	
	@PropertiesFile(filePath="")
	@JsonFile(filePath="")
	@ExcelFile(filePath="")
	public class PageObjectAllFileAnnotation implements TestPage {
		@FindByExcel
		private WebElement element1;
	}
}
