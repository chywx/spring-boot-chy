package cn.chendahai.chy.demo.test.soap;

import cn.chendahai.chy.demo.tools.JaxbXmlUtil;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "header",
        "body"
})
@XmlRootElement(name = "soapenv:Envelope")
@Data
public class RequestEntity {

    @XmlAttribute(name = "xmlns:soapenv")
    protected String soapenv = "http://schemas.xmlsoap.org/soap/envelope/";

    @XmlAttribute(name = "xmlns:eapp")
    protected String eapp = "http://wx.gmw9.com";

    @XmlAttribute(name = "xmlns:bus")
    protected String bus = null;


    @XmlElement(required = true, name = "soapenv:Header")
    protected RequestHeader header;

    @XmlElement(required = true, name = "soapenv:Body")
    protected RequestBody body;


    //get set方法省略

    public static void main(String[] args) throws Exception {
        RequestEntity re = new RequestEntity();

        BodyContent bodyContent = new BodyContent();
        bodyContent.setAa1("chy1");
        bodyContent.setAa2("chy2");
        bodyContent.setAa3("chy3");

        RequestBody requestBody = new RequestBody();
        requestBody.setAaaaaaa(bodyContent);
        re.setBody(requestBody);
        re.setHeader(new RequestHeader());

        String xmlout = JaxbXmlUtil.convertToXml(re);
        System.out.println(xmlout);
    }


}
