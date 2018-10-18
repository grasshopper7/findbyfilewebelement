package file.pagefactory.properties;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.support.pagefactory.Annotations;

import file.pagefactory.ByCreator;
import file.pagefactory.FieldByCache;
import file.pagefactory.FileProcessor;

public class PropertiesFileProcessor implements FileProcessor {

	private String path;
	private String delimiter;

	@Override
	public void dataSourceDetails(Field field) {

		PropertiesFile propsFile = field.getDeclaringClass().getAnnotation(PropertiesFile.class);
		path = propsFile.filePath();
		delimiter = propsFile.delimiter();
	}
	
	@Override
	public void parseDataSource() {
		Properties appProps = new Properties();
		//System.out.println(Thread.currentThread().getId() + "---" + "Processing Properties");
		try (FileInputStream fis = new FileInputStream(path);) {
			appProps.load(fis);
			Set<Object> keys = appProps.keySet();

			for (Object key : keys) {
				String[] keyDets = key.toString().split(delimiter);
				String[] valDets = appProps.get(key).toString().split(delimiter);

				if (keyDets.length != 2 || valDets.length != 2)
					throw new ParseException("Delimiter not present or incorrect in key or value in line - "
							+ key.toString() + "=" + appProps.get(key).toString(), 0);

				Class<?> pkgCls = Class.forName(keyDets[0]);

				FieldByCache.addDetail(pkgCls.getDeclaredField(keyDets[1]),
						ByCreator.createBy(valDets[0].toUpperCase(), valDets[1]));
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Annotations getAnnotation(Field field) {
		return new PropertiesAnnotation(field, this);
	}
}
