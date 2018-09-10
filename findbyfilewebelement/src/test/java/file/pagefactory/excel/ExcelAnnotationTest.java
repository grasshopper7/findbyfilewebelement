package file.pagefactory.excel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;

import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import file.pagefactory.FieldByCache;
import file.pagefactory.excel.ExcelAnnotation;
import file.pagefactory.excel.ExcelFile;
import file.pagefactory.excel.ExcelFileProcessor;
import file.pagefactory.excel.FindByExcel;
import file.pagefactory.json.JsonFile;
import file.pagefactory.properties.FindByProperties;
import file.pagefactory.properties.PropertiesAnnotation;
import file.pagefactory.properties.PropertiesFile;
import file.pagefactory.properties.PropertiesFileProcessor;
import file.pagefactory.properties.PropertiesAnnotationTest.PageObjectAllFileAnnotation;
import file.pagefactory.properties.PropertiesAnnotationTest.PageObjectPropExcelFileAnnotation;
import file.pagefactory.properties.PropertiesAnnotationTest.PageObjectPropJsonFileAnnotation;

public class ExcelAnnotationTest {

	@Test
	public void testValidAnnotations() throws Exception {

		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("element1");
		ExcelAnnotation pa = new ExcelAnnotation(elem1, new ExcelFileProcessor());
		try {
			pa.assertValidAnnotations();
		} catch (Exception e) {
			fail("No Exception should be thrown.");
		}
	}
	
	private void checkExcelIllegalArgExcep(ExcelAnnotation pa) {
		try {
			pa.assertValidAnnotations();
			fail("@ExcelFile annotation need to be present.");
		} catch (Exception e) {
			assertNotEquals("@ExcelFile annotation need to be present.", 
					IllegalArgumentException.class, e);
			assertEquals("Thrown message is wrong. ", "@ExcelFile annotation is missing on class level.", 
					e.getMessage());
		}
	}

	@Test
	public void testMissingFilePathAnnotation() throws Exception {

		PageObjectWOFilePathAnnotation po = new PageObjectWOFilePathAnnotation();
		Field elem1 = po.getClass().getDeclaredField("element1");
		ExcelAnnotation pa = new ExcelAnnotation(elem1, new ExcelFileProcessor());
		checkExcelIllegalArgExcep(pa);
	}

	@Test
	public void testMissingBothAnnotations() throws Exception {

		PageObjectWOFilePathAnnotation po = new PageObjectWOFilePathAnnotation();
		Field elem1 = po.getClass().getDeclaredField("element1");
		Field spyElem1 = Mockito.spy(elem1);
		Mockito.when(spyElem1.getAnnotation(FindByExcel.class)).thenReturn(null);
		ExcelAnnotation pa = new ExcelAnnotation(spyElem1, new ExcelFileProcessor());
		checkExcelIllegalArgExcep(pa);
	}
	
	private void checkFileAnnotationIllegalAdditionalExcep(ExcelAnnotation pa) {
		try {
			pa.assertValidAnnotations();
			fail("Additional File Processor annotation with @ExcelFile is not allowed.");
		} catch (Exception e) {
			assertNotEquals("Additional File Processor annotation with @ExcelFile is not allowed.", 
					IllegalArgumentException.class, e);
			assertEquals("Thrown message is wrong.", "Only @ExcelFile annotation is allowed on class level.", 
					e.getMessage());
		}
	}
	
	@Test
	public void testAdditionalPropertiesFileAnnotation() throws Exception {
		
		PageObjectExcelPropFileAnnotation po = new PageObjectExcelPropFileAnnotation();
		Field elem1 = po.getClass().getDeclaredField("element1");
		ExcelAnnotation pa = new ExcelAnnotation(elem1, new ExcelFileProcessor());
		checkFileAnnotationIllegalAdditionalExcep(pa);
	}
	
	@Test
	public void testAdditionalJsonFileAnnotation() throws Exception {
		
		PageObjectExcelJsonFileAnnotation po = new PageObjectExcelJsonFileAnnotation();
		Field elem1 = po.getClass().getDeclaredField("element1");
		ExcelAnnotation pa = new ExcelAnnotation(elem1, new ExcelFileProcessor());
		checkFileAnnotationIllegalAdditionalExcep(pa);
	}
	
	@Test
	public void testAdditionalAllFileAnnotation() throws Exception {
		
		PageObjectAllFileAnnotation po = new PageObjectAllFileAnnotation();
		Field elem1 = po.getClass().getDeclaredField("element1");
		ExcelAnnotation pa = new ExcelAnnotation(elem1, new ExcelFileProcessor());
		checkFileAnnotationIllegalAdditionalExcep(pa);
	}
	
