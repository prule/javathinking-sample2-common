package com.javathinking.sample2.common.file.input.beanio;

import org.beanio.types.TypeConversionException;
import org.beanio.types.TypeHandler;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * Date: 9/03/2014
 */
public class DateTimeHandler implements TypeHandler {
    private String pattern;

    @Override
    public Object parse(String text) throws TypeConversionException {
        return DateTimeFormat.forPattern(pattern).parseDateTime(text);
    }

    @Override
    public String format(Object value) {
        return DateTimeFormat.forPattern(pattern).print((DateTime) value);
    }

    @Override
    public Class<?> getType() {
        return DateTime.class;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

}
