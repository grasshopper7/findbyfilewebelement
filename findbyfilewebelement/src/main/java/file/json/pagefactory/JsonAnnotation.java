package file.json.pagefactory;

import java.lang.reflect.Field;

import org.openqa.selenium.By;

import file.pagefactory.AbstractFileAnnotations;
import file.pagefactory.FileProcessor;

public class JsonAnnotation extends AbstractFileAnnotations {

	private String fieldAnnotationName = FindByJson.class.getSimpleName();
	private String fileAnnotationName = JsonFile.class.getSimpleName();

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
			throw new IllegalArgumentException("@" + fileAnnotationName + "annotation is missing on class level.");
		
		super.assertValidAnnotations(findJson != null, jsonFile != null, fieldAnnotationName, fileAnnotationName);
	}
}
