package com.javathinking.sample2.common.file.input.custom.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;

public class TX {
    private String account;
    private DateTime date;
    private BigDecimal amount;

    public TX() {
    }

    public TX(String account, DateTime date, BigDecimal amount) {
        this.account = account;
        this.date = date;
        this.amount = amount;
    }

    public String getAccount() {
        return account;
    }

    public DateTime getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
