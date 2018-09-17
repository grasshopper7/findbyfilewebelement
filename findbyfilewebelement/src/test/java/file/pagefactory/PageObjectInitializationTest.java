package file.pagefactory;

import org.junit.Test;

import file.pagefactory.excel.ExcelAnnotation;
import file.pagefactory.excel.ExcelFileProcessor;
import file.pagefactory.json.JsonAnnotation;
import file.pagefactory.json.JsonFileProcessor;
import file.pagefactory.properties.PropertiesAnnotation;
import file.pagefactory.properties.PropertiesFileProcessor;

public class PageObjectInitializationTest extends BaseMultipleThreadCacheTest {
	
	@Test
	public void propertiesFilePageObjectInitialization() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		TestPage tp = new CommonValidPageObjects.PageObjectProperties();
		FileProcessor pfp = new PropertiesFileProcessor();
		initializePage(pfp, tp);
		checkFieldData(tp, Class.forName(PropertiesAnnotation.getFindByAnnotationFullName()));
	}
	
	@Test
	public void jsonFilePageObjectInitialization() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		TestPage tp = new CommonValidPageObjects.PageObjectJson();
		FileProcessor jfp = new JsonFileProcessor();
		initializePage(jfp, tp);
		checkFieldData(tp, Class.forName(JsonAnnotation.getFindByAnnotationFullName()));
	}
	
	@Test
	public void excelFilePageObjectInitialization() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		TestPage tp = new CommonValidPageObjects.PageObjectExcel();
		FileProcessor efp = new ExcelFileProcessor();
		initializePage(efp, tp);
		checkFieldData(tp, Class.forName(ExcelAnnotation.getFindByAnnotationFullName()));
	}

}
