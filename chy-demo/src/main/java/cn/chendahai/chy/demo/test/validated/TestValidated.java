package cn.chendahai.chy.demo.test.validated;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.groups.Default;
import java.util.Iterator;
import java.util.Set;

public class TestValidated {

    /**
     * @Validated与@Valid的简单对比说明
     * @Valid注解与@Validated注解功能大部分类似；两者的不同主要在于:@Valid属于javax下的，而@Validated属于spring下；@Valid支持嵌套校验、而@Validated不支持，@Validated支持分组，而@Valid不支持
     */

    public static void main(String[] args) {
        TestValidated testValidated = new TestValidated();
        DevEquipment devEquipment = new DevEquipment();
        devEquipment.setA("aaa");
        devEquipment.setB("aaaaaa");
        devEquipment.setC("2");
        devEquipment.setEmail("abc@com");
        String res = testValidated.validateDevEquipment(devEquipment);
        System.out.println(res);
    }

    private String validateDevEquipment(DevEquipment devEquipment) {
        //先进行参数校验
        StringBuilder returnSb = new StringBuilder();
        Set<ConstraintViolation<DevEquipment>> validateResult = Validation.buildDefaultValidatorFactory().getValidator().validate(devEquipment);
        if (validateResult.size() > 0) {
            Iterator<ConstraintViolation<DevEquipment>> it = validateResult.iterator();
            while (it.hasNext()) {
                ConstraintViolation<DevEquipment> cv = it.next();
                returnSb.append(cv.getPropertyPath() + "：" + cv.getMessage()).append(";");
            }
        }
        return returnSb.toString();
    }

}
