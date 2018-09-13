package file.pagefactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;

import file.pagefactory.excel.FindByExcel;
import file.pagefactory.json.FindByJson;
import file.pagefactory.properties.FindByProperties;

public class FileFieldDecorator extends DefaultFieldDecorator {

	public FileFieldDecorator(FileElementLocatorFactory factory) {
		super(factory);
	}

	protected boolean isDecoratableList(Field field) {
		if (!List.class.isAssignableFrom(field.getType())) {
			return false;
		}

		// Type erasure in Java isn't complete. Attempt to discover the generic
		// type of the list.
		Type genericType = field.getGenericType();
		if (!(genericType instanceof ParameterizedType)) {
			return false;
		}

		Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];

		if (!WebElement.class.equals(listType)) {
			return false;
		}

		if (field.getAnnotation(FindByProperties.class) == null && 
				field.getAnnotation(FindByExcel.class) == null	&& 
				field.getAnnotation(FindByJson.class) == null) {
			return super.isDecoratableList(field);
		}

		return true;
	}
}
