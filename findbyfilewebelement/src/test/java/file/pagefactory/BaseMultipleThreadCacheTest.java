package file.pagefactory;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BaseMultipleThreadCacheTest {
	/*
	 * Tests
	 * - Two threads of same file processor type at almost same time updating same page object data, repeat for other 2 types.
	 * - Two threads of same file processor type at different time updating same page object data, repeat for other 2 types.
	 * - Two threads of same file processor type at almost same time updating different page object data from same file, repeat for other 2 types.
	 * - Two threads of same file processor type at almost same time updating different page object data from different file, repeat for other 2 types.
	 * - Two threads of same file processor type at different time updating different page object data from same file, repeat for other 2 types.
	 * - Two threads of same file processor type at different time updating different page object data from different file, repeat for other 2 types.
	 * 
	 * - Three threads of different file processor type at almost same time updating same page object data - Check in assertvalidannot to address this.
	 * - Three threads of different file processor type at almost same time updating different page object data.
	 * 
	 * - Three threads of different file processor type at different time updating same page object data - Check in assertvalidannot to address this.
	 * - Three threads of different file processor type at different time updating different page object data.
	 * 
	 * -- OTHER TEST CASE
	 * - Three threads of different file processor type at almost same time updating different page object data with some standard @FindBy fields.
	 * - Two threads. Two PageObject. Two data file. One file has fields of one PO and some of second PO. Second file has other fields of second PO.
	 */

	@Before
	public void setup() {
		System.out.println("--------");
		FieldByCache.removeDetails();
	}
	
	public void createThreadsAssertCache(FileProcessor[] fp, TestPage[] pages, boolean differentPageObject,
			boolean differentTime,Integer[] checkCalls, Integer[] parseCalls, String[] findByAnnotation) 
					throws InterruptedException {
		
		Thread[] threads = new Thread[fp.length];
		
		for(int i = 0;i < fp.length;i++) {
			threads[i] = fileThread(fp[i], pages[i]);
		}
		
		threads[0].start();		
		for(int i = 1;i < threads.length;i++) {
			if(differentTime)
				Thread.sleep(2000);
			threads[i].start();
		}
		
		for(int i = 0;i < threads.length;i++) {
			threads[i].join(5000);
		}
		
		for(int i = 0;i < threads.length;i++) {
			Mockito.verify(fp[i], Mockito.times(checkCalls[i])).checkAndCallParseDataSource(Mockito.any(Field.class));
	
			if (differentTime) {
				Mockito.verify(fp[i], Mockito.times(parseCalls[i])).parseDataSource();
			} else {
				// Need a better check to get exact counts
				Mockito.verify(fp[i], Mockito.atMost(parseCalls[i])).parseDataSource();
			}	
		}
		checkFieldDataInCache(pages, findByAnnotation);

	}
	
	@SuppressWarnings("unchecked")
	private void checkFieldDataInCache(TestPage[] pages, String[] findby) {
		int count = 0;
		@SuppressWarnings("rawtypes")
		Set<Class> classDetails = new HashSet<>();
		try {
			for(int i = 0; i < pages.length ; i ++) {
				TestPage page = pages[i];
				if(classDetails.add(page.getClass())) {
					@SuppressWarnings("rawtypes")
					Class annot = Class.forName(findby[i]);
					for (Field field : page.getClass().getDeclaredFields()) {
						if (field.getAnnotation(annot) != null) {
							count++;
							Assert.assertTrue(field.getName() + " of class " + page.getClass() + " is not present in cache",
									FieldByCache.doesByExistForField(field));
						}
					}
				}
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		Assert.assertEquals("Locator Data has not been parsed properly.", count, FieldByCache.size());
	}
	
	private Thread fileThread(FileProcessor pfp, TestPage testPage) {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				WebDriver driver = Mockito.mock(WebDriver.class);
				FileElementLocatorFactory felf = new FileElementLocatorFactory(driver, pfp);
				PageFactory.initElements(felf, testPage);
			}
		});
	}
}
