package cn.chendahai.chy.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Recall {

    @ExcelProperty(value= "user_id",index = 0)
    private Integer userId;

    @ExcelProperty(value= "1%返给用户",index = 3)
    private String rewardAmount;

}
