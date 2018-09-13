package file.pagefactory.properties;

import static org.junit.Assert.fail;

import java.lang.reflect.Field;

import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import file.pagefactory.AbstractFileAnnotations;
import file.pagefactory.BaseAnnotationTest;
import file.pagefactory.TestPage;
import file.pagefactory.excel.ExcelFile;
import file.pagefactory.json.JsonFile;

public class PropertiesAnnotationTest extends BaseAnnotationTest{

	@Override
	public TestPage createValidPageObject() {
		return new PageObject();
	}
	
	@Override
	public AbstractFileAnnotations createFileAnnotation(Field field) {
		return new PropertiesAnnotation(field, new PropertiesFileProcessor());
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
		return FindByProperties.class;
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
		checkAdditionalFileAnnotations(new PageObjectPropExcelFileAnnotation());
	}
	
	@Test
	public void testAdditionalJsonFileAnnotation() {		
		checkAdditionalFileAnnotations(new PageObjectPropJsonFileAnnotation());
	}
	
	@Test
	public void testAdditionalAllFileAnnotation() {
		checkAdditionalFileAnnotations(new PageObjectAllFileAnnotation());
	}

	
	@PropertiesFile(filePath = "src/test/resources/properties/MissingElementData.properties")
	public class PageObject implements TestPage{
		@FindByProperties
		private WebElement element1;
		@FindBy(id="exampleId")
		private WebElement element2;
		@FindByProperties
		private WebElement elementMissData;
	}

	public class PageObjectWOFilePathAnnotation implements TestPage {
		@FindByProperties
		private WebElement element1;
	}
	
	@PropertiesFile(filePath="")
	@ExcelFile(filePath="")
	public class PageObjectPropExcelFileAnnotation implements TestPage {
		@FindByProperties
		private WebElement element1;
	}

	@PropertiesFile(filePath="")
	@JsonFile(filePath="")
	public class PageObjectPropJsonFileAnnotation implements TestPage {
		@FindByProperties
		private WebElement element1;
	}
	
	@PropertiesFile(filePath="")
	@JsonFile(filePath="")
	@ExcelFile(filePath="")
	public class PageObjectAllFileAnnotation implements TestPage {
		@FindByProperties
		private WebElement element1;
	}
}
