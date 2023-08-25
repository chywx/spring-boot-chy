package cn.chendahai.chy.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Recall {

    @ExcelProperty(value= "user_id",index = 0)
    private Integer userId;

//    @ExcelProperty(value= "1%返给用户",index = 3)
//    @ExcelProperty(value= "1%返给用户")
    @ExcelProperty(value= "1%返给用户\n" +
            "当地货币")
    private String rewardAmount;

    // 游戏特别使用的
    @ExcelProperty(value= "返利金额")
    private String gameAmount;

}
