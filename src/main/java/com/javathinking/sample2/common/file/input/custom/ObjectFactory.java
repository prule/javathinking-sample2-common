package com.javathinking.sample2.common.file.input.custom;

import java.util.Map;

public interface ObjectFactory<T> {
    T fromMap(Map map);

    Map toMap(T object);
}
