package cn.chendahai.chy.demo.tools;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 功能描述
 *
 * @author chy
 * @date 2019/7/24 0024
 */
public class DateConverter implements Converter {

    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {

    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        GregorianCalendar calendar = new GregorianCalendar();
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd"); //格式化当前系统日期
        try {
            calendar.setTime(dateFm.parse(hierarchicalStreamReader.getValue()));
        } catch (ParseException e) {
            throw new ConversionException(e.getMessage(), e);
        }
        return calendar.getTime();
    }

    @Override
    public boolean canConvert(Class aClass) {
        return Date.class == aClass;
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy");
        Date parse = dateFm.parse("2019");
        System.out.println(parse);

    }
}
