package cn.chendahai.chy.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class UserBasic {

    @ExcelProperty(value= "user_id")
    private Integer userId;

    @ExcelProperty(value= "username")
    private String username;


}
