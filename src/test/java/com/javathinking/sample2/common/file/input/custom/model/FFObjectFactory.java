package com.javathinking.sample2.common.file.input.custom.model;


import com.javathinking.sample2.common.file.input.custom.ObjectFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FFObjectFactory implements ObjectFactory<FF> {

    public static final String COUNT = "count";

    @Override
    public FF fromMap(Map map) {
        return new FF((BigDecimal) map.get(COUNT));
    }

    @Override
    public Map<String, Object> toMap(FF object) {
        Map<String, Object> map = new HashMap();
        map.put(COUNT, object.getCount());
        return map;
    }

}
