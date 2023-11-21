package cn.chendahai.chy.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class LuckyBusUser {

    @ExcelProperty(value= "userId")
    private Integer userId;

    @ExcelProperty(value= "username")
    private String username;

    @ExcelProperty(value= "addAmount")
    private String addAmount;

}
