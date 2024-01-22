package cn.chendahai.chy.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class LuckyBusUser {

    //    @ExcelProperty(value= "userId")
    @ExcelProperty(value = "user_id")
    private Integer userId;

    //    @ExcelProperty(value= "username")
    @ExcelProperty(value = "PHONE_NUMBER")
//    @ExcelProperty(index = 1)
    private String username;

    //    @ExcelProperty(value= "addAmount")
    @ExcelProperty(value= "加钱")
//    @ExcelProperty(value = "TRANS_AMOUNT")
//    @ExcelProperty(index = 2)
    private String addAmount;

}
