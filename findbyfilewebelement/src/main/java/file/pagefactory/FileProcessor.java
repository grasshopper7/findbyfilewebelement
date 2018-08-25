package file.pagefactory;

import java.lang.reflect.Field;

import org.openqa.selenium.support.pagefactory.Annotations;

public interface FileProcessor {

	void dataSourceDetails(Field field);

	void parseDataSource();

	Annotations getAnnotation(Field field);

	default void populateData(Field field) {

		dataSourceDetails(field);

		parseDataSource();
	}

}
