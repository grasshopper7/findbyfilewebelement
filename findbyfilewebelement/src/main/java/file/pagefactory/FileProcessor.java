package file.pagefactory;

import java.lang.reflect.Field;

import org.openqa.selenium.support.pagefactory.Annotations;

public interface FileProcessor {

	void dataSourceDetails(Field field);

	default void checkAndCallParseDataSource(Field field) {
		System.out.println(Thread.currentThread().getId() + "---" + "Check data In here");
		// If data is got from previous parsing then return.
		if (FieldByCache.doesByExistForField(field))
			return;
		parseDataSource();
	}
	
	void parseDataSource();

	Annotations getAnnotation(Field field);

	default void populateData(Field field) {

		dataSourceDetails(field);

		checkAndCallParseDataSource(field);
	}

}
