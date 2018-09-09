package file.pagefactory.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.text.ParseException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.gson.stream.MalformedJsonException;

import file.pagefactory.FieldByCache;
import file.pagefactory.json.FindByJson;
import file.pagefactory.json.JsonAnnotation;
import file.pagefactory.json.JsonFile;
import file.pagefactory.json.JsonFileProcessor;
import file.pagefactory.properties.FindByProperties;
import file.pagefactory.properties.PropertiesFile;
import file.pagefactory.properties.PropertiesFileProcessor;
import file.pagefactory.properties.PropertiesFileProcessorTest.InValidFieldNamePage;
import file.pagefactory.properties.PropertiesFileProcessorTest.InValidFilePathPage;
import file.pagefactory.properties.PropertiesFileProcessorTest.InValidHowPage;
import file.pagefactory.properties.PropertiesFileProcessorTest.InValidPageObjectPathPage;
import file.pagefactory.properties.PropertiesFileProcessorTest.TestPage;
import file.pagefactory.properties.PropertiesFileProcessorTest.ValidFilePathDefaultDelimiterPage;

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
	public void testInValidPageObjectPath() {
		Throwable fnfe = null;
		try{
			createAndSetupJFP(new InValidPageObjectPathPage(),"inValidPageObjectPath");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be ClassNotFoundException", ClassNotFoundException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidFieldName()  {
		Throwable fnfe = null;
		try{
			createAndSetupJFP(new InValidFieldNamePage(),"inValidFieldName");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be NoSuchFieldException", NoSuchFieldException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidHow()  {
		Throwable fnfe = null;
		try{
			createAndSetupJFP(new InValidHowPage(),"inValidHow");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be IllegalArgumentException", IllegalArgumentException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidJsonWrongClassName() {
		jsonStructureCheck(new InValidJsonWrongClassNamePage(), "className");
	}
	
	@Test
	public void testInValidJsonWrongFieldBy() {
		jsonStructureCheck(new InValidJsonWrongFieldByPage(), "fieldBy");
	}
	
	@Test
	public void testInValidJsonWrongFieldByField() {
		jsonStructureCheck(new InValidJsonWrongFieldByFieldPage(), "field");
	}
	
	@Test
	public void testInValidJsonWrongFieldByHow() {
		jsonStructureCheck(new InValidJsonWrongFieldByHowPage(), "how");
	}
	
	@Test
	public void testInValidJsonWrongFieldByUsing() {
		jsonStructureCheck(new InValidJsonWrongFieldByUsingPage(), "using");
	}
	
	private void jsonStructureCheck(TestPage page, String attribute) {
		Throwable fnfe = null;
		try{
			createAndSetupJFP(page,"inValidFileStructure");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be MalformedJsonException", MalformedJsonException.class, fnfe.getClass());
		assertEquals("Message for missing " + attribute + " attribute is not correct.", 
				attribute + " attribute is missing or misspelled.", fnfe.getMessage());
	}
	
	private Field createAndSetupJFP(TestPage page, String fieldName) {
		try {
			JsonFileProcessor jfp = new JsonFileProcessor();
			Field field = page.getClass().getField(fieldName);
			jfp.populateData(field);
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
	
	@JsonFile(filePath = "src/test/resources/json/InValidJsonWrongClassNameData.json")
	public class InValidJsonWrongClassNamePage implements TestPage{
		@FindByJson
		public WebElement inValidFileStructure;
	}
	
	@JsonFile(filePath = "src/test/resources/json/InValidJsonWrongFieldByData.json")
	public class InValidJsonWrongFieldByPage implements TestPage{
		@FindByJson
		public WebElement inValidFileStructure;
	}
	
	@JsonFile(filePath = "src/test/resources/json/InValidJsonWrongFieldByFieldData.json")
	public class InValidJsonWrongFieldByFieldPage implements TestPage{
		@FindByJson
		public WebElement inValidFileStructure;
	}
	
	@JsonFile(filePath = "src/test/resources/json/InValidJsonWrongFieldByHowData.json")
	public class InValidJsonWrongFieldByHowPage implements TestPage{
		@FindByJson
		public WebElement inValidFileStructure;
	}
	
	@JsonFile(filePath = "src/test/resources/json/InValidJsonWrongFieldByUsingData.json")
	public class InValidJsonWrongFieldByUsingPage implements TestPage{
		@FindByJson
		public WebElement inValidFileStructure;
	}
}


