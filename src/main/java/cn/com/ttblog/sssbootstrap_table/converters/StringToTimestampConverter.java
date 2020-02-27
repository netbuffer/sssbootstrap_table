package cn.com.ttblog.sssbootstrap_table.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToTimestampConverter implements Converter<String, Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(StringToTimestampConverter.class);
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Integer convert(String source) {
        Date d = null;
        if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            try {
                d = format.parse(source);
            } catch (Exception e) {
                LOG.error("转换:{}到日期类型失败!", source);
            }
        }
        if (d != null) {
            return (int) (d.getTime() / 1000);
        } else {
            return Integer.parseInt(source);
        }
    }

}
