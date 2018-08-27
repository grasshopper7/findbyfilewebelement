package file.pagefactory;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.DefaultElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileElementLocatorFactoryTest {

	@Test
	public void test() {
		
		SearchContext sc = mock(SearchContext.class);
		FileProcessor fp = mock(FileProcessor.class);
		Field f = mock(Field.class);
		AbstractFileAnnotations afa = mock(AbstractFileAnnotations.class);
		
		when(afa.isLookupCached()).thenReturn(true);
		when(fp.getAnnotation(f)).thenReturn(afa);
		
		FileElementLocatorFactory felf = new FileElementLocatorFactory(sc, fp);
		ElementLocator del = felf.createLocator(f);
		
		assertEquals("Object returned should be of org.openqa.selenium.support.pagefactory.DefaultElementLocator class", 
				DefaultElementLocator.class, del.getClass());
	}

}
