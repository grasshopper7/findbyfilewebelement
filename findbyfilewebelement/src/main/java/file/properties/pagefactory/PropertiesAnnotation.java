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
		System.out.println(Thread.currentThread().getId() + "---" + "PROPS ANNOT --- " + getField().getName());
		return super.buildBy(getField().getAnnotation(FindByProperties.class) != null);
	}

	protected void assertValidAnnotations() {

		FindByProperties findProps = getField().getAnnotation(FindByProperties.class);
		PropertiesFile propsFile = getField().getDeclaringClass().getAnnotation(PropertiesFile.class);
		
		if(propsFile == null)
			throw new IllegalArgumentException("@"+getFileAnnotationName()+ " annotation is missing on class level.");

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
