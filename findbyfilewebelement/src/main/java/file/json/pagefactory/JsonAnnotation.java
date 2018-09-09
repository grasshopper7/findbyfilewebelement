package file.json.pagefactory;

import java.lang.reflect.Field;

import org.openqa.selenium.By;

import file.pagefactory.AbstractFileAnnotations;
import file.pagefactory.FileProcessor;
import file.properties.pagefactory.FindByProperties;

public class JsonAnnotation extends AbstractFileAnnotations {

	public JsonAnnotation(Field field, FileProcessor fileProcessor) {
		super(field, fileProcessor);
	}

	public By buildBy() {
		return super.buildBy(getField().getAnnotation(FindByJson.class) != null);
	}

	protected void assertValidAnnotations() {
		FindByJson findJson = getField().getAnnotation(FindByJson.class);
		JsonFile jsonFile = getField().getDeclaringClass().getAnnotation(JsonFile.class);

		if(jsonFile == null)
			throw new IllegalArgumentException("@" + getFileAnnotationName() + " annotation is missing on class level.");
		
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
