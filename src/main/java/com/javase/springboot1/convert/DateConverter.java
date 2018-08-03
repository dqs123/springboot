package com.javase.springboot1.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定义字符串转时间的格式化器
 */

public class DateConverter implements Converter<String,Date> {

    /**
     * 2018/08/08
     * 2018/08/08 11:11:11
     * 2018-08-08
     * 2018-08-08 11:11:11
     *
     * @param s
     * @return
     */

    @Override
    public Date convert(String s) {
        Date date = null;
        if(!StringUtils.isEmpty(s)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date =  sdf.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }
}
