package file.pagefactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.function.Consumer;

import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public abstract class  BaseAnnotationTest {
	
	
	public abstract TestPage createValidPageObject();
	
	public abstract Field createValidField();
	
	public abstract Field createValidSpyField();
	
	public abstract AbstractFileAnnotations createFileAnnotation(Field field);
	
	@SuppressWarnings("rawtypes")
	public abstract Class getFileAnnotationsClass();
	
	
	
	public Field createField(TestPage tp, String field) {
		try {
			return tp.getClass().getDeclaredField(field);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
		
	public Field createSpyField(TestPage tp, String field) {
		 Field fld = createField(tp, field);
		 return Mockito.spy(fld);
	}
	
	
	
	@Test
	public void testValidAnnotations()  { 
		Field field = createValidField();
		AbstractFileAnnotations afa = createFileAnnotation(field);
		try {
			afa.assertValidAnnotations();
		} catch (Exception e) {
			fail("No Exception should be thrown.");
		}
	}
	
	protected void checkIllegalArgException(AbstractFileAnnotations afa) {
		try {
			afa.assertValidAnnotations();
			fail("@"+afa.getFileAnnotationName()+" annotation need to be present.");
		} catch (Exception e) {
			assertNotEquals("@"+afa.getFileAnnotationName()+" annotation need to be present.", 
					IllegalArgumentException.class, e);
			assertEquals("Thrown message is wrong.", "@"+afa.getFileAnnotationName()+" annotation is missing on class level.", 
					e.getMessage());
		}
	}
	
	protected void checkMissingFilePathAnnotation(TestPage po)  {
		Field elem1 = createField(po, "element1");
		AbstractFileAnnotations afa = createFileAnnotation(elem1);
		checkIllegalArgException(afa);
	}

	@SuppressWarnings("unchecked")
	protected void checkMissingBothAnnotations(TestPage po)  {
		Field elem1 = createField(po, "element1");
		Field spyElem1 = Mockito.spy(elem1);
		Mockito.when(spyElem1.getAnnotation(getFileAnnotationsClass())).thenReturn(null);
		AbstractFileAnnotations afa = createFileAnnotation(elem1);
		checkIllegalArgException(afa);
	}
	
	protected void checkFileAnnotationIllegalAdditionalExcep(AbstractFileAnnotations afa) {
		try {
			afa.assertValidAnnotations();
			fail("Additional File Processor annotation with @"+afa.getFileAnnotationName()+" is not allowed.");
		} catch (Exception e) {
			assertNotEquals("Additional File Processor annotation with @"+afa.getFileAnnotationName()+" is not allowed.", 
					IllegalArgumentException.class, e);
			assertEquals("Thrown message is wrong.", "Only @"+afa.getFileAnnotationName()+" annotation is allowed on class level.", 
					e.getMessage());
		}
	}
	
	protected void checkAdditionalFileAnnotations(TestPage po)  {		
		Field elem1 = createField(po, "element1");
		AbstractFileAnnotations afa = createFileAnnotation(elem1);
		checkFileAnnotationIllegalAdditionalExcep(afa);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testMissingFindByFileAnnotation()  {
		Field spyElem = createValidSpyField();
		Mockito.when(spyElem.getAnnotation(getFileAnnotationsClass())).thenReturn(null);
		AbstractFileAnnotations afa = createFileAnnotation(spyElem);		
		try {
			afa.assertValidAnnotations();
		} catch (Exception e) {
			fail("No Exception should be thrown.");
		}
	}
	
	protected void checkAdditionalFieldIllegalArgExcep(Consumer<Field> cons) {
		
		Field spyElem = createSpyField(createValidPageObject(), "element1");
		cons.accept(spyElem);
		AbstractFileAnnotations afa = createFileAnnotation(spyElem);
		try {
			afa.assertValidAnnotations();
			fail("Only @"+afa.getFieldAnnotationName()+" should be present.");
		} catch (Exception e) {
			assertNotEquals("Only @"+afa.getFileAnnotationName()+" should be present.", 
					IllegalArgumentException.class, e);
			assertEquals("Thrown message is wrong. ", "If you use a '@"+afa.getFieldAnnotationName()+"' annotation, "
					+ "you must not also use a '@FindBy' or '@FindBys' or '@FindAll' annotation", e.getMessage());
		}
	}
	
	@Test
	public void testAdditionalFindByAnnotation()  {		
		Consumer<Field> cons = f -> {
			Mockito.when(f.getAnnotation(FindBy.class)).thenReturn(Mockito.mock(FindBy.class));
		};		
		checkAdditionalFieldIllegalArgExcep(cons);
	}
	
	@Test
	public void testAdditionalFindBysAnnotation()  {
		Consumer<Field> cons = f -> {
			Mockito.when(f.getAnnotation(FindBys.class)).thenReturn(Mockito.mock(FindBys.class));
		};		
		checkAdditionalFieldIllegalArgExcep(cons);
	}
	
	@Test
	public void testAdditionalFindAllAnnotation()  {
		Consumer<Field> cons = f -> {
			Mockito.when(f.getAnnotation(FindAll.class)).thenReturn(Mockito.mock(FindAll.class));
		};		
		checkAdditionalFieldIllegalArgExcep(cons);
	}
	
	@Test
	public void testTwoAdditionalFindAnnotations()  {
		Consumer<Field> cons = f -> {
			Mockito.when(f.getAnnotation(FindBy.class)).thenReturn(Mockito.mock(FindBy.class));
			Mockito.when(f.getAnnotation(FindAll.class)).thenReturn(Mockito.mock(FindAll.class));
		};		
		checkAdditionalFieldIllegalArgExcep(cons);
	}
	
	@Test
	public void testAllAdditionalFindAnnotations()  {
		Consumer<Field> cons = f -> {
			Mockito.when(f.getAnnotation(FindBy.class)).thenReturn(Mockito.mock(FindBy.class));
			Mockito.when(f.getAnnotation(FindBys.class)).thenReturn(Mockito.mock(FindBys.class));
			Mockito.when(f.getAnnotation(FindAll.class)).thenReturn(Mockito.mock(FindAll.class));
		};		
		checkAdditionalFieldIllegalArgExcep(cons);
	}
	
	@Test
	public void testBuildByFindByPropertiesAnnotation()  {
		Field field = createField(createValidPageObject(), "element1");
		AbstractFileAnnotations afa = createFileAnnotation(field);
		By exBy = By.id("exampleId");
		FieldByCache.addDetail(field, exBy);	
		assertEquals("By value not returned correctly.", exBy, afa.buildBy());
	}
	
	@Test
	public void testBuildByMissingFieldFindByPropertiesAnnotation()  {
		Field field = createField(createValidPageObject(), "elementMissData");
		AbstractFileAnnotations afa = createFileAnnotation(field);
		try {
			afa.buildBy();
			fail(field.getName() + " field data is not present in data file.");
		} catch (Exception e) {
			assertNotEquals(field.getName() + " field data is not present in data file.", 
					IllegalArgumentException.class, e);
			assertEquals("Thrown message is wrong.", field.getName() + " locator data for @"+ afa.getFieldAnnotationName() + 
					" is not available in the data file at the path mentioned in @"+ afa.getFileAnnotationName()+".", 
					e.getMessage());
		}
	}
	
	@Test
	public void testBuildByFindByAnnotation()  {
		Field field = createField(createValidPageObject(), "element2");
		AbstractFileAnnotations afa = createFileAnnotation(field);
		assertEquals("By value not returned correctly.", By.id("exampleId"), afa.buildBy());
	}
	
}
