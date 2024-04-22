package cn.chendahai.chy.demo.test.validated;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;

@Data
@ToString
public class DevEquipment {

    @NotBlank(message = "aa")
    @Min(value = 6, message = "aa")
    private String a;

    @NotBlank(message = "bb")
    @Size(min = 6, message = "bb")
    private String b;

    @NotBlank(message = "cc")
    @Pattern(regexp = "1|2|3", message = "cc")
    private String c;

    @Email(message = "非法邮件地址")
    private String email;

}
