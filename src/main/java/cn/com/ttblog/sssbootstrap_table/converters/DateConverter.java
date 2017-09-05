package cn.com.ttblog.sssbootstrap_table.converters;

import com.google.common.collect.Lists;
import org.springframework.core.convert.converter.Converter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 日期格式转换,符合formarts中的任一种格式就可以
 */
public class DateConverter implements Converter<String, Date> {

    private static final List<String> formarts = Lists.newArrayList("yyyy-MM", "yyyy-MM-dd", "yyyy-MM-dd hh:mm", "yyyy-MM-dd hh:mm:ss");

    public Date convert(String source) {
        String value = source.trim();
        if ("".equals(value)) {
            return null;
        }
        if (source.matches("^\\d{4}-\\d{1,2}$")) {
            return parseDate(source, formarts.get(0));
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            return parseDate(source, formarts.get(1));
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            return parseDate(source, formarts.get(2));
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return parseDate(source, formarts.get(3));
        } else {
            throw new IllegalArgumentException(String.format("日期格式%s错误,需要符合:%s中的日期格式!", source, formarts));
        }
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            date = (Date) dateFormat.parse(dateStr);
        } catch (Exception e) {
        }
        return date;
    }

}