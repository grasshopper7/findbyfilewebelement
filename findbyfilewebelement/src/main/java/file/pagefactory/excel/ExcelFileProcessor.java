package file.pagefactory.excel;

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

public class ExcelFileProcessor implements FileProcessor {

	private String path;
	private String sheetName;

	@Override
	public void dataSourceDetails(Field field) {
		ExcelFile xlsFile = field.getDeclaringClass().getAnnotation(ExcelFile.class);
		path = xlsFile.filePath();
		sheetName = xlsFile.sheetName();
	}

	@Override
	public void parseDataSource(/*Field field*/) {

		System.out.println(Thread.currentThread().getId() + "---" + "Processing Excel");
		try (Workbook workbook = WorkbookFactory.create(new File(path));) {
			Sheet sheet = workbook.getSheet(sheetName);
			DataFormatter dataFormatter = new DataFormatter();

			String pkgClsName = "";
			Class<?> pkgCls = null;
			String fld = "";
			String how = "";
			String using = "";

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				pkgClsName = dataFormatter.formatCellValue(row.getCell(0));

				if (!pkgClsName.isEmpty())
					pkgCls = Class.forName(pkgClsName);
				
				fld = dataFormatter.formatCellValue(row.getCell(1));
				how = dataFormatter.formatCellValue(row.getCell(2));
				using = dataFormatter.formatCellValue(row.getCell(3));				
				checkValues(fld, how, using, i);

				FieldByCache.addDetail(pkgCls.getDeclaredField(fld),
						ByCreator.createBy(how.toUpperCase(), using));
			}
			workbook.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void checkValues(String fld, String how, String using, int line) {
		if(fld == null || fld.length() == 0)
			throw new IllegalArgumentException("Field Name attribute data is missing for line " + line + ".");
		else if(how == null || how.length() == 0)
			throw new IllegalArgumentException("How attribute data is missing for line " + line + ".");
		else if(using == null || using.length() == 0)
			throw new IllegalArgumentException("Using attribute data is missing for line " + line + ".");
	}

	@Override
	public Annotations getAnnotation(Field field) {
		return new ExcelAnnotation(field, this);
	}

}
