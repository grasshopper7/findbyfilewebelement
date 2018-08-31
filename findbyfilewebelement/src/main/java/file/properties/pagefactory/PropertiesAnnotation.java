package file.properties.pagefactory;

import java.lang.reflect.Field;

import org.openqa.selenium.By;

import file.pagefactory.AbstractFileAnnotations;
import file.pagefactory.FileProcessor;

public class PropertiesAnnotation extends AbstractFileAnnotations {

	public PropertiesAnnotation(Field field, FileProcessor fileProcessor) {
		super(field, fileProcessor);
	}

	public By buildBy() {
		return super.buildBy(getField().getAnnotation(FindByProperties.class) != null);
	}

	protected void assertValidAnnotations() {

		FindByProperties findProps = getField().getAnnotation(FindByProperties.class);
		PropertiesFile propsFile = getField().getDeclaringClass().getAnnotation(PropertiesFile.class);

		super.assertValidAnnotations(findProps != null, propsFile != null, 
				findProps.getClass().getSimpleName(), propsFile.getClass().getSimpleName());
	}
}
