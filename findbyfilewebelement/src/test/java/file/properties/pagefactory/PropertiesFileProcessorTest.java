package file.properties.pagefactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.text.ParseException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import file.pagefactory.FieldByCache;

public class PropertiesFileProcessorTest {
	
	@Test
	public void testPropertiesAnnotation() {
		Field field = mock(Field.class);
		PropertiesFileProcessor pfp = new PropertiesFileProcessor();
		assertEquals("Should return PropertiesAnnotation", PropertiesAnnotation.class, pfp.getAnnotation(field).getClass());
	}

	@Test
	public void testValidFilePathDefaultDelimiter() {
		Field field = createAndSetupPFP(new ValidFilePathDefaultDelimiterPage(),"validFileDefaultDelim");				
		assertEquals("By stored in cache is not correct.", By.id("validFileDefaultDelim"), FieldByCache.getByForField(field));
	}
	
	//Figure out which delimiters work...
	@Test
	public void testValidFilePathCustomDelimiter() {	
		Field field = createAndSetupPFP(new ValidFilePathCustomDelimiterPage(),"validFileCustomDelim");
		assertEquals("By stored in cache is not correct.", By.name("validFileCustomDelim"), FieldByCache.getByForField(field));
	}
	
	@Test
	public void testInValidFilePath() throws Exception {
		Throwable fnfe = null;
		try{
			createAndSetupPFP(new InValidFilePathPage(),"inValidFile");		
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be FileNotFoundExeption", FileNotFoundException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidPageObjectPath() throws Exception {
		Throwable fnfe = null;
		try{
			createAndSetupPFP(new InValidPageObjectPathPage(),"inValidPageObjectPath");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be ClassNotFoundException", ClassNotFoundException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidFieldName() throws Exception {
		Throwable fnfe = null;
		try{
			createAndSetupPFP(new InValidFieldNamePage(),"inValidFieldName");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be NoSuchFieldException", NoSuchFieldException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidKey() throws Exception {
		Throwable fnfe = null;
		try{
			createAndSetupPFP(new InValidKeyPage(),"inValidKey");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be ParseException", ParseException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidValue() throws Exception {
		Throwable fnfe = null;
		try{
			createAndSetupPFP(new InValidValuePage(),"inValidValue");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be ParseException", ParseException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidHow() throws Exception {
		Throwable fnfe = null;
		try{
			createAndSetupPFP(new InValidHowPage(),"inValidHow");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be IllegalArgumentException", IllegalArgumentException.class, fnfe.getClass());
	}
	
	private Field createAndSetupPFP(TestPage page, String fieldName) {
		try {
			PropertiesFileProcessor pfp = new PropertiesFileProcessor();
			Field field = page.getClass().getField(fieldName);
			pfp.populateData(field);
			return field;
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}		
	}
	
	public interface TestPage{}	
	
	@PropertiesFile(filePath = "src/test/resources/properties/ValidFilePathDefaultDelimiterData.properties")
	public class ValidFilePathDefaultDelimiterPage implements TestPage{		
		@FindByProperties
		public WebElement validFileDefaultDelim;
	}
	
	@PropertiesFile(filePath = "src/test/resources/properties/ValidFilePathCustomDelimiterData.properties", delimiter="%%")
	public class ValidFilePathCustomDelimiterPage implements TestPage{		
		@FindByProperties
		public WebElement validFileCustomDelim;
	}

	@PropertiesFile(filePath = "src/test/resources/properties/InValidFilePathData.properties")
	public class InValidFilePathPage implements TestPage{
		@FindByProperties
		public WebElement inValidFile;
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
}


