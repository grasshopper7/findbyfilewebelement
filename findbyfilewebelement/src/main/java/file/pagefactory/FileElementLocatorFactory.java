package file.pagefactory;

import java.lang.reflect.Field;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.DefaultElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

public class FileElementLocatorFactory implements ElementLocatorFactory {

	private final SearchContext searchContext;
	private FileProcessor fileProcessor;

	public FileElementLocatorFactory(SearchContext searchContext, FileProcessor fileProcessor) {
		this.fileProcessor = fileProcessor;
		this.searchContext = searchContext;
	}

	@Override
	public ElementLocator createLocator(Field field) {		
		//StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		System.out.println("Locator Factory -- "+Thread.currentThread().getId() /*+ "----" + stackTraceElements[3].getClassName() + "----" + stackTraceElements[3].getMethodName()*/);
		return new DefaultElementLocator(searchContext, fileProcessor.getAnnotation(field));
	}
}
