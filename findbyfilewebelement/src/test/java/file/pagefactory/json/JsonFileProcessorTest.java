package file.pagefactory.json;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import com.google.gson.stream.MalformedJsonException;

import file.pagefactory.BaseFileProcessorTest;
import file.pagefactory.FileProcessor;
import file.pagefactory.TestPage;

public class JsonFileProcessorTest extends BaseFileProcessorTest {
	
	@Override
	public FileProcessor createFileProcessor() {
		return new JsonFileProcessor();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class getRequisiteAnnotation() {
		return JsonAnnotation.class;
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
			createAndSetupFileProcessor(page,"inValidFileStructure");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be MalformedJsonException", MalformedJsonException.class, fnfe.getClass());
		assertEquals("Message for missing " + attribute + " attribute is not correct.", 
				attribute + " attribute is missing or misspelled.", fnfe.getMessage());
	}
	
		
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


