package file.properties.pagefactory;

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
			assertEquals("Thrown message is wrong. ", "@PropertiesFile annotation is missing on class level.", 
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
	public void testBuildByFindByPropertiesAnntation() throws Exception {
		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("element1");
		PropertiesAnnotation pa = new PropertiesAnnotation(elem1, new PropertiesFileProcessor());
		By exBy = By.id("exampleId");
		FieldByCache.addDetail(elem1, exBy);	
		assertEquals("By value not returned correctly.", exBy, pa.buildBy());
	}

	@Test
	public void testBuildByFindByAnntation() throws Exception {
		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("element2");
		PropertiesAnnotation pa = new PropertiesAnnotation(elem1, new PropertiesFileProcessor());
		assertEquals("By value not returned correctly.", By.id("exampleId"), pa.buildBy());
	}
	
	@PropertiesFile(filePath = "")
	public class PageObject {
		@FindByProperties
		private WebElement element1;
		@FindBy(id="exampleId")
		private WebElement element2;
	}

	public class PageObjectWOFilePathAnnotation {
		@FindByProperties
		private WebElement element1;
	}

}
