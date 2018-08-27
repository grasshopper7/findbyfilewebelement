package file.pagefactory;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.By;

public class FieldByCache {

	private static ConcurrentHashMap<Field, By> fieldByStore = new ConcurrentHashMap<>();
	
	public static By getByForField(Field field) {
		return fieldByStore.get(field);
	}
	
	public static boolean doesByExistForField(Field field) {
		return fieldByStore.containsKey(field);
	}
	
	public static void addDetail(Field field, By by) {
		fieldByStore.put(field, by);
	}
	
	public static void removeDetails() {
		fieldByStore.clear();
	}
	
}
