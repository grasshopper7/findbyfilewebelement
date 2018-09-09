package file.pageobject.test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Instant;

import org.mockito.Mockito;

import file.pagefactory.excel.ExcelFile;
import file.pagefactory.excel.FindByExcel;
import file.pagefactory.properties.PropertiesFileProcessor;

@ExcelFile(filePath = "E:/sample-xlsx-file.xlsx")
public class FindTest {

	@FindByExcel
	public String str1;

	@FindByExcel
	public String str2;

	@FindByExcel
	public String str3;

	public static void main(String[] args) throws /* EncryptedDocumentException, InvalidFormatException, */ IOException,
			ClassNotFoundException, NoSuchFieldException, SecurityException, InterruptedException {

		/*XlsFile xlsFile = FindTest.class.getAnnotation(XlsFile.class);
		String path = xlsFile.filePath();
		String sheetName = xlsFile.sheetName();

		File file = new File(path);
		try(Workbook workbook = WorkbookFactory.create(file);) {
			Sheet sheet1 = workbook.getSheet(sheetName);
			DataFormatter dataFormatter = new DataFormatter();

			for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
				Row row = sheet1.getRow(i);
				for (Cell cell : row) {
					String cellValue = dataFormatter.formatCellValue(cell);
					System.out.print(cellValue + "\t");
				}
				String field = dataFormatter.formatCellValue(row.getCell(0));
				String how = dataFormatter.formatCellValue(row.getCell(1));
				String using = dataFormatter.formatCellValue(row.getCell(2));
				System.out.println(field + "  "+how +"   "+using);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		/*Class<?> cls = Class.forName("xls.pageobject.LoginXlsPageObject");
		System.out.println(cls.getFields().length);
		System.out.println(cls.getDeclaredFields().length);
		Field f = cls.getDeclaredField("usernameInput");
		
		Class<?> cls1 = Class.forName("xls.pageobject.LoginXlsPageObject");
		Field f1 = cls1.getDeclaredField("usernameInput");
		
		Class<?> cls2 = Class.forName("map.pageobject.LoginMapPageObject");
		Field f2 = cls2.getDeclaredField("usernameInput");
		
		System.out.println(f.equals(f1));
		System.out.println(f1.equals(f2));*/
		
		/*Type REVIEW_TYPE = new TypeToken<List<ClassDetails>>(){}.getType();
		
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader("E:/sample-json-file.json"));

		List<ClassDetails> data = gson.fromJson(reader, REVIEW_TYPE);
		System.out.println(data);*/
		
		/*Properties appProps = new Properties();
		appProps.load(new FileInputStream("src/test/resources/sample-properties-file.txt"));
		
		System.out.println(appProps);
		
		Set<Object> keys = appProps.keySet();
		
		for(Object key : keys) {
			System.out.println(key + " -- " + appProps.get(key));
			String s = key.toString();
			System.out.println(s.substring(0, s.indexOf("")));
			System.out.println(s.substring(s.indexOf("$$") + "$$".length()));
			
		}
		
		String[] splitted = "peter##james##thomas".split("##");
		System.out.println(Arrays.toString(splitted));*/
		
		/*Instant lastUpdated = Instant.now();
		Thread.sleep(3596);
		Instant two = Instant.ofEpochSecond(lastUpdated.getEpochSecond(), lastUpdated.getNano());
		
		System.out.println(two.equals(lastUpdated));*/
		
		/*PropertiesFileProcessor spyHell = Mockito.spy(PropertiesFileProcessor.class);
		spyHell.trial(Mockito.mock(Field.class));
		
		Mockito.verify(spyHell, Mockito.times(1)).trial(Mockito.any(Field.class));
		System.out.println("Done");*/
	}
	

}