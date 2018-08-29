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
import file.properties.pagefactory.PropertiesFileProcessorTest.InValidFilePathPage;
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
}


