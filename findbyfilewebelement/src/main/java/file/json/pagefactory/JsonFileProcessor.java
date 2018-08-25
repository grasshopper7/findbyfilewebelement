package file.json.pagefactory;

import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

import org.openqa.selenium.support.pagefactory.Annotations;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import file.pagefactory.ByCreator;
import file.pagefactory.FieldByCache;
import file.pagefactory.FileProcessor;

public class JsonFileProcessor implements FileProcessor{

	private String path;
	
	@Override
	public void dataSourceDetails(Field field) {
		JsonFile xlsFile = field.getDeclaringClass().getAnnotation(JsonFile.class);
		path = xlsFile.filePath();
	}

	@Override
	public void parseDataSource() {
		
		try {
			Type FIELD_BY_DETAILS = new TypeToken<List<ClassDetails>>(){}.getType();			
			Gson gson = new Gson();
			JsonReader reader = new JsonReader(new FileReader(path));	
			
			List<ClassDetails> data = gson.fromJson(reader, FIELD_BY_DETAILS);
			
			for(ClassDetails detail : data) {				
				Class<?> pkgCls = Class.forName(detail.getClassName()); 
				
				for(FieldByDetails fbdet : detail.getFieldBy()) {
					FieldByCache.addDetail(pkgCls.getDeclaredField(fbdet.getField()), 
							ByCreator.createBy(fbdet.getHow(), fbdet.getUsing()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public Annotations getAnnotation(Field field) {
		return new JsonAnnotation(field,this);
	}
}
