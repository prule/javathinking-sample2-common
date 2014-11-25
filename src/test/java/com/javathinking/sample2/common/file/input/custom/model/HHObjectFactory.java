package com.javathinking.sample2.common.file.input.custom.model;

import com.javathinking.sample2.common.file.input.custom.ObjectFactory;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class HHObjectFactory implements ObjectFactory<HH> {

    public static final String DATE = "date";

    @Override
    public HH fromMap(Map map) {
        return new HH((DateTime) map.get(DATE));
    }

    @Override
    public Map<String, Object> toMap(HH object) {
        Map<String, Object> map = new HashMap();
        map.put(DATE, object.getDate());
        return map;
    }


}
