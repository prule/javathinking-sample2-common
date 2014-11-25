package com.javathinking.sample2.common.file.input.custom.model;

import org.joda.time.DateTime;

/**
 * Date: 7/03/2014
 */
public class HH {
    private DateTime date;

    public HH() {
    }

    public HH(DateTime date) {
        this.date = date;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }
}
