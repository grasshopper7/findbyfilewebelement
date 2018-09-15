package file.pagefactory;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;

import org.junit.Test;
import org.openqa.selenium.By;

public abstract class BaseFileProcessorTest {

	public abstract FileProcessor createFileProcessor();
	
	@SuppressWarnings("rawtypes")
	public abstract Class getRequisiteAnnotation();
	
	public abstract TestPage createValidFilePathPage();
	
	public abstract TestPage createInValidFilePathPage();
	
	public abstract TestPage createInValidPageObjectPathPage();
	
	public abstract TestPage createInValidFieldNamePage();
	
	public abstract TestPage createInValidHowPage();
	
	public abstract TestPage createInValidMissingFileAnnotationPage();
	
	
	@Test
	public void testRequisiteAnnotation() {
		Field field = mock(Field.class);
		FileProcessor fp = createFileProcessor();
		assertEquals("Should return "+getRequisiteAnnotation().getName() + " class.", getRequisiteAnnotation(), 
				fp.getAnnotation(field).getClass());
	}
	
	@Test
	public void testValidFilePath() {
		Field field = createAndSetupFileProcessor(createValidFilePathPage(),"validFilePath");				
		assertEquals("By stored in cache is not correct.", By.id("validFilePath"), FieldByCache.getByForField(field));
	}
	
	@Test
	public void testInValidFilePath() {
		Throwable fnfe = null;
		try{
			createAndSetupFileProcessor(createInValidFilePathPage(),"inValidFilePath");		
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be FileNotFoundExeption", FileNotFoundException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidPageObjectPath() {
		Throwable fnfe = null;
		try{
			createAndSetupFileProcessor(createInValidPageObjectPathPage(),"inValidPageObjectPath");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be ClassNotFoundException", ClassNotFoundException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidFieldName() {
		Throwable fnfe = null;
		try{
			createAndSetupFileProcessor(createInValidFieldNamePage(),"inValidFieldName");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be NoSuchFieldException", NoSuchFieldException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidHow() {
		Throwable fnfe = null;
		try{
			createAndSetupFileProcessor(createInValidHowPage(),"inValidHow");
		} catch (RuntimeException e) {
			fnfe = e.getCause();
		}
		assertEquals("Exception thrown needs to be IllegalArgumentException", IllegalArgumentException.class, fnfe.getClass());
	}
	
	@Test
	public void testInValidMissingFileAnnotation() throws NoSuchFieldException {
		Throwable fnfe = null;
		try{
			FileProcessor fp = createFileProcessor();
			Field field = (createInValidMissingFileAnnotationPage()).getClass().getField("inValidMissingFileAnnotation");
			fp.dataSourceDetails(field);
		} catch (NullPointerException e) {
			fnfe = e;
		}
		assertEquals("Exception thrown needs to be NullPointerException", NullPointerException.class, fnfe.getClass());
	}
	
	protected Field createAndSetupFileProcessor(TestPage page, String fieldName) {
		try {
			FileProcessor fp = createFileProcessor();
			Field field = page.getClass().getField(fieldName);
			fp.populateData(field);
			return field;
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}		
	}
}
