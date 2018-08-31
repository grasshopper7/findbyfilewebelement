package file.properties.pagefactory;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class PropertiesAnnotationTest {

	@Test
	public void testValidAnnotations() throws NoSuchFieldException, SecurityException {

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
			fail("Both @PropertiesFile and @FindByProperties should be present.");
		} catch (Exception e) {
			assertNotEquals("Both @PropertiesFile and @FindByProperties should be present.", 
					IllegalArgumentException.class, e);
			assertEquals("Thrown message is wrong. ", "'@FindByProperties' annotation must be use together "
					+ "with a '@PropertiesFile' annotation", e.getMessage());
		}
	}

	@Test
	public void testMissingFilePathAnnotation() throws NoSuchFieldException, SecurityException {

		PageObjectWOFilePathAnnotation po = new PageObjectWOFilePathAnnotation();
		Field elem1 = po.getClass().getDeclaredField("element1");
		PropertiesAnnotation pa = new PropertiesAnnotation(elem1, new PropertiesFileProcessor());
		checkPropertiesIllegalArgExcep(pa);			
	}

	@Test
	public void testMissingFindPropertiesAnnotation() throws NoSuchFieldException, SecurityException {

		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("element1");
		Field spyElem1 = Mockito.spy(elem1);
		Mockito.when(spyElem1.getAnnotation(FindByProperties.class)).thenReturn(null);
		PropertiesAnnotation pa = new PropertiesAnnotation(spyElem1, new PropertiesFileProcessor());
		checkPropertiesIllegalArgExcep(pa);
	}

	@Test
	public void testMissingBothAnnotation() throws NoSuchFieldException, SecurityException {

		PageObjectWOFilePathAnnotation po = new PageObjectWOFilePathAnnotation();
		Field elem1 = po.getClass().getDeclaredField("element1");
		Field spyElem1 = Mockito.spy(elem1);
		Mockito.when(spyElem1.getAnnotation(FindByProperties.class)).thenReturn(null);
		PropertiesAnnotation pa = new PropertiesAnnotation(spyElem1, new PropertiesFileProcessor());
		checkPropertiesIllegalArgExcep(pa);	
	}
	
	private Field prepareField() throws NoSuchFieldException, SecurityException {
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
	public void testAdditionalFindByAnnotation() throws NoSuchFieldException, SecurityException {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindBy.class)).thenReturn(Mockito.mock(FindBy.class));
		PropertiesAnnotation pa = new PropertiesAnnotation(spyElem1, new PropertiesFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testAdditionalFindBysAnnotation() throws NoSuchFieldException, SecurityException {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindBys.class)).thenReturn(Mockito.mock(FindBys.class));
		PropertiesAnnotation pa = new PropertiesAnnotation(spyElem1, new PropertiesFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testAdditionalFindAllAnnotation() throws NoSuchFieldException, SecurityException {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindAll.class)).thenReturn(Mockito.mock(FindAll.class));
		PropertiesAnnotation pa = new PropertiesAnnotation(spyElem1, new PropertiesFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testTwoAdditionalFindAnnotations() throws NoSuchFieldException, SecurityException {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindBy.class)).thenReturn(Mockito.mock(FindBy.class));
		Mockito.when(spyElem1.getAnnotation(FindAll.class)).thenReturn(Mockito.mock(FindAll.class));
		PropertiesAnnotation pa = new PropertiesAnnotation(spyElem1, new PropertiesFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}
	
	@Test
	public void testAllAdditionalFindAnnotations() throws NoSuchFieldException, SecurityException {
		Field spyElem1 = prepareField(); 
		Mockito.when(spyElem1.getAnnotation(FindBy.class)).thenReturn(Mockito.mock(FindBy.class));
		Mockito.when(spyElem1.getAnnotation(FindBys.class)).thenReturn(Mockito.mock(FindBys.class));
		Mockito.when(spyElem1.getAnnotation(FindAll.class)).thenReturn(Mockito.mock(FindAll.class));
		PropertiesAnnotation pa = new PropertiesAnnotation(spyElem1, new PropertiesFileProcessor());
		checkAdditionalFieldIllegalArgExcep(pa);
	}

	@PropertiesFile(filePath = "")
	public class PageObject {
		@FindByProperties
		private WebElement element1;
	}

	public class PageObjectWOFilePathAnnotation {
		@FindByProperties
		private WebElement element1;
	}

}
