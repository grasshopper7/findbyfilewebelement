package file.pagefactory;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
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
	 * - Three threads of different file processor type at almost same time updating different page object data with standard @FindBy fields.(COMP)
	 * - Two threads. Two PageObject. Two data file. One file has fields of one PO and some of second PO. Second file has other fields of second PO.(?)
	 * - Valid PageFactory initialization without mock test.(COMP)
	 * - Valid By options test
	 */

	@Before
	public void setup() {
		FieldByCache.removeDetails();
	}
	
	protected void createThreadsAssertCache(FileProcessor[] fp, TestPage[] pages, boolean differentPageObject,
			boolean differentTime,Integer[] checkCalls, Integer[] parseCalls, String[] findByAnnotation) 
					throws InterruptedException {
		
		Thread[] threads = new Thread[fp.length];
		
		for(int i = 0;i < fp.length;i++) {
			threads[i] = fileThread(fp[i], pages[i]);
		}
		
		threads[0].start();		
		for(int i = 1;i < threads.length;i++) {
			if(differentTime)
				Thread.sleep(4000);
			threads[i].start();
		}
		
		for(int i = 0;i < threads.length;i++) {
			threads[i].join(10000);
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
	
	protected void checkFieldDataInCache(TestPage[] pages, String[] findby) {
		int count = 0;
		@SuppressWarnings("rawtypes")
		Set<Class> classDetails = new HashSet<>();
		try {
			for(int i = 0; i < pages.length ; i ++) {
				TestPage page = pages[i];
				if(classDetails.add(page.getClass())) {
					@SuppressWarnings("rawtypes")
					Class annot = Class.forName(findby[i]);
					count = count + checkFieldData(page, annot);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Assert.assertEquals("Locator Data has not been parsed properly.", count, FieldByCache.size());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected int checkFieldData(TestPage page, Class annot ) throws IllegalArgumentException, IllegalAccessException {
		int count= 0;
		for (Field field : page.getClass().getDeclaredFields()) {
			//Made inner class TestPage implementations static to avoid the this$0 reference
			//Inner class fields are private. Can make them public to avoid this.
			field.setAccessible(true);
			//One can add FindBys and FindAll also if added in PageObject - normal fields
			if(field.getAnnotation(annot) == null && field.getAnnotation(FindBy.class) == null)
				Assert.assertNull(field.getName() + " of class " + page.getClass() + " should not be initialized.", field.get(page));
			//FindBy annotated field should be initialized,One can also add FindBys and FindAll
			if(field.getAnnotation(FindBy.class) != null)
				Assert.assertNotNull(field.getName() + " of class " + page.getClass() + " is not initialized.", field.get(page));
			//FindByFile annotated field details will be cached and not null.
			if (field.getAnnotation(annot) != null) {
				count++;
				Assert.assertNotNull(field.getName() + " of class " + page.getClass() + " is not initialized.", field.get(page));
				Assert.assertTrue(field.getName() + " of class " + page.getClass() + " is not present in cache.",
						FieldByCache.doesByExistForField(field));
				Assert.assertEquals("By value stored in cache for element " + field.getName() +" of class " + 
						page.getClass() + " is not correct.", By.id(field.getName()), FieldByCache.getByForField(field));
			}
		}
		return count;
	}
	
	protected Thread fileThread(FileProcessor pfp, TestPage testPage) {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				initializePage(pfp, testPage);
			}
		});
	}
	
	protected void initializePage(FileProcessor pfp, TestPage testPage) {
		WebDriver driver = Mockito.mock(WebDriver.class);
		FileElementLocatorFactory felf = new FileElementLocatorFactory(driver, pfp);
		FileFieldDecorator ffd = new FileFieldDecorator(felf);
		PageFactory.initElements(ffd, testPage);
	}
}
