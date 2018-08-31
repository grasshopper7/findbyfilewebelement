package file.properties.pagefactory;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebElement;

import file.test.FindTest;

public class PropertiesAnnotationTest {

	@Test
	public void test() {
		
		PropertiesFileProcessor pfp = new PropertiesFileProcessor();
		Field fld = Mockito.mock(Field.class);
		FindByProperties fbp = Mockito.mock(FindByProperties.class);
		Mockito.when(fld.getAnnotation(FindByProperties.class)).thenReturn(fbp);
		PropertiesFile pf = Mockito.mock(PropertiesFile.class);
		
		
		System.out.println(fld.getAnnotation(FindByProperties.class) != null);
		
		
		/*PropertiesAnnotation propAnn = new PropertiesAnnotation(fld,pfp);
		
		propAnn.buildBy(fld.getAnnotation(FindByProperties.class) != null);*/
	}
	
	@PropertiesFile(filePath="")
	public  static class PageObject {
		@FindByProperties
		private WebElement element1;
	}

}
