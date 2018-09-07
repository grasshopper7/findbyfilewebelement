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

public class JsonFileProcessor implements FileProcessor {

	private String path;

	@Override
	public void dataSourceDetails(Field field) {
		JsonFile xlsFile = field.getDeclaringClass().getAnnotation(JsonFile.class);
		path = xlsFile.filePath();
	}

	@Override
	public void parseDataSource(/*Field field*/) {

		// If data is got from previous parsing then return.
		/*if (FieldByCache.doesByExistForField(field))
			return;*/
		System.out.println(Thread.currentThread().getId() + "---" + "Processing Json");
		Type FIELD_BY_DETAILS = new TypeToken<List<ClassDetails>>() {}.getType();
		Gson gson = new Gson();

		try(JsonReader reader = new JsonReader(new FileReader(path));) {
			List<ClassDetails> data = gson.fromJson(reader, FIELD_BY_DETAILS);

			for (ClassDetails detail : data) {
				ClassDetails.checkValues(detail);
				Class<?> pkgCls = Class.forName(detail.getClassName());

				for (FieldByDetails fbdet : detail.getFieldBy()) {
					FieldByDetails.checkValues(fbdet);
					FieldByCache.addDetail(pkgCls.getDeclaredField(fbdet.getField()),
							ByCreator.createBy(fbdet.getHow(), fbdet.getUsing()));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Annotations getAnnotation(Field field) {
		return new JsonAnnotation(field, this);
	}

}
