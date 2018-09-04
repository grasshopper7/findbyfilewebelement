package file.pagefactory;

import java.lang.reflect.Field;
import java.time.Instant;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import file.properties.pagefactory.FindByProperties;
import file.properties.pagefactory.PropertiesAnnotation;
import file.properties.pagefactory.PropertiesFile;
import file.properties.pagefactory.PropertiesFileProcessor;

public class MultipleThreadCacheTest {
	
	@Test
	public void testMultipleAccess() throws InterruptedException {
		
		FileAccessRun far = new FileAccessRun();
		Thread t1 = (new Thread(far));
		t1.start();
		
		//t1.join(2000);
		
		
		FileAccessRunSec far1 = new FileAccessRunSec();
		Thread t2 =  (new Thread(far1));
		t2.start();
		
		
		//t1.join(1000);
		//t2.join(1000);
		
		Thread.sleep(5000);
				
	}
	
	public class FileAccessRun implements Runnable {

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getId() + "--Start--"+Instant.now());
			PageObject po = new PageObject();
			try {
				Field elem1 = po.getClass().getDeclaredField("element2");
				PropertiesAnnotation pa = new PropertiesAnnotation(elem1, new PropertiesFileProcessor());
				pa.buildBy();
				System.out.println(Thread.currentThread().getId() + "---"+FieldByCache.lastUpdatedInstant());
				FieldByCache.printCache();
			} catch (NoSuchFieldException | SecurityException e) {
				throw new RuntimeException(e);
			}			
		}
	}
	
	public class FileAccessRunSec implements Runnable {

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getId() + "--Start--"+Instant.now());
			PageObject po = new PageObject();
			try {
				Field elem1 = po.getClass().getDeclaredField("element2");
				PropertiesAnnotation pa = new PropertiesAnnotation(elem1, new PropertiesFileProcessor());
				pa.buildBy();
				System.out.println(Thread.currentThread().getId() + "---"+FieldByCache.lastUpdatedInstant());
				FieldByCache.printCache();
			} catch (NoSuchFieldException | SecurityException e) {
				throw new RuntimeException(e);
			}			
		}
	}
	
	@PropertiesFile(filePath = "src/test/resources/properties/MultipleAccessData.properties")
	public class PageObject {
		@FindByProperties
		private WebElement element1;
		@FindByProperties
		private WebElement element2;
	}

}
