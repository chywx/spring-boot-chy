package cn.chendahai.chy.demo;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

public class ReadDemo {


    /**
     * 不创建对象的读
     */
    @Test
    public void noModelRead() {
        String fileName = "C:\\Users\\cob\\Desktop\\召回\\noModelWrite1722913758777.xlsx";
        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
        EasyExcel.read(fileName, new NoModelDataListener()).sheet()
                .headRowNumber(0)
                .doRead();

        String str = "89|Hot";
        System.out.println(str.split("\\|")[0].length());
    }

}
