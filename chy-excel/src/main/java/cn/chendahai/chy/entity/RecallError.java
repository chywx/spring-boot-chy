package cn.chendahai.chy.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class RecallError {

    @ExcelProperty(value= "username",index = 0)
    private String username;

    @ExcelProperty(value= "amount",index = 3)
    private String amount;

}
