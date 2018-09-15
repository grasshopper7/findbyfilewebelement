package file.pagefactory.properties;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.text.ParseException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import file.pagefactory.BaseFileProcessorTest;
import file.pagefactory.FieldByCache;
import file.pagefactory.FileProcessor;
import file.pagefactory.TestPage;

public class PropertiesFileProcessorTest extends BaseFileProcessorTest {
	
	@Override
	public FileProcessor createFileProcessor() {
		return new PropertiesFileProcessor();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class getRequisiteAnnotation() {
		return PropertiesAnnotation.class;
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
	
	@Override
	public TestPage createInValidMissingFileAnnotationPage() {
		return new InValidMissingFileAnnotationPage();
	}
	
	
	//Figure out which delimiters work...
	@Test
	public void testValidFilePathCustomDelimiter() {	
		Field field = createAndSetupFileProcessor(new ValidFilePathCustomDelimiterPage(),"validFileCustomDelim");
		assertEquals("By stored in cache is not correct.", By.name("validFileCustomDelim"), FieldByCache.getByForField(field));
	}
	
	@Test
	public void testInValidKey(){
		Throwable fnfe = null;
		try{
			createAndSetupFileProcessor(new InValidKeyPage(),"inValidKey");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be ParseException", ParseException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidValue()  {
		Throwable fnfe = null;
		try{
			createAndSetupFileProcessor(new InValidValuePage(),"inValidValue");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be ParseException", ParseException.class, fnfe.getClass());
	}
	
	
	@PropertiesFile(filePath = "src/test/resources/properties/ValidFilePathDefaultDelimiterData.properties")
	public class ValidFilePathPage implements TestPage{		
		@FindByProperties
		public WebElement validFilePath;
	}
	
	@PropertiesFile(filePath = "src/test/resources/properties/ValidFilePathCustomDelimiterData.properties", delimiter="%%")
	public class ValidFilePathCustomDelimiterPage implements TestPage{		
		@FindByProperties
		public WebElement validFileCustomDelim;
	}

	@PropertiesFile(filePath = "src/test/resources/properties/InValidFilePathData.properties")
	public class InValidFilePathPage implements TestPage{
		@FindByProperties
		public WebElement inValidFilePath;
	}
	
	@PropertiesFile(filePath = "src/test/resources/properties/InValidPageObjectPathData.properties")
	public class InValidPageObjectPathPage implements TestPage{
		@FindByProperties
		public WebElement inValidPageObjectPath;
	}
	
	@PropertiesFile(filePath = "src/test/resources/properties/InValidFieldNameData.properties")
	public class InValidFieldNamePage implements TestPage{
		@FindByProperties
		public WebElement inValidFieldName;
	}
	
	@PropertiesFile(filePath = "src/test/resources/properties/InValidKeyData.properties")
	public class InValidKeyPage implements TestPage{
		@FindByProperties
		public WebElement inValidKey;
	}
	
	@PropertiesFile(filePath = "src/test/resources/properties/InValidValueData.properties")
	public class InValidValuePage implements TestPage{
		@FindByProperties
		public WebElement inValidValue;
	}
	
	@PropertiesFile(filePath = "src/test/resources/properties/InValidHowData.properties")
	public class InValidHowPage implements TestPage{
		@FindByProperties
		public WebElement inValidHow;
	}
	
	public class InValidMissingFileAnnotationPage implements TestPage{
		@FindByProperties
		public WebElement inValidMissingFileAnnotation;
	}
}