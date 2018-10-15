package file.pagefactory;

import java.lang.reflect.Field;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.Annotations;

public abstract class AbstractFileAnnotations extends Annotations {

	protected FileProcessor fileProcessor;
	private static Object obj = new Object();


	public AbstractFileAnnotations(Field field, FileProcessor fileProcessor) {
		super(field);
		this.fileProcessor = fileProcessor;
	}
	
	public abstract String getFieldAnnotationName();
	
	public abstract String getFileAnnotationName();
		
	protected abstract void assertValidAnnotations();

	public By buildBy(boolean fieldAnnotationExists) {
		//assertValidAnnotations();
		if (fieldAnnotationExists) {
			System.out.println(Thread.currentThread().getId() + "---" + "Before initial check data "+getField().getName());
			if (!FieldByCache.doesByExistForField(getField())) {
				System.out.println(Thread.currentThread().getId() + "---" + "Initial check data failed "+getField().getName());
				synchronized (obj) {
					System.out.println(Thread.currentThread().getId() + "---" + "Synchronize data In here");
					fileProcessor.populateData(getField());			
				}
			}
			if (!FieldByCache.doesByExistForField(getField()))
				throw new IllegalArgumentException(getField().getName() + " locator data for @" + getFieldAnnotationName() + 
						" is not available in the data file at the path mentioned in @" + getFileAnnotationName() +".");
			return FieldByCache.getByForField(getField());
		}
		return super.buildBy();
	}

	protected void assertValidAnnotations(boolean fieldAnnotationExists, boolean fileAnnotationExists) {
		FindBys findBys = getField().getAnnotation(FindBys.class);
		FindAll findAll = getField().getAnnotation(FindAll.class);
		FindBy findBy = getField().getAnnotation(FindBy.class);
		

		if (fieldAnnotationExists && !fileAnnotationExists) {
			throw new IllegalArgumentException("'@" + getFieldAnnotationName() + "' annotation must be use together with a "
					+ "'@" + getFileAnnotationName() + "' annotation");
		}
		
		if (fieldAnnotationExists && (findBys != null || findAll != null || findBy != null)) {
			throw new IllegalArgumentException("If you use a '@" + getFieldAnnotationName() + "' annotation, "
					+ "you must not also use a '@FindBy' or '@FindBys' or '@FindAll' annotation");
		}

		super.assertValidAnnotations();
	}
}
