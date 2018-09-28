package file.pagefactory;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import file.pagefactory.CommonValidPageObjects.PageObjectExcel;
import file.pagefactory.CommonValidPageObjects.PageObjectJson;
import file.pagefactory.CommonValidPageObjects.PageObjectNormal;
import file.pagefactory.CommonValidPageObjects.PageObjectProperties;
import file.pagefactory.excel.ExcelFile;
import file.pagefactory.excel.FindByExcel;
import file.pagefactory.json.FindByJson;
import file.pagefactory.json.JsonFile;
import file.pagefactory.properties.FindByProperties;
import file.pagefactory.properties.PropertiesFile;

public class InheritedValidPageObjects {

	@PropertiesFile(filePath = "src/test/resources/properties/PropertiesInheritancePOData.properties")
	public static class PropertiesInheritancePageObject extends PageObjectProperties {
		@FindByProperties
		private WebElement element1;
		@FindByProperties
		private List<WebElement> element2;
		@FindBy
		private WebElement element3;
		private Object element4;
		private List<Object> element5;
	}

	@PropertiesFile(filePath = "src/test/resources/properties/PropertiesInheritanceNormalPOData.properties")
	public static class PropertiesInheritanceNormalPageObject extends PageObjectNormal {
		@FindByProperties
		private WebElement element1;
		@FindByProperties
		private List<WebElement> element2;
		@FindBy
		private WebElement element3;
		private Object element4;
		private List<Object> element5;
	}
	
	@JsonFile(filePath = "src/test/resources/json/JsonInheritancePOData.json")
	public static class JsonInheritancePageObject extends PageObjectJson {
		@FindByJson
		private WebElement element1;
		@FindByJson
		private List<WebElement> element2;
		@FindBy
		private WebElement element3;
		private Object element4;
		private List<Object> element5;
	}
	
	@JsonFile(filePath = "src/test/resources/json/JsonInheritanceNormalPOData.json")
	public static class JsonInheritanceNormalPageObject extends PageObjectNormal {
		@FindByJson
		private WebElement element1;
		@FindByJson
		private List<WebElement> element2;
		@FindBy
		private WebElement element3;
		private Object element4;
		private List<Object> element5;
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/ExcelInheritancePOData.xlsx")
	public static class ExcelInheritancePageObject extends PageObjectExcel {
		@FindByExcel
		private WebElement element1;
		@FindByExcel
		private List<WebElement> element2;
		@FindBy
		private WebElement element3;
		private Object element4;
		private List<Object> element5;
	}
	
	@ExcelFile(filePath = "src/test/resources/excel/ExcelInheritanceNormalPOData.xlsx")
	public static class ExcelInheritanceNormalPageObject extends PageObjectNormal {
		@FindByExcel
		private WebElement element1;
		@FindByExcel
		private List<WebElement> element2;
		@FindBy
		private WebElement element3;
		private Object element4;
		private List<Object> element5;
	}
	
	public static class NormalInheritancePropertiesPageObject extends PageObjectProperties {
		@FindBy
		private WebElement elementAA;
		@FindBy
		private List<WebElement> elementBB;
		private Object elementCC;
	}
	
	public static class NormalInheritanceJsonPageObject extends PageObjectJson {
		@FindBy
		private WebElement elementAA;
		@FindBy
		private List<WebElement> elementBB;
		private Object elementCC;
	}
	
	public static class NormalInheritanceExcelPageObject extends PageObjectExcel {
		@FindBy
		private WebElement elementAA;
		@FindBy
		private List<WebElement> elementBB;
		private Object elementCC;
	}
}
