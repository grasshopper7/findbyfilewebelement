package file.json.pagefactory;

import java.lang.reflect.Field;

import org.openqa.selenium.By;

import file.pagefactory.AbstractFileAnnotations;
import file.pagefactory.FileProcessor;

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

		super.assertValidAnnotations(findJson != null, jsonFile != null, 
				findJson.getClass().getSimpleName(), jsonFile.getClass().getSimpleName());
	}
}
