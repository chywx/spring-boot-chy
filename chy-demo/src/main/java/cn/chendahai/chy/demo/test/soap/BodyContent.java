package cn.chendahai.chy.demo.test.soap;

import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "aa1",
        "aa2",
        "aa3"
})
@XmlRootElement(name = "eapp:aaaaaa")
@Data
public class BodyContent {

    @XmlElement(required = true, name = "eapp:compNo")
    protected String aa1;
    @XmlElement(required = true, name = "eapp:agentCode")
    protected String aa2;
    @XmlElement(required = true, name = "eapp:eAppId")
    protected String aa3;
    //get set方法省略




}