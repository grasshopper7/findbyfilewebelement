package file.excel.pagefactory;

import java.io.File;
import java.lang.reflect.Field;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.support.pagefactory.Annotations;

import file.pagefactory.ByCreator;
import file.pagefactory.FieldByCache;
import file.pagefactory.FileProcessor;

public class ExcelFileProcessor implements FileProcessor{

	private String path;
	private String sheetName;
	
	@Override
	public void dataSourceDetails(Field field) {
		ExcelFile xlsFile = field.getDeclaringClass().getAnnotation(ExcelFile.class);
		path = xlsFile.filePath();
		sheetName = xlsFile.sheetName();
	}

	@Override
	public void parseDataSource() {
		try (Workbook workbook = WorkbookFactory.create(new File(path));) {
			Sheet sheet = workbook.getSheet(sheetName);
			DataFormatter dataFormatter = new DataFormatter();
			
			String pkgClsName="";
			Class<?> pkgCls = null; 

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				pkgClsName = dataFormatter.formatCellValue(row.getCell(0));
				
				if(!pkgClsName.isEmpty())
					pkgCls = Class.forName(pkgClsName);
				
				FieldByCache.addDetail(pkgCls.getDeclaredField(dataFormatter.formatCellValue(row.getCell(1))), ByCreator.createBy(
						dataFormatter.formatCellValue(row.getCell(2)), dataFormatter.formatCellValue(row.getCell(3))));
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Annotations getAnnotation(Field field) {
		return new ExcelAnnotation(field, this);
	}

}
