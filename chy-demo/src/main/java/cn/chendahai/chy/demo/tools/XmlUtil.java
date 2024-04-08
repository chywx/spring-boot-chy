package cn.chendahai.chy.demo.tools;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;
import org.junit.platform.commons.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author xxp
 * @date 2019/07/24
 * @time 13:51
 * *Xstream是一种OXMapping 技术，是用来处理XML文件序列化的框架,在将JavaBean序列化，或将XML文件反序列化的时候，不需要其它辅助类和映射文件，使得XML序列化不再繁索。Xstream也可以将JavaBean序列化成Json或反序列化，使用非常方便。
 * * 主要使用
 * * @XStreamAlias(“alis”)java对象在xml中以标签的形式显示时，如果名字与类名或者属性名不一致，可以使用该标签并在括号内注明别名。
 * * @XStreamOmitField在输出XML的时候忽略该属性
 * * @XStreamImplicit如果该属性是一个列表或者数组，在XML中不显示list或者Array字样
 * * @XStreamAsAttribute该属性不单独显示成XML节点，而是作为属性显示出来
 * * @XStreamContainedType
 * * @XStreamConverter设置转换器
 * * @XStreamConverters converter主要用于将某些字段进行复杂的转换，转换过程写在一个类中。
 * * 然后将其注册到XStream。
 */
public class XmlUtil {

    public static String xmlTag = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";


    /**
     * xml文件转对象
     */
    public static <T> T xmlFileToBean(Class<T> clazz, String xmlPath) {
        T xmlObject = null;
        XStream xStream = new XStream(new Xpp3Driver(new NoNameCoder()));
//        XStream xStream = new XStream(new DomDriver("UTF_8"));
        xStream.registerConverter(new DateConverter());
        xStream.ignoreUnknownElements();
        xStream.processAnnotations(clazz);
        xStream.autodetectAnnotations(true);
        File file = new File(xmlPath);
        xmlObject = (T) xStream.fromXML(file);
        return xmlObject;
    }

    public static <T> T xmlFileToBean(Class<T> clazz, String xmlPath, String alias) {
        T xmlObject = null;
        XStream xStream = new XStream(new DomDriver("UTF_8"));
        xStream.registerConverter(new DateConverter());
        if (StringUtils.isNotBlank(alias)) {
            xStream.alias(alias, clazz);
        }
        xStream.processAnnotations(clazz);
        xStream.autodetectAnnotations(true);
        File file = new File(xmlPath);
        xmlObject = (T) xStream.fromXML(file);
        return xmlObject;
    }

    /**
     * xml字符串转对象
     */
    public static <T> T xmlStrToBean(String xmlString, Class<T> clazz) {
        if (xmlString == null) return null;
        T xmlObject = null;
        XStream xStream = new XStream(new DomDriver("UTF_8"));
        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(new Class[]{clazz});
        xStream.processAnnotations(clazz);
        xStream.autodetectAnnotations(true);
        xmlObject = (T) xStream.fromXML(xmlString);
        return xmlObject;
    }

    /**
     * 对象转xml文件
     */
    public static void beanToXml(Object obj, String filePath) throws IOException {
        XStream xStream = new XStream(new DomDriver("UTF_8"));
        xStream.processAnnotations(obj.getClass());
        Writer writer = new FileWriter(filePath);
        writer.write(xmlTag);
        xStream.toXML(obj, writer);
    }

    /**
     * xml转String
     *
     * 据说开了autodetect会变慢(没测试过)，所以封装了这两个方法，在object内没有泛型/接口/基类时，就默认不开autodetect
     */
    public static String beanToXml(Object object) {
        return beanToXml(object, false);
    }

    /**
     * xml转String
     *
     * 据说开了autodetect会变慢(没测试过)，所以封装了这两个方法，在object内没有泛型/接口/基类时，就默认不开autodetect
     */
    public static String beanToXml(Object object, boolean autodetectAnnotations) {
        XStream xStream = new XStream(new DomDriver("UTF_8"));
        xStream.processAnnotations(object.getClass());
        if (autodetectAnnotations) {
            xStream.autodetectAnnotations(true);
        }
        String xmlString = xStream.toXML(object);
        return xmlTag + xmlString;
    }

    public static String beanToNoTagXml(Object object) {
        XStream xStream = new XStream(new DomDriver("UTF_8"));
        xStream.processAnnotations(object.getClass());
        String xmlString = xStream.toXML(object);
        return xmlString;
    }
}
