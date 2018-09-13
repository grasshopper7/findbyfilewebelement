package file.pagefactory;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import file.pagefactory.excel.FindByExcel;
import file.pagefactory.json.FindByJson;
import file.pagefactory.properties.FindByProperties;

public class FileFieldDecoratorTest {
	
	@Test
	public void testFindByList() throws NoSuchFieldException, SecurityException {
		
		PageObject po =  new PageObject();
		FileFieldDecorator ffd = new FileFieldDecorator(Mockito.mock(FileElementLocatorFactory.class));		
		Field field = po.getClass().getDeclaredField("element1");		
		assertTrue("List of WebElement with FindBy annotation should return true.", ffd.isDecoratableList(field));
	}
	
	@Test
	public void testFindByPropertiesList() throws NoSuchFieldException, SecurityException {
		
		PageObject po =  new PageObject();
		FileFieldDecorator ffd = new FileFieldDecorator(Mockito.mock(FileElementLocatorFactory.class));			
		Field field = po.getClass().getDeclaredField("element2");		
		assertTrue("List of WebElement with FindByProperties annotation should return true.", ffd.isDecoratableList(field));	
	}
	
	@Test
	public void testFindByJsonList() throws NoSuchFieldException, SecurityException {
		
		PageObject po =  new PageObject();
		FileFieldDecorator ffd = new FileFieldDecorator(Mockito.mock(FileElementLocatorFactory.class));			
		Field field = po.getClass().getDeclaredField("element3");		
		assertTrue("List of WebElement with FindByJson annotation should return true.", ffd.isDecoratableList(field));	
	}
	
	@Test
	public void testFindByExcelList() throws NoSuchFieldException, SecurityException {
		
		PageObject po =  new PageObject();
		FileFieldDecorator ffd = new FileFieldDecorator(Mockito.mock(FileElementLocatorFactory.class));			
		Field field = po.getClass().getDeclaredField("element4");		
		assertTrue("List of WebElement with FindByExcel annotation should return true.", ffd.isDecoratableList(field));	
	}
	
	public class PageObject {	
		@FindBy
		private List<WebElement> element1;
		
		@FindByProperties
		private List<WebElement> element2;
		
		@FindByJson
		private List<WebElement> element3;
		
		@FindByExcel
		private List<WebElement> element4;
	}
}
