package file.pagefactory;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.By;

public class FieldByCache {

	private static ConcurrentHashMap<Field, By> fieldByStore = new ConcurrentHashMap<>();
	
	private static Instant lastUpdated = Instant.now();
	
	public static By getByForField(Field field) {
		return fieldByStore.get(field);
	}
	
	public static boolean doesByExistForField(Field field) {
		return fieldByStore.containsKey(field);
	}
	
	public static void addDetail(Field field, By by) {
		fieldByStore.put(field, by);
		lastUpdated = Instant.now();
		System.out.println(Thread.currentThread().getId() + "--Updating--");
		System.out.println("Adding");
	}
	
	//Is this needed?
	/*public static void removeDetails() {
		fieldByStore.clear();
	}*/
	
	public static int size() {
		return fieldByStore.size();
	}
	
	public static Instant lastUpdatedInstant() {
		return Instant.ofEpochSecond(lastUpdated.getEpochSecond(), lastUpdated.getNano());
	}
	
	public static void printCache() {
		fieldByStore.forEach((key,value) -> {
			System.out.println(key.toString() + " -- " +value.toString());
		});
	}
	
}
