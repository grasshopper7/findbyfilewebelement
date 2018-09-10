package file.pagefactory.excel;

import java.lang.reflect.Field;

import org.openqa.selenium.By;

import file.pagefactory.AbstractFileAnnotations;
import file.pagefactory.FileProcessor;
import file.pagefactory.json.FindByJson;
import file.pagefactory.json.JsonFile;
import file.pagefactory.properties.FindByProperties;
import file.pagefactory.properties.PropertiesFile;

public class ExcelAnnotation extends AbstractFileAnnotations {
	
	public ExcelAnnotation(Field field, FileProcessor fileProcessor) {
		super(field, fileProcessor);
	}

	public By buildBy() {
		return super.buildBy(getField().getAnnotation(FindByExcel.class) != null);
	}

	protected void assertValidAnnotations() {

		FindByExcel findExcel = getField().getAnnotation(FindByExcel.class);
		ExcelFile excelFile = getField().getDeclaringClass().getAnnotation(ExcelFile.class);
		
		if(excelFile == null)
			throw new IllegalArgumentException("@" + getFileAnnotationName() + " annotation is missing on class level.");
		
		if(getField().getDeclaringClass().getAnnotation(JsonFile.class) != null || 
				getField().getDeclaringClass().getAnnotation(PropertiesFile.class) != null)
			throw new IllegalArgumentException("Only @"+getFileAnnotationName()+ " annotation is allowed on class level.");
		
		if(findExcel != null && (getField().getAnnotation(FindByJson.class) != null || 
				getField().getAnnotation(FindByProperties.class) != null))
			throw new IllegalArgumentException("Only @"+getFieldAnnotationName()+ " annotation is allowed on field level.");
		
		super.assertValidAnnotations(findExcel != null, excelFile != null);
	}

	@Override
	public String getFieldAnnotationName() {
		return FindByExcel.class.getSimpleName();
	}

	@Override
	public String getFileAnnotationName() {
		return ExcelFile.class.getSimpleName();
	}
	
	public static String getFindByAnnotationFullName() {
		return FindByExcel.class.getName();
	}
}
