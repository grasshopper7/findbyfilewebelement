package file.properties.pagefactory;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebElement;

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
	
	private void checkIllegalArgExcep(PropertiesAnnotation pa) {
		try {
			pa.assertValidAnnotations();
			fail("Both @PropertiesFile and @FindByProperties should be present.");
		} catch (Exception e) {
			assertNotEquals("Both @PropertiesFile and @FindByProperties should be present.", 
					IllegalArgumentException.class, e);
		}
	}

	@Test
	public void testMissingFilePathAnnotation() throws NoSuchFieldException, SecurityException {

		PageObjectWOFilePathAnnotation po = new PageObjectWOFilePathAnnotation();
		Field elem1 = po.getClass().getDeclaredField("element1");
		PropertiesAnnotation pa = new PropertiesAnnotation(elem1, new PropertiesFileProcessor());
		checkIllegalArgExcep(pa);			
	}

	@Test
	public void testMissingFindPropertiesAnnotation() throws NoSuchFieldException, SecurityException {

		PageObject po = new PageObject();
		Field elem1 = po.getClass().getDeclaredField("element1");
		Field spyElem1 = Mockito.spy(elem1);
		Mockito.when(spyElem1.getAnnotation(FindByProperties.class)).thenReturn(null);
		PropertiesAnnotation pa = new PropertiesAnnotation(spyElem1, new PropertiesFileProcessor());
		checkIllegalArgExcep(pa);
	}

	@Test
	public void testMissingBothAnnotation() throws NoSuchFieldException, SecurityException {

		PageObjectWOFilePathAnnotation po = new PageObjectWOFilePathAnnotation();
		Field elem1 = po.getClass().getDeclaredField("element1");
		Field spyElem1 = Mockito.spy(elem1);
		Mockito.when(spyElem1.getAnnotation(FindByProperties.class)).thenReturn(null);
		PropertiesAnnotation pa = new PropertiesAnnotation(spyElem1, new PropertiesFileProcessor());
		checkIllegalArgExcep(pa);	
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
