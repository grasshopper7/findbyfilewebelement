package file.pagefactory;

import java.lang.reflect.Field;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.Annotations;

public abstract class AbstractFileAnnotations extends Annotations {

	protected FileProcessor fileProcessor;

	public AbstractFileAnnotations(Field field, FileProcessor fileProcessor) {
		super(field);
		this.fileProcessor = fileProcessor;
	}

	public By buildBy(boolean fieldAnnotationExists) {
		assertValidAnnotations();
		if (fieldAnnotationExists) {
			if (!FieldByCache.doesByExistForField(getField()))
				fileProcessor.populateData(getField());
			return FieldByCache.getByForField(getField());
		}
		return super.buildBy();
	}

	protected void assertValidAnnotations(boolean fieldAnnotationExists, boolean fileAnnotationExists,
			String fieldAnnotationName, String fileAnnotationName) {
		FindBys findBys = getField().getAnnotation(FindBys.class);
		FindAll findAll = getField().getAnnotation(FindAll.class);
		FindBy findBy = getField().getAnnotation(FindBy.class);

		if (!(fieldAnnotationExists && fileAnnotationExists)) {
			throw new IllegalArgumentException("'@" + fieldAnnotationName + "' annotation must be use together with a "
					+ "'@" + fileAnnotationName + "' annotation");
		}
		
		if (fieldAnnotationExists && (findBys != null || findAll != null || findBy != null)) {
			throw new IllegalArgumentException("If you use a '@" + fieldAnnotationName + "' annotation, "
					+ "you must not also use a '@FindBy' or '@FindBys' or '@FindAll' annotation");
		}

		super.assertValidAnnotations();
	}
}
