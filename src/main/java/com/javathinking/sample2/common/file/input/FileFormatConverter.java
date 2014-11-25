package com.javathinking.sample2.common.file.input;

/**
 * Date: 28/03/2014
 */
public interface FileFormatConverter<T,K> {
    T convert(K object, FileFormatContext context);
}
