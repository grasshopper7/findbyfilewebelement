package file.properties.pagefactory;

import java.io.FileInputStream;
import java.lang.reflect.Field;
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
		delimiter = propsFile.delmiter();
	}

	@Override
	public void parseDataSource() {
		try {
			Properties appProps = new Properties();

			appProps.load(new FileInputStream(path));
			Set<Object> keys = appProps.keySet();
			
			for(Object key : keys) {
				String[] keyDets = key.toString().split(delimiter);
				String[] valDets = appProps.get(key).toString().split(delimiter);

				Class<?> pkgCls = Class.forName(keyDets[0]);
				
				FieldByCache.addDetail(pkgCls.getDeclaredField(keyDets[1]), 
						ByCreator.createBy(valDets[0], valDets[1]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 		
	}

	@Override
	public Annotations getAnnotation(Field field) {
		return new PropertiesAnnotation(field, this);
	}
}
