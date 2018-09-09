package file.excel.pagefactory;

import java.lang.reflect.Field;

import org.openqa.selenium.By;

import file.json.pagefactory.FindByJson;
import file.pagefactory.AbstractFileAnnotations;
import file.pagefactory.FileProcessor;

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
