package file.pagefactory.properties;

import java.lang.reflect.Field;

import org.openqa.selenium.By;

import file.pagefactory.AbstractFileAnnotations;
import file.pagefactory.FileProcessor;
import file.pagefactory.excel.ExcelFile;
import file.pagefactory.excel.FindByExcel;
import file.pagefactory.json.FindByJson;
import file.pagefactory.json.JsonFile;

public class PropertiesAnnotation extends AbstractFileAnnotations {

	public PropertiesAnnotation(Field field, FileProcessor fileProcessor) {
		super(field, fileProcessor);
	}

	public By buildBy() {
		assertValidAnnotations();
		return super.buildBy(getField().getAnnotation(FindByProperties.class) != null);
	}

	protected void assertValidAnnotations() {

		FindByProperties findProps = getField().getAnnotation(FindByProperties.class);
		PropertiesFile propsFile = getField().getDeclaringClass().getAnnotation(PropertiesFile.class);
		
		if(findProps != null && propsFile == null)
			throw new IllegalArgumentException("@"+getFileAnnotationName()+ " annotation is missing on class level.");
		
		if(getField().getDeclaringClass().getAnnotation(ExcelFile.class) != null || 
				getField().getDeclaringClass().getAnnotation(JsonFile.class) != null)
			throw new IllegalArgumentException("Only @"+getFileAnnotationName()+ " annotation is allowed on class level.");
		
		if(findProps != null && (getField().getAnnotation(FindByExcel.class) != null || 
				getField().getAnnotation(FindByJson.class) != null))
			throw new IllegalArgumentException("Only @"+getFieldAnnotationName()+ " annotation is allowed on field level.");
		
		super.assertValidAnnotations(findProps != null, propsFile != null);
	}

	@Override
	public String getFieldAnnotationName() {
		return FindByProperties.class.getSimpleName();
	}

	@Override
	public String getFileAnnotationName() {
		return PropertiesFile.class.getSimpleName();
	}
	
	public static String getFindByAnnotationFullName() {
		return FindByProperties.class.getName();
	}

}
