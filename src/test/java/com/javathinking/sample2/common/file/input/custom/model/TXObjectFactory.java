package com.javathinking.sample2.common.file.input.custom.model;

import com.javathinking.sample2.common.file.input.custom.ObjectFactory;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TXObjectFactory implements ObjectFactory<TX> {

    public static final String DATE = "date";
    public static final String AMOUNT = "amount";
    public static final String ACCOUNT = "account";

    @Override
    public TX fromMap(Map map) {
        return new TX((String) map.get(ACCOUNT), (DateTime) map.get(DATE), (BigDecimal) map.get(AMOUNT));
    }

    @Override
    public Map<String, Object> toMap(TX object) {
        Map<String, Object> map = new HashMap();
        map.put(ACCOUNT, object.getAccount());
        map.put(DATE, object.getDate());
        map.put(AMOUNT, object.getAmount());
        return map;
    }


}
