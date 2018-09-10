package file.pagefactory.excel;

import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebElement;

import file.pagefactory.BaseMultipleThreadCacheTest;
import file.pagefactory.FileProcessor;
import file.pagefactory.TestPage;

public class ExcelFPMultipleThreadCacheTest extends BaseMultipleThreadCacheTest{

	// Two threads of Excel file processor at same time updating same
	// page object data	
	@Test()
	public void twoSameTimeExcelFileThreadsWithSamePageObject()
			throws NoSuchFieldException, SecurityException, InterruptedException {

		// Second thread will be not enter the if condition in AbstractFileAnnotations
		// buildBy(boolean) method and return. This will not enter the
		// ExcelFileProcessor
		// checkAndCallParseDataSource(Field).

		ExcelFileProcessor pfp1 = Mockito.spy(ExcelFileProcessor.class);
		ExcelFileProcessor pfp2 = Mockito.spy(ExcelFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectExcelFirst(), new PageObjectExcelFirst() };
		Integer[] checkCalls = { 1, 1 };
		Integer[] parseCalls = { 1, 1 };
		String[] annotations = { ExcelAnnotation.getFindByAnnotationFullName(),
				ExcelAnnotation.getFindByAnnotationFullName()};

		createThreadsAssertCache(fp, tp, false, false, checkCalls, parseCalls,annotations);
	}
	
	// Two threads of Excel file processor at different time updating same
	// page object data
	@Test()
	public void twoDifferentTimeExcelFileThreadsWithSamePageObject()
			throws NoSuchFieldException, SecurityException, InterruptedException {

		// Second thread will be not enter the if condition in AbstractFileAnnotations
		// buildBy(boolean) method and return. This will not enter the
		// ExcelFileProcessor
		// checkAndCallParseDataSource(Field).

		ExcelFileProcessor pfp1 = Mockito.spy(ExcelFileProcessor.class);
		ExcelFileProcessor pfp2 = Mockito.spy(ExcelFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectExcelFirst(), new PageObjectExcelFirst() };
		Integer[] checkCalls = { 1, 0 };
		Integer[] parseCalls = { 1, 0 };
		String[] annotations = { ExcelAnnotation.getFindByAnnotationFullName(),
				ExcelAnnotation.getFindByAnnotationFullName()};

		createThreadsAssertCache(fp, tp, false, true, checkCalls, parseCalls,annotations);
	}

	// Two threads of Excel file processor at almost same time updating different
	// page object data from same file
	@Test()
	public void twoSameTimeExcelFileThreadsWithDifferentPageObjectSameFile()
			throws NoSuchFieldException, SecurityException, InterruptedException {
		

		ExcelFileProcessor pfp1 = Mockito.spy(ExcelFileProcessor.class);
		ExcelFileProcessor pfp2 = Mockito.spy(ExcelFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectExcelSecond(), new PageObjectExcelThird() };
		Integer[] checkCalls = { 1, 1 };
		Integer[] parseCalls = { 1, 1 };		
		String[] annotations = { ExcelAnnotation.getFindByAnnotationFullName(),
		ExcelAnnotation.getFindByAnnotationFullName()};

		createThreadsAssertCache(fp, tp, true, false, checkCalls, parseCalls,annotations);
	}
	
	
	// Two threads of Excel file processor at almost same time updating different
	// page object data from different file
	@Test()
	public void twoSameTimeExcelFileThreadsWithDifferentPageObjectDifferentFile()
			throws NoSuchFieldException, SecurityException, InterruptedException {
		

		ExcelFileProcessor pfp1 = Mockito.spy(ExcelFileProcessor.class);
		ExcelFileProcessor pfp2 = Mockito.spy(ExcelFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectExcelFourth(), new PageObjectExcelFifth() };
		Integer[] checkCalls = { 1, 1 };
		Integer[] parseCalls = { 1, 1 };
		String[] annotations = { ExcelAnnotation.getFindByAnnotationFullName(),
				ExcelAnnotation.getFindByAnnotationFullName()};

		createThreadsAssertCache(fp, tp, true, false, checkCalls, parseCalls,annotations);
	}
	
	// Two threads of Excel file processor at different time updating different
	// page object data from same file
	@Test()
	public void twoDifferentTimeExcelFileThreadsWithDifferentPageObjectSameFile()
			throws NoSuchFieldException, SecurityException, InterruptedException {
		

		ExcelFileProcessor pfp1 = Mockito.spy(ExcelFileProcessor.class);
		ExcelFileProcessor pfp2 = Mockito.spy(ExcelFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectExcelSecond(), new PageObjectExcelThird() };
		Integer[] checkCalls = { 1, 0 };
		Integer[] parseCalls = { 1, 0 };
		String[] annotations = { ExcelAnnotation.getFindByAnnotationFullName(),
				ExcelAnnotation.getFindByAnnotationFullName()};

		createThreadsAssertCache(fp, tp, true, true, checkCalls, parseCalls,annotations);
	}	
	
	// Two threads of Excel file processor at different time updating different
	// page object data from different file
	@Test()
	public void twoDifferentTimeExcelFileThreadsWithDifferentPageObjectDifferentFile()
			throws NoSuchFieldException, SecurityException, InterruptedException {
		

		ExcelFileProcessor pfp1 = Mockito.spy(ExcelFileProcessor.class);
		ExcelFileProcessor pfp2 = Mockito.spy(ExcelFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2 };
		TestPage[] tp = { new PageObjectExcelFourth(), new PageObjectExcelFifth() };
		Integer[] checkCalls = { 1, 1 };
		Integer[] parseCalls = { 1, 1 };
		String[] annotations = { ExcelAnnotation.getFindByAnnotationFullName(),
				ExcelAnnotation.getFindByAnnotationFullName()};

		createThreadsAssertCache(fp, tp, true, true, checkCalls, parseCalls,annotations);
	}
	

	@ExcelFile(filePath = "src/test/resources/excel/ThreadPOExcelOneData.xlsx")
	public class PageObjectExcelFirst implements TestPage {
		@FindByExcel
		private WebElement element1;
		@FindByExcel
		private WebElement element2;
	}

	@ExcelFile(filePath = "src/test/resources/excel/ThreadPOExcelTwoData.xlsx")
	public class PageObjectExcelSecond implements TestPage {
		@FindByExcel
		private WebElement element3;
		@FindByExcel
		private WebElement element4;
	}

	@ExcelFile(filePath = "src/test/resources/excel/ThreadPOExcelTwoData.xlsx")
	public class PageObjectExcelThird implements TestPage {
		@FindByExcel
		private WebElement element5;
		@FindByExcel
		private WebElement element6;
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/ThreadPOExcelFourData.xlsx")
	public class PageObjectExcelFourth implements TestPage {
		@FindByExcel
		private WebElement element7;
		@FindByExcel
		private WebElement element8;
	}

	@ExcelFile(filePath = "src/test/resources/excel/ThreadPOExcelFiveData.xlsx")
	public class PageObjectExcelFifth implements TestPage {
		@FindByExcel
		private WebElement element9;
		@FindByExcel
		private WebElement element10;
	}

}
