package com.javathinking.sample2.common.file.input.custom.model;

import java.math.BigDecimal;

/**
 * Date: 7/03/2014
 */
public class GF {
    private BigDecimal count;

    public GF() {
    }

    public GF(BigDecimal count) {
        this.count = count;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }
}
