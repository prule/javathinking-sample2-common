package com.javathinking.sample2.common.file.input;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 12/03/2014
 */
public class SysoutLineParsingListener extends AbstractLineParsingListener {

    @Override
    public void handleObject(Object object, FileFormatContext context) {
        System.out.println(ToStringBuilder.reflectionToString(object));
    }

}
