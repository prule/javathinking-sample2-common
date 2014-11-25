package com.javathinking.sample2.common.file.input.custom.model;

import com.javathinking.sample2.common.file.input.custom.ObjectFactory;

import java.util.HashMap;
import java.util.Map;

public class GHObjectFactory implements ObjectFactory<GH> {

    public static final String CLIENT = "client";

    @Override
    public GH fromMap(Map map) {
        return new GH((String) map.get(CLIENT));
    }

    @Override
    public Map<String, Object> toMap(GH object) {
        Map<String, Object> map = new HashMap();
        map.put(CLIENT, object.getClient());
        return map;
    }


}
