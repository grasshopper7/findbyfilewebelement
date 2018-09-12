package file.pagefactory.json;

import java.lang.reflect.Field;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import file.pagefactory.AbstractFileAnnotations;
import file.pagefactory.BaseAnnotationTest;
import file.pagefactory.TestPage;
import file.pagefactory.excel.ExcelFile;
import file.pagefactory.properties.PropertiesFile;

public class JsonAnnotationTest extends BaseAnnotationTest {

	@Override
	public TestPage createValidPageObject() {
		return new PageObject();
	}
	
	@Override
	public AbstractFileAnnotations createFileAnnotation(Field field) {
		return new JsonAnnotation(field, new JsonFileProcessor());
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
		return FindByJson.class;
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
	public void testAdditionalExcelFileAnnotation() {		
		checkAdditionalFileAnnotations(new PageObjectJsonExcelFileAnnotation());
	}
	
	@Test
	public void testAdditionalPropertiesFileAnnotation() {		
		checkAdditionalFileAnnotations(new PageObjectJsonPropFileAnnotation());
	}
	
	@Test
	public void testAdditionalAllFileAnnotation() {
		checkAdditionalFileAnnotations(new PageObjectAllFileAnnotation());
	}
	
	
	@JsonFile(filePath = "src/test/resources/json/MissingElementData.json")
	public class PageObject  implements TestPage {
		@FindByJson
		private WebElement element1;
		@FindBy(id="exampleId")
		private WebElement element2;
		@FindByJson
		private WebElement elementMissData;
	}

	public class PageObjectWOFilePathAnnotation implements TestPage  {
		@FindByJson
		private WebElement element1;
	}

	@PropertiesFile(filePath="")
	@JsonFile(filePath="")
	public class PageObjectJsonPropFileAnnotation implements TestPage  {
		@FindByJson
		private WebElement element1;
	}

	@ExcelFile(filePath="")
	@JsonFile(filePath="")
	public class PageObjectJsonExcelFileAnnotation implements TestPage  {
		@FindByJson
		private WebElement element1;
	}
	
	@PropertiesFile(filePath="")
	@JsonFile(filePath="")
	@ExcelFile(filePath="")
	public class PageObjectAllFileAnnotation implements TestPage  {
		@FindByJson
		private WebElement element1;
	}
}
