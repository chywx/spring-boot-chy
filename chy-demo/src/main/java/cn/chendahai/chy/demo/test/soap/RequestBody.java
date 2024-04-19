package cn.chendahai.chy.demo.test.soap;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "soapenv:Body")
@Data
public class RequestBody {


    @XmlElement(required = true,name="eapp:aaaaaa")
    public BodyContent aaaaaaa;
    //get set方法省略

}
