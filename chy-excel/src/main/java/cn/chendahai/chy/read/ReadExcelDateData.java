package cn.chendahai.chy.read;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReadExcelDateData {
    public static void main(String[] args) throws IOException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("C:\\Users\\cob\\Desktop\\文档\\查数\\game数据.xlsx"));


        List<String> list = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2021);
            calendar.set(Calendar.MONTH, i);
            // 获取最后一天
            int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            calendar.set(Calendar.DAY_OF_MONTH, lastDay);
            System.out.println(simpleDateFormat.format(calendar.getTime()));
            list.add(simpleDateFormat.format(calendar.getTime()));
        }

        for (int i = 0; i < 12; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2022);
            calendar.set(Calendar.MONTH, i);
            // 获取最后一天
            int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            calendar.set(Calendar.DAY_OF_MONTH, lastDay);
            System.out.println(simpleDateFormat.format(calendar.getTime()));
            list.add(simpleDateFormat.format(calendar.getTime()));
        }

        System.out.println(list);

        List<Double> amountList = new ArrayList<>();

        Iterator<Sheet> sheetIterator = xssfWorkbook.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            String sheetName = sheet.getSheetName();


            if ("2021".equals(sheetName) || "2022".equals(sheetName)) {
                int lastRowNum = sheet.getLastRowNum();

                List<Integer> columnList = Arrays.asList(6, 13, 20, 27);

                for (Integer column : columnList) {
                    for (int i = 2; i <= lastRowNum; i++) {
                        Cell cell = sheet.getRow(i).getCell(column);
                        if (cell != null) {
//                            System.out.println(sheetName + "\t" + cell.getNumericCellValue());
                            amountList.add(cell.getNumericCellValue());
                        }
                    }
                }

            }

        }

        System.out.println(amountList);
        System.out.println(amountList.size());

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).substring(0, 7) + "\t" + amountList.get(i));
        }

    }
}
