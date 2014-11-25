package com.javathinking.sample2.common.file.input;

import java.io.IOException;

/**
 * Date: 7/03/2014
 */
public interface LineParsingListener {
    void handleObject(Object object, FileFormatContext context);

    boolean supports(Object object);

    void after(FileFormatContext context);

    void before(FileFormatContext context);
}
