package file.pagefactory;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import file.pagefactory.excel.ExcelFile;
import file.pagefactory.excel.FindByExcel;
import file.pagefactory.json.FindByJson;
import file.pagefactory.json.JsonFile;
import file.pagefactory.properties.FindByProperties;
import file.pagefactory.properties.PropertiesFile;

public class CommonValidPageObjects {

	@PropertiesFile(filePath = "src/test/resources/properties/MultipleThreadPOPropertiesData.properties")
	public static class PageObjectProperties implements TestPage {
		@FindByProperties
		private WebElement element1;
		@FindByProperties
		private List<WebElement> element2;
		@FindBy
		private WebElement element3;
		private Object element4;
		private List<Object> element5;
	}

	@JsonFile(filePath = "src/test/resources/json/MultipleThreadPOJsonData.json")
	public static class PageObjectJson implements TestPage {
		@FindByJson
		private WebElement element1;
		@FindByJson
		private List<WebElement> element2;
		@FindBy
		private WebElement element3;
		private Object element4;
		private List<Object> element5;
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/MultipleThreadPOExcelData.xlsx")
	public static class PageObjectExcel implements TestPage {
		@FindByExcel
		private WebElement element1;
		@FindByExcel
		private List<WebElement> element2;
		@FindBy
		private WebElement element3;
		private Object element4;
		private List<Object> element5;
	}
	
	public static class PageObjectNormal implements TestPage {
		@FindBy
		private WebElement elementAA;
		@FindBy
		private List<WebElement> elementBB;
		private Object elementCC;
	}
}
