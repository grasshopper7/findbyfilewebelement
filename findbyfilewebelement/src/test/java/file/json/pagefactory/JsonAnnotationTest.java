package file.json.pagefactory;

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

import file.excel.pagefactory.ExcelAnnotation;
import file.excel.pagefactory.ExcelFileProcessor;
import file.excel.pagefactory.FindByExcel;
import file.excel.pagefactory.ExcelAnnotationTest.PageObject;
import file.pagefactory.FieldByCache;

public class JsonAnnotationTest {

	@Test
	public void testValidAnnotations() throws Exception {

		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("element1");
		JsonAnnotation pa = new JsonAnnotation(elem1, new JsonFileProcessor());
		try {
			pa.assertValidAnnotations();
		} catch (Exception e) {
			fail("No Exception should be thrown.");
		}
	}
	
	private void checkJsonIllegalArgExcep(JsonAnnotation pa) {
		try {
			pa.assertValidAnnotations();
			fail("@JsonFile annotation need to be present.");
		} catch (Exception e) {
			assertNotEquals("@JsonFile annotation need to be present.", 
					IllegalArgumentException.class, e);
			assertEquals("Thrown message is wrong. ", "@JsonFile annotation is missing on class level.", 
					e.getMessage());
		}
	}

	@Test
	public void testMissingFilePathAnnotation() throws Exception {

		PageObjectWOFilePathAnnotation po = new PageObjectWOFilePathAnnotation();
		Field elem1 = po.getClass().getDeclaredField("element1");
		JsonAnnotation pa = new JsonAnnotation(elem1, new JsonFileProcessor());
		checkJsonIllegalArgExcep(pa);
	}

	@Test
	public void testMissingBothAnnotations() throws Exception {

		PageObjectWOFilePathAnnotation po = new PageObjectWOFilePathAnnotation();
		Field elem1 = po.getClass().getDeclaredField("element1");
		Field spyElem1 = Mockito.spy(elem1);
		Mockito.when(spyElem1.getAnnotation(FindByJson.class)).thenReturn(null);
		JsonAnnotation pa = new JsonAnnotation(spyElem1, new JsonFileProcessor());
		checkJsonIllegalArgExcep(pa);
	}
	
	@Test
	public void testMissingFindByJsonAnnotation() throws Exception {

		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("element1");
		Field spyElem1 = Mockito.spy(elem1);
		Mockito.when(spyElem1.getAnnotation(FindByJson.class)).thenReturn(null);
		JsonAnnotation pa = new JsonAnnotation(elem1, new JsonFileProcessor());
		
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
	
	private void checkAdditionalFieldIllegalArgExcep(JsonAnnotation pa) {
		try {
			pa.assertValidAnnotations();
			fail("Only @FindByJson should be present.");
		} catch (Exception e) {
			assertNotEquals("Only @FindByJson should be present.", 
					IllegalArgumentException.class, e);
			assertEquals("Thrown message is wrong. ", "If you use a '@FindByJson' annotation, "
					+ "you must not also use a '@FindBy' or '@FindBys' or '@FindAll' annotation", e.getMessage());
		}
	}
	
	@Test
	public void testAdditionalFindByAnnotation() throws Exception {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindBy.class)).thenReturn(Mockito.mock(FindBy.class));
		JsonAnnotation pa = new JsonAnnotation(spyElem1, new JsonFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testAdditionalFindBysAnnotation() throws Exception {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindBys.class)).thenReturn(Mockito.mock(FindBys.class));
		JsonAnnotation pa = new JsonAnnotation(spyElem1, new JsonFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testAdditionalFindAllAnnotation() throws Exception {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindAll.class)).thenReturn(Mockito.mock(FindAll.class));
		JsonAnnotation pa = new JsonAnnotation(spyElem1, new JsonFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testTwoAdditionalFindAnnotations() throws Exception {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindBy.class)).thenReturn(Mockito.mock(FindBy.class));
		Mockito.when(spyElem1.getAnnotation(FindAll.class)).thenReturn(Mockito.mock(FindAll.class));
		JsonAnnotation pa = new JsonAnnotation(spyElem1, new JsonFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testAllAdditionalFindAnnotations() throws Exception {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindBy.class)).thenReturn(Mockito.mock(FindBy.class));
		Mockito.when(spyElem1.getAnnotation(FindBys.class)).thenReturn(Mockito.mock(FindBys.class));
		Mockito.when(spyElem1.getAnnotation(FindAll.class)).thenReturn(Mockito.mock(FindAll.class));
		JsonAnnotation pa = new JsonAnnotation(spyElem1, new JsonFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testBuildByFindByJsonAnnotation() throws Exception {
		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("element1");
		JsonAnnotation pa = new JsonAnnotation(elem1, new JsonFileProcessor());
		By exBy = By.id("exampleId");
		FieldByCache.addDetail(elem1, exBy);	
		assertEquals("By value not returned correctly.", exBy, pa.buildBy());
	}
	
	@Test
	public void testBuildByMissingFieldFindByJsonAnnotation() throws Exception {
		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("elementMissData");
		JsonAnnotation pa = new JsonAnnotation(elem1, new JsonFileProcessor());
		try {
			pa.buildBy();
			fail(elem1.getName() + " field data is not present in data file.");
		} catch (Exception e) {
			assertNotEquals(elem1.getName() + " field data is not present in data file.", 
					IllegalArgumentException.class, e);
			assertEquals("Thrown message is wrong.", elem1.getName() + " locator data for @FindByJson" + 
					" is not available in the data file at the path mentioned in @JsonFile.", 
					e.getMessage());
		}
	}

	@Test
	public void testBuildByFindByAnnotation() throws Exception {
		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("element2");
		JsonAnnotation pa = new JsonAnnotation(elem1, new JsonFileProcessor());
		assertEquals("By value not returned correctly.", By.id("exampleId"), pa.buildBy());
	}
	
	@JsonFile(filePath = "src/test/resources/json/MissingElementData.json")
	public class PageObject {
		@FindByJson
		private WebElement element1;
		@FindBy(id="exampleId")
		private WebElement element2;
		@FindByJson
		private WebElement elementMissData;
	}

	public class PageObjectWOFilePathAnnotation {
		@FindByJson
		private WebElement element1;
	}

}
