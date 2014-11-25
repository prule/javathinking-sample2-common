package com.javathinking.sample2.common.file.input;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 12/03/2014
 */
public class LoggerDebugLineParsingListener extends AbstractLineParsingListener {
    private static final Logger log = LoggerFactory.getLogger(LoggerDebugLineParsingListener.class);

    @Override
    public void handleObject(Object object, FileFormatContext context) {
        log.debug(ToStringBuilder.reflectionToString(object));
    }

}
