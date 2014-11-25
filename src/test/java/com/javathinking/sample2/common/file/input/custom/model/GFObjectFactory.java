package com.javathinking.sample2.common.file.input.custom.model;


import com.javathinking.sample2.common.file.input.custom.ObjectFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class GFObjectFactory implements ObjectFactory<GF> {

    public static final String COUNT = "count";

    @Override
    public GF fromMap(Map map) {
        return new GF((BigDecimal) map.get(COUNT));
    }

    @Override
    public Map<String, Object> toMap(GF object) {
        Map<String, Object> map = new HashMap();
        map.put(COUNT, object.getCount());
        return map;
    }


}
