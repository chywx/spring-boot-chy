package cn.chendahai.chy.read;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;

public class ReadExcelLocationData {
    public static void main(String[] args) throws IOException {

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("C:\\Users\\cob\\Desktop\\文档\\查数\\game数据.xlsx"));

        Iterator<Sheet> sheetIterator = xssfWorkbook.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            String sheetName = sheet.getSheetName();
            if (sheetName.startsWith("2023")) {

                System.out.print(sheetName + "\t");

                Cell cell = sheet.getRow(0).getCell(18);
                System.out.println(cell.getNumericCellValue());

                DecimalFormat format = new DecimalFormat("#,###.00");
//                System.out.println(format.format(cell.getNumericCellValue() * 7.134));
            }

        }

    }
}