	@Test
	public void testMissingFindByExcelAnnotation() throws Exception {

		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("element1");
		Field spyElem1 = Mockito.spy(elem1);
		Mockito.when(spyElem1.getAnnotation(FindByExcel.class)).thenReturn(null);
		ExcelAnnotation pa = new ExcelAnnotation(elem1, new ExcelFileProcessor());
		
		try {
			pa.assertValidAnnotations();
		} catch (Exception e) {
			fail("No Exception should be thrown.");
		}
	}

	
	private Field prepareField() throws Exception {
		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("element1");
		return Mockito.spy(elem1);
	}
	
	private void checkAdditionalFieldIllegalArgExcep(ExcelAnnotation pa) {
		try {
			pa.assertValidAnnotations();
			fail("Only @FindByExcel should be present.");
		} catch (Exception e) {
			assertNotEquals("Only @FindByExcel should be present.", 
					IllegalArgumentException.class, e);
			assertEquals("Thrown message is wrong. ", "If you use a '@FindByExcel' annotation, "
					+ "you must not also use a '@FindBy' or '@FindBys' or '@FindAll' annotation", e.getMessage());
		}
	}
	
	@Test
	public void testAdditionalFindByAnnotation() throws Exception {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindBy.class)).thenReturn(Mockito.mock(FindBy.class));
		ExcelAnnotation pa = new ExcelAnnotation(spyElem1, new ExcelFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testAdditionalFindBysAnnotation() throws Exception {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindBys.class)).thenReturn(Mockito.mock(FindBys.class));
		ExcelAnnotation pa = new ExcelAnnotation(spyElem1, new ExcelFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testAdditionalFindAllAnnotation() throws Exception {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindAll.class)).thenReturn(Mockito.mock(FindAll.class));
		ExcelAnnotation pa = new ExcelAnnotation(spyElem1, new ExcelFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testTwoAdditionalFindAnnotations() throws Exception {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindBy.class)).thenReturn(Mockito.mock(FindBy.class));
		Mockito.when(spyElem1.getAnnotation(FindAll.class)).thenReturn(Mockito.mock(FindAll.class));
		ExcelAnnotation pa = new ExcelAnnotation(spyElem1, new ExcelFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testAllAdditionalFindAnnotations() throws Exception {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindBy.class)).thenReturn(Mockito.mock(FindBy.class));
		Mockito.when(spyElem1.getAnnotation(FindBys.class)).thenReturn(Mockito.mock(FindBys.class));
		Mockito.when(spyElem1.getAnnotation(FindAll.class)).thenReturn(Mockito.mock(FindAll.class));
		ExcelAnnotation pa = new ExcelAnnotation(spyElem1, new ExcelFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testBuildByFindByExcelAnnotation() throws Exception {
		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("element1");
		ExcelAnnotation pa = new ExcelAnnotation(elem1, new ExcelFileProcessor());
		By exBy = By.id("exampleId");
		FieldByCache.addDetail(elem1, exBy);	
		assertEquals("By value not returned correctly.", exBy, pa.buildBy());
	}

	@Test
	public void testBuildByMissingFieldFindByExcelAnnotation() throws Exception {
		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("elementMissData");
		ExcelAnnotation pa = new ExcelAnnotation(elem1, new ExcelFileProcessor());
		try {
			pa.buildBy();
			fail(elem1.getName() + " field data is not present in data file.");
		} catch (Exception e) {
			assertNotEquals(elem1.getName() + " field data is not present in data file.", 
					IllegalArgumentException.class, e);
			assertEquals("Thrown message is wrong.", elem1.getName() + " locator data for @FindByExcel" + 
					" is not available in the data file at the path mentioned in @ExcelFile.", 
					e.getMessage());
		}
	}
	
	@Test
	public void testBuildByFindByAnnotation() throws Exception {
		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("element2");
		ExcelAnnotation pa = new ExcelAnnotation(elem1, new ExcelFileProcessor());
		assertEquals("By value not returned correctly.", By.id("exampleId"), pa.buildBy());
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/MissingElementData.xlsx")
	public class PageObject {
		@FindByExcel
		private WebElement element1;
		@FindBy(id="exampleId")
		private WebElement element2;
		@FindByExcel
		private WebElement elementMissData;
	}

	public class PageObjectWOFilePathAnnotation {
		@FindByExcel
		private WebElement element1;
	}

	@PropertiesFile(filePath="")
	@ExcelFile(filePath="")
	public class PageObjectExcelPropFileAnnotation {
		@FindByExcel
		private WebElement element1;
	}

	@ExcelFile(filePath="")
	@JsonFile(filePath="")
	public class PageObjectExcelJsonFileAnnotation {
		@FindByExcel
		private WebElement element1;
	}
	
	@PropertiesFile(filePath="")
	@JsonFile(filePath="")
	@ExcelFile(filePath="")
	public class PageObjectAllFileAnnotation {
		@FindByExcel
		private WebElement element1;
	}
}
