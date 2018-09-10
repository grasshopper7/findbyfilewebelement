package file.pagefactory.properties;

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
import file.pagefactory.excel.ExcelFile;
import file.pagefactory.excel.FindByExcel;
import file.pagefactory.json.FindByJson;
import file.pagefactory.json.JsonFile;
import file.pagefactory.properties.FindByProperties;
import file.pagefactory.properties.PropertiesAnnotation;
import file.pagefactory.properties.PropertiesFile;
import file.pagefactory.properties.PropertiesFileProcessor;

public class PropertiesAnnotationTest {

	@Test
	public void testValidAnnotations() throws Exception {

		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("element1");
		PropertiesAnnotation pa = new PropertiesAnnotation(elem1, new PropertiesFileProcessor());
		try {
			pa.assertValidAnnotations();
		} catch (Exception e) {
			fail("No Exception should be thrown.");
		}
	}
	
	private void checkPropertiesIllegalArgExcep(PropertiesAnnotation pa) {
		try {
			pa.assertValidAnnotations();
			fail("@PropertiesFile annotation need to be present.");
		} catch (Exception e) {
			assertNotEquals("@PropertiesFile annotation need to be present.", 
					IllegalArgumentException.class, e);
			assertEquals("Thrown message is wrong.", "@PropertiesFile annotation is missing on class level.", 
					e.getMessage());
		}
	}

	@Test
	public void testMissingFilePathAnnotation() throws Exception {

		PageObjectWOFilePathAnnotation po = new PageObjectWOFilePathAnnotation();
		Field elem1 = po.getClass().getDeclaredField("element1");
		PropertiesAnnotation pa = new PropertiesAnnotation(elem1, new PropertiesFileProcessor());
		checkPropertiesIllegalArgExcep(pa);
	}

	@Test
	public void testMissingBothAnnotations() throws Exception {

		PageObjectWOFilePathAnnotation po = new PageObjectWOFilePathAnnotation();
		Field elem1 = po.getClass().getDeclaredField("element1");
		Field spyElem1 = Mockito.spy(elem1);
		Mockito.when(spyElem1.getAnnotation(FindByProperties.class)).thenReturn(null);
		PropertiesAnnotation pa = new PropertiesAnnotation(spyElem1, new PropertiesFileProcessor());
		checkPropertiesIllegalArgExcep(pa);
	}
	
	private void checkFileAnnotationIllegalAdditionalExcep(PropertiesAnnotation pa) {
		try {
			pa.assertValidAnnotations();
			fail("Additional File Processor annotation with @PropertiesFile is not allowed.");
		} catch (Exception e) {
			assertNotEquals("Additional File Processor annotation with @PropertiesFile is not allowed.", 
					IllegalArgumentException.class, e);
			assertEquals("Thrown message is wrong.", "Only @PropertiesFile annotation is allowed on class level.", 
					e.getMessage());
		}
	}
	
	@Test
	public void testAdditionalExcelFileAnnotation() throws Exception {
		
		PageObjectPropExcelFileAnnotation po = new PageObjectPropExcelFileAnnotation();
		Field elem1 = po.getClass().getDeclaredField("element1");
		PropertiesAnnotation pa = new PropertiesAnnotation(elem1, new PropertiesFileProcessor());
		checkFileAnnotationIllegalAdditionalExcep(pa);
	}
	
	@Test
	public void testAdditionalJsonFileAnnotation() throws Exception {
		
		PageObjectPropJsonFileAnnotation po = new PageObjectPropJsonFileAnnotation();
		Field elem1 = po.getClass().getDeclaredField("element1");
		PropertiesAnnotation pa = new PropertiesAnnotation(elem1, new PropertiesFileProcessor());
		checkFileAnnotationIllegalAdditionalExcep(pa);
	}
	
	@Test
	public void testAdditionalAllFileAnnotation() throws Exception {
		
		PageObjectAllFileAnnotation po = new PageObjectAllFileAnnotation();
		Field elem1 = po.getClass().getDeclaredField("element1");
		PropertiesAnnotation pa = new PropertiesAnnotation(elem1, new PropertiesFileProcessor());
		checkFileAnnotationIllegalAdditionalExcep(pa);
	}
	
