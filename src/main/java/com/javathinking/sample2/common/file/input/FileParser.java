package com.javathinking.sample2.common.file.input;


import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

/**
 * Date: 9/03/2014
 */
public interface FileParser {
    FileFormatContext parse(Reader reader, FileFormatContext context) throws IOException;

    void setListeners(List<LineParsingListener> listeners);

    ObjectReader getObjectReader(Reader reader, final FileFormatContext context);

}
