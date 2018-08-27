package file.properties.pagefactory;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import file.pagefactory.FieldByCache;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.List;

public class PropertiesFileProcessorTest {
	
	@Test
	public void testAnnotation() {
		Field field = mock(Field.class);
		PropertiesFileProcessor pfp = new PropertiesFileProcessor();
		assertEquals("Should return PropertiesAnnotation", PropertiesAnnotation.class, pfp.getAnnotation(field).getClass());
	}

	@Test
	public void testValidFilePathDefaultDelimiter() throws Exception {
		PropertiesFileProcessor pfp = new PropertiesFileProcessor();
		ValidFilePathDefaultDelimiterPage pp = new ValidFilePathDefaultDelimiterPage();		
		Field field = pp.getClass().getField("validFileDefaultDelim");
		pfp.dataSourceDetails(field);
		pfp.parseDataSource();		
		assertEquals("By stored in cache is not correct.", By.id("validFileDefaultDelim"), FieldByCache.getByForField(field));
	}
	
	@Test
	public void testValidFilePathCustomDelimiter() throws Exception {
		PropertiesFileProcessor pfp = new PropertiesFileProcessor();
		ValidFilePathCustomDelimiterPage pp = new ValidFilePathCustomDelimiterPage();		
		Field field = pp.getClass().getField("validFileCustomDelim");
		pfp.dataSourceDetails(field);
		pfp.parseDataSource();		
		assertEquals("By stored in cache is not correct.", By.name("validFileCustomDelim"), FieldByCache.getByForField(field));
	}
	
	@Test(expected=RuntimeException.class)
	public void testInValidFilePath() throws Exception {
		PropertiesFileProcessor pfp = new PropertiesFileProcessor();
		InValidFilePathCustomDelimiterPage pp = new InValidFilePathCustomDelimiterPage();		
		Field field = pp.getClass().getField("inValidFile");
		pfp.dataSourceDetails(field);
		pfp.parseDataSource();		
		fail("Runtime Exception with cause FileNotFoundException should be thrown.");
	}
	
	@Test
	public void testInValidFilePathExactException() throws Exception {
		PropertiesFileProcessor pfp = new PropertiesFileProcessor();
		InValidFilePathCustomDelimiterPage pp = new InValidFilePathCustomDelimiterPage();		
		Field field = pp.getClass().getField("inValidFile");
		pfp.dataSourceDetails(field);
		Throwable fnfe = null;
		try{
			pfp.parseDataSource();		
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be FileNotFoundExeption", FileNotFoundException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidPageObjectPath() throws Exception {
		PropertiesFileProcessor pfp = new PropertiesFileProcessor();
		InValidPageObjectPathPage pp = new InValidPageObjectPathPage();		
		Field field = pp.getClass().getField("inValidPageObjectPath");
		pfp.dataSourceDetails(field);
		Throwable fnfe = null;
		try{
			pfp.parseDataSource();		
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be ClassNotFoundException", ClassNotFoundException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidFieldName() throws Exception {
		PropertiesFileProcessor pfp = new PropertiesFileProcessor();
		InValidFieldNamePage pp = new InValidFieldNamePage();		
		Field field = pp.getClass().getField("inValidFieldName");
		pfp.dataSourceDetails(field);
		Throwable fnfe = null;
		try{
			pfp.parseDataSource();		
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be NoSuchFieldException", NoSuchFieldException.class, fnfe.getClass());
	}
	
	
	@PropertiesFile(filePath = "src/test/resources/properties/ValidFilePathDefaultDelimiterData.txt")
	public class ValidFilePathDefaultDelimiterPage{		
		@FindByProperties
		public WebElement validFileDefaultDelim;
	}
	
	@PropertiesFile(filePath = "src/test/resources/properties/ValidFilePathCustomDelimiterData.txt", delimiter="%%")
	public class ValidFilePathCustomDelimiterPage{		
		@FindByProperties
		public WebElement validFileCustomDelim;
	}

	@PropertiesFile(filePath = "src/test/resources/properties/InValidFilePathData.txt")
	public class InValidFilePathCustomDelimiterPage{
		@FindByProperties
		public WebElement inValidFile;
	}
	
	@PropertiesFile(filePath = "src/test/resources/properties/InValidPageObjectPathData.txt")
	public class InValidPageObjectPathPage{
		@FindByProperties
		public WebElement inValidPageObjectPath;
	}
	
	@PropertiesFile(filePath = "src/test/resources/properties/InValidFieldNameData.txt")
	public class InValidFieldNamePage{
		@FindByProperties
		public WebElement inValidFieldName;
	}
}


