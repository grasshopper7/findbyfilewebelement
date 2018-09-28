package file.pagefactory;

import org.junit.Assert;
import org.junit.Test;

import file.pagefactory.excel.ExcelAnnotation;
import file.pagefactory.excel.ExcelFileProcessor;
import file.pagefactory.json.JsonAnnotation;
import file.pagefactory.json.JsonFileProcessor;
import file.pagefactory.properties.PropertiesAnnotation;
import file.pagefactory.properties.PropertiesFileProcessor;

//Allow inheritance across file types. 
//Abstract PropertiesFile, JsonFile, ExcelFile to FileLocation annotation

public class InheritedPageObjectInitializationTest extends BaseMultipleThreadCacheTest {
	
	private void commonCheck(TestPage tp, FileProcessor fp, String annotCls) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		initializePage(fp, tp);
		Assert.assertEquals("Locator Data has not been parsed properly.", 
				checkFieldData(tp, Class.forName(annotCls)), FieldByCache.size());
	}
	
	
	@Test
	public void normalInheritancePropertiesPOInit() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		commonCheck(new InheritedValidPageObjects.NormalInheritancePropertiesPageObject(), 
				new PropertiesFileProcessor(), PropertiesAnnotation.getFindByAnnotationFullName());
	}
	
	@Test
	public void normalInheritanceJsonPOInit() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		commonCheck(new InheritedValidPageObjects.NormalInheritanceJsonPageObject(), 
				new JsonFileProcessor(), JsonAnnotation.getFindByAnnotationFullName());
	}
	
	@Test
	public void normalInheritanceExcelPOInit() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		commonCheck(new InheritedValidPageObjects.NormalInheritanceExcelPageObject(), 
				new ExcelFileProcessor(), ExcelAnnotation.getFindByAnnotationFullName());
	}
	
	@Test
	public void propertiesInheritancePOInit() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		commonCheck(new InheritedValidPageObjects.PropertiesInheritancePageObject(), 
				new PropertiesFileProcessor(), PropertiesAnnotation.getFindByAnnotationFullName());
	}
	
	@Test
	public void propertiesInheritanceNormalPOInit() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		commonCheck(new InheritedValidPageObjects.PropertiesInheritanceNormalPageObject(), 
				new PropertiesFileProcessor(), PropertiesAnnotation.getFindByAnnotationFullName());
	}
	
	@Test
	public void jsonInheritancePOInit() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		commonCheck(new InheritedValidPageObjects.JsonInheritancePageObject(), 
				new JsonFileProcessor(), JsonAnnotation.getFindByAnnotationFullName());
	}
	
	@Test
	public void jsonInheritanceNormalPOInit() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		commonCheck(new InheritedValidPageObjects.JsonInheritanceNormalPageObject(), 
				new JsonFileProcessor(), JsonAnnotation.getFindByAnnotationFullName());
	}
	
	@Test
	public void excelInheritancePOInit() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		commonCheck(new InheritedValidPageObjects.ExcelInheritancePageObject(), 
				new ExcelFileProcessor(), ExcelAnnotation.getFindByAnnotationFullName());
	}
	
	@Test
	public void excelInheritanceNormalPOInit() throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		commonCheck(new InheritedValidPageObjects.ExcelInheritanceNormalPageObject(), 
				new ExcelFileProcessor(), ExcelAnnotation.getFindByAnnotationFullName());
	}

}
