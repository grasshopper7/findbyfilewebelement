package file.pagefactory.json;

import java.lang.reflect.Field;

import org.openqa.selenium.By;

import file.pagefactory.AbstractFileAnnotations;
import file.pagefactory.FileProcessor;
import file.pagefactory.excel.ExcelFile;
import file.pagefactory.excel.FindByExcel;
import file.pagefactory.properties.FindByProperties;
import file.pagefactory.properties.PropertiesFile;

public class JsonAnnotation extends AbstractFileAnnotations {

	public JsonAnnotation(Field field, FileProcessor fileProcessor) {
		super(field, fileProcessor);
	}

	public By buildBy() {
		assertValidAnnotations();
		return super.buildBy(getField().getAnnotation(FindByJson.class) != null);
	}

	protected void assertValidAnnotations() {
		FindByJson findJson = getField().getAnnotation(FindByJson.class);
		JsonFile jsonFile = getField().getDeclaringClass().getAnnotation(JsonFile.class);

		if(findJson != null && jsonFile == null)
			throw new IllegalArgumentException("@" + getFileAnnotationName() + " annotation is missing on class level.");
		
		if(getField().getDeclaringClass().getAnnotation(ExcelFile.class) != null || 
				getField().getDeclaringClass().getAnnotation(PropertiesFile.class) != null)
			throw new IllegalArgumentException("Only @"+getFileAnnotationName()+ " annotation is allowed on class level.");
		
		if(findJson != null && (getField().getAnnotation(FindByExcel.class) != null || 
				getField().getAnnotation(FindByProperties.class) != null))
			throw new IllegalArgumentException("Only @"+getFieldAnnotationName()+ " annotation is allowed on field level.");
				
		super.assertValidAnnotations(findJson != null, jsonFile != null);
	}

	@Override
	public String getFieldAnnotationName() {		
		return FindByJson.class.getSimpleName();
	}

	@Override
	public String getFileAnnotationName() {
		return JsonFile.class.getSimpleName();
	}
	
	public static String getFindByAnnotationFullName() {
		return FindByJson.class.getName();
	}
}
