package file.json.pagefactory;

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
import file.properties.pagefactory.FindByProperties;
import file.properties.pagefactory.PropertiesFile;
import file.properties.pagefactory.PropertiesFileProcessor;
import file.properties.pagefactory.PropertiesFileProcessorTest.InValidFieldNamePage;
import file.properties.pagefactory.PropertiesFileProcessorTest.InValidFilePathPage;
import file.properties.pagefactory.PropertiesFileProcessorTest.InValidHowPage;
import file.properties.pagefactory.PropertiesFileProcessorTest.InValidPageObjectPathPage;
import file.properties.pagefactory.PropertiesFileProcessorTest.TestPage;
import file.properties.pagefactory.PropertiesFileProcessorTest.ValidFilePathDefaultDelimiterPage;

public class JsonFileProcessorTest {
	
	@Test
	public void testJsonAnnotation() {
		Field field = mock(Field.class);
		JsonFileProcessor pfp = new JsonFileProcessor();
		assertEquals("Should return JsonAnnotation", JsonAnnotation.class, pfp.getAnnotation(field).getClass());
	}

	@Test
	public void testValidFilePath() {
		Field field = createAndSetupJFP(new ValidFilePathPage(),"validFilePath");				
		assertEquals("By stored in cache is not correct.", By.id("validFilePath"), FieldByCache.getByForField(field));
	}
	
	@Test
	public void testInValidFilePath() {
		Throwable fnfe = null;
		try{
			createAndSetupJFP(new InValidFilePathPage(),"inValidFilePath");	
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be FileNotFoundExeption", FileNotFoundException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidPageObjectPath() throws Exception {
		Throwable fnfe = null;
		try{
			createAndSetupJFP(new InValidPageObjectPathPage(),"inValidPageObjectPath");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be ClassNotFoundException", ClassNotFoundException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidFieldName() throws Exception {
		Throwable fnfe = null;
		try{
			createAndSetupJFP(new InValidFieldNamePage(),"inValidFieldName");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be NoSuchFieldException", NoSuchFieldException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidHow() throws Exception {
		Throwable fnfe = null;
		try{
			createAndSetupJFP(new InValidHowPage(),"inValidHow");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be IllegalArgumentException", IllegalArgumentException.class, fnfe.getClass());
	}
	
	private Field createAndSetupJFP(TestPage page, String fieldName) {
		try {
			JsonFileProcessor pfp = new JsonFileProcessor();
			Field field = page.getClass().getField(fieldName);
			pfp.populateData(field);
			return field;
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}		
	}
	
	
	public interface TestPage{}	
	
	@JsonFile(filePath = "src/test/resources/json/ValidFilePathData.json")
	public class ValidFilePathPage implements TestPage{		
		@FindByJson
		public WebElement validFilePath;
	}
	
	@JsonFile(filePath = "src/test/resources/json/InValidFilePathData.json")
	public class InValidFilePathPage implements TestPage{
		@FindByJson
		public WebElement inValidFilePath;
	}
	
	@JsonFile(filePath = "src/test/resources/json/InValidPageObjectPathData.json")
	public class InValidPageObjectPathPage implements TestPage{
		@FindByJson
		public WebElement inValidPageObjectPath;
	}
	
	@JsonFile(filePath = "src/test/resources/json/InValidFieldNameData.json")
	public class InValidFieldNamePage implements TestPage{
		@FindByJson
		public WebElement inValidFieldName;
	}
	
	@JsonFile(filePath = "src/test/resources/json/InValidHowData.json")
	public class InValidHowPage implements TestPage{
		@FindByJson
		public WebElement inValidHow;
	}
}


