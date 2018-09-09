package file.pagefactory;

import java.lang.reflect.Field;
import java.time.Instant;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import file.pagefactory.excel.ExcelAnnotation;
import file.pagefactory.excel.ExcelFile;
import file.pagefactory.excel.ExcelFileProcessor;
import file.pagefactory.excel.FindByExcel;
import file.pagefactory.properties.FindByProperties;
import file.pagefactory.properties.PropertiesAnnotation;
import file.pagefactory.properties.PropertiesFile;
import file.pagefactory.properties.PropertiesFileProcessor;

public class ThreadingTest {
	
	
	@Test
	public void testMultipleAccess() throws InterruptedException {
		
		FileAccessPropertiesRun far = new FileAccessPropertiesRun();
		Thread t1 = (new Thread(far));
		t1.start();		
		
		FileAccessPropertiesRunSec far1 = new FileAccessPropertiesRunSec();
		Thread t2 =  (new Thread(far1));
		t2.start();
		
		FileAccessExcelRun far2 = new FileAccessExcelRun();
		Thread t3 = (new Thread(far2));
		t3.start();
		
		FileAccessExcelRunSec far3 = new FileAccessExcelRunSec();
		Thread t4 = (new Thread(far3));
		t4.start();
			
		t1.join(3000);
		t2.join(3000);
		t3.join(5000);
		t4.join(5000);
		
		//Thread.sleep(10000);
		
		System.out.println(FieldByCache.size());
	}
	
	public class FileAccessPropertiesRun implements Runnable {

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getId() + "--Start--"+Instant.now());
			PageObjectProperties po = new PageObjectProperties();
			try {
				Field elem1 = po.getClass().getDeclaredField("element1");
				PropertiesAnnotation pa = new PropertiesAnnotation(elem1, new PropertiesFileProcessor());
				pa.buildBy();
				FieldByCache.printCache();
			} catch (NoSuchFieldException | SecurityException e) {
				throw new RuntimeException(e);
			}			
		}
	}
	
	public class FileAccessPropertiesRunSec implements Runnable {

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getId() + "--Start--"+Instant.now());
			PageObjectProperties po = new PageObjectProperties();
			try {
				Field elem1 = po.getClass().getDeclaredField("element2");
				PropertiesAnnotation pa = new PropertiesAnnotation(elem1, new PropertiesFileProcessor());
				pa.buildBy();
				FieldByCache.printCache();
			} catch (NoSuchFieldException | SecurityException e) {
				throw new RuntimeException(e);
			}			
		}
	}
	
	public class FileAccessExcelRun implements Runnable {

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getId() + "--Start--"+Instant.now());
			PageObjectExcel po = new PageObjectExcel();
			try {
				Field elem1 = po.getClass().getDeclaredField("element1");
				ExcelAnnotation pa = new ExcelAnnotation(elem1, new ExcelFileProcessor());
				pa.buildBy();
				FieldByCache.printCache();
			} catch (NoSuchFieldException | SecurityException e) {
				throw new RuntimeException(e);
			}			
		}
	}
	
	public class FileAccessExcelRunSec implements Runnable {

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getId() + "--Start--"+Instant.now());
			PageObjectExcel po = new PageObjectExcel();
			try {
				Field elem1 = po.getClass().getDeclaredField("element2");
				ExcelAnnotation pa = new ExcelAnnotation(elem1, new ExcelFileProcessor());
				pa.buildBy();
				FieldByCache.printCache();
			} catch (NoSuchFieldException | SecurityException e) {
				throw new RuntimeException(e);
			}			
		}
	}
	
	@PropertiesFile(filePath = "src/test/resources/properties/MultipleAccessData.properties")
	public class PageObjectProperties {
		@FindByProperties
		private WebElement element1;
		@FindByProperties
		private WebElement element2;
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/MultipleAccessData.xlsx")
	public class PageObjectExcel {
		@FindByExcel
		private WebElement element1;
		@FindByExcel
		private WebElement element2;
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/MultipleAccessData.xlsx")
	public class PageObjectExcelSec {
		@FindByExcel
		private WebElement element1;
		@FindByExcel
		private WebElement element2;
	}

}