	@Test
	public void testMissingFindByPropertiesAnnotation() throws Exception {

		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("element1");
		Field spyElem1 = Mockito.spy(elem1);
		Mockito.when(spyElem1.getAnnotation(FindByProperties.class)).thenReturn(null);
		PropertiesAnnotation pa = new PropertiesAnnotation(elem1, new PropertiesFileProcessor());
		
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
	
	private void checkAdditionalFieldIllegalArgExcep(PropertiesAnnotation pa) {
		try {
			pa.assertValidAnnotations();
			fail("Only @FindByProperties should be present.");
		} catch (Exception e) {
			assertNotEquals("Only @FindByProperties should be present.", 
					IllegalArgumentException.class, e);
			assertEquals("Thrown message is wrong. ", "If you use a '@FindByProperties' annotation, "
					+ "you must not also use a '@FindBy' or '@FindBys' or '@FindAll' annotation", e.getMessage());
		}
	}
	
	@Test
	public void testAdditionalFindByAnnotation() throws Exception {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindBy.class)).thenReturn(Mockito.mock(FindBy.class));
		PropertiesAnnotation pa = new PropertiesAnnotation(spyElem1, new PropertiesFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testAdditionalFindBysAnnotation() throws Exception {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindBys.class)).thenReturn(Mockito.mock(FindBys.class));
		PropertiesAnnotation pa = new PropertiesAnnotation(spyElem1, new PropertiesFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testAdditionalFindAllAnnotation() throws Exception {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindAll.class)).thenReturn(Mockito.mock(FindAll.class));
		PropertiesAnnotation pa = new PropertiesAnnotation(spyElem1, new PropertiesFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testTwoAdditionalFindAnnotations() throws Exception {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindBy.class)).thenReturn(Mockito.mock(FindBy.class));
		Mockito.when(spyElem1.getAnnotation(FindAll.class)).thenReturn(Mockito.mock(FindAll.class));
		PropertiesAnnotation pa = new PropertiesAnnotation(spyElem1, new PropertiesFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testAllAdditionalFindAnnotations() throws Exception {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindBy.class)).thenReturn(Mockito.mock(FindBy.class));
		Mockito.when(spyElem1.getAnnotation(FindBys.class)).thenReturn(Mockito.mock(FindBys.class));
		Mockito.when(spyElem1.getAnnotation(FindAll.class)).thenReturn(Mockito.mock(FindAll.class));
		PropertiesAnnotation pa = new PropertiesAnnotation(spyElem1, new PropertiesFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testBuildByFindByPropertiesAnnotation() throws Exception {
		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("element1");
		PropertiesAnnotation pa = new PropertiesAnnotation(elem1, new PropertiesFileProcessor());
		By exBy = By.id("exampleId");
		FieldByCache.addDetail(elem1, exBy);	
		assertEquals("By value not returned correctly.", exBy, pa.buildBy());
	}
	
	@Test
	public void testBuildByMissingFieldFindByPropertiesAnnotation() throws Exception {
		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("elementMissData");
		PropertiesAnnotation pa = new PropertiesAnnotation(elem1, new PropertiesFileProcessor());
		try {
			pa.buildBy();
			fail(elem1.getName() + " field data is not present in data file.");
		} catch (Exception e) {
			assertNotEquals(elem1.getName() + " field data is not present in data file.", 
					IllegalArgumentException.class, e);
			assertEquals("Thrown message is wrong.", elem1.getName() + " locator data for @FindByProperties" + 
					" is not available in the data file at the path mentioned in @PropertiesFile.", 
					e.getMessage());
		}
	}

	@Test
	public void testBuildByFindByAnnotation() throws Exception {
		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("element2");
		PropertiesAnnotation pa = new PropertiesAnnotation(elem1, new PropertiesFileProcessor());
		assertEquals("By value not returned correctly.", By.id("exampleId"), pa.buildBy());
	}
	
	@PropertiesFile(filePath = "src/test/resources/properties/MissingElementData.properties")
	public class PageObject {
		@FindByProperties
		private WebElement element1;
		@FindBy(id="exampleId")
		private WebElement element2;
		@FindByProperties
		private WebElement elementMissData;
	}

	public class PageObjectWOFilePathAnnotation {
		@FindByProperties
		private WebElement element1;
	}
	
	@PropertiesFile(filePath="")
	@ExcelFile(filePath="")
	public class PageObjectPropExcelFileAnnotation {
		@FindByProperties
		private WebElement element1;
	}

	@PropertiesFile(filePath="")
	@JsonFile(filePath="")
	public class PageObjectPropJsonFileAnnotation {
		@FindByProperties
		private WebElement element1;
	}
	
	@PropertiesFile(filePath="")
	@JsonFile(filePath="")
	@ExcelFile(filePath="")
	public class PageObjectAllFileAnnotation {
		@FindByProperties
		private WebElement element1;
	}	
}
