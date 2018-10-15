package file.pagefactory;

import org.junit.Test;
import org.mockito.Mockito;

import file.pagefactory.excel.ExcelAnnotation;
import file.pagefactory.excel.ExcelFileProcessor;
import file.pagefactory.json.JsonAnnotation;
import file.pagefactory.json.JsonFileProcessor;
import file.pagefactory.properties.PropertiesAnnotation;
import file.pagefactory.properties.PropertiesFileProcessor;

public class AllFPMultipleThreadCacheTest extends BaseMultipleThreadCacheTest {

	// Three threads of different file processor type at almost same time updating
	// different page object data.
	@Test
	public void sameTimeAllFileProcessorThreadsWithDifferentPageObject() throws NoSuchFieldException, SecurityException,
			InterruptedException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException {

		executeAllFPThreads(false);
	}

	// Three threads of different file processor type at different time updating
	// different page object data.
	@Test
	public void differentTimeAllFileProcessorThreadsWithDifferentPageObject() {

		executeAllFPThreads(true);
	}

	private void executeAllFPThreads(boolean time) {

		ExcelFileProcessor pfp1 = Mockito.spy(ExcelFileProcessor.class);
		JsonFileProcessor pfp2 = Mockito.spy(JsonFileProcessor.class);
		PropertiesFileProcessor pfp3 = Mockito.spy(PropertiesFileProcessor.class);

		FileProcessor[] fp = { pfp1, pfp2, pfp3 };
		TestPage[] tp = { new CommonValidPageObjects.PageObjectExcel(), new CommonValidPageObjects.PageObjectJson(),
				new CommonValidPageObjects.PageObjectProperties() };
		Integer[] checkCalls = { 1, 1, 1 };
		Integer[] parseCalls = { 1, 1, 1 };
		String[] annotations = { ExcelAnnotation.getFindByAnnotationFullName(),
				JsonAnnotation.getFindByAnnotationFullName(), PropertiesAnnotation.getFindByAnnotationFullName() };

		createThreadsAssertCache(fp, tp, true, time, checkCalls, parseCalls, annotations);
	}

}
