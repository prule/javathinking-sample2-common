package com.javathinking.sample2.common.file.input;

import java.io.IOException;

public interface ObjectReader {
    Object next() throws LineParsingException, IOException;

    boolean finished();
}
